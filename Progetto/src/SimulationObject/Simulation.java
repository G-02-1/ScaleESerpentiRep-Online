package SimulationObject;

import Board.Grid.GridBoard.Board;
import Exceptions.IllegalSimulationInitializzation;
import Patterns.Memento.Memento;
import Patterns.Memento.Originator;
import Patterns.ObserverComunication.Subscriber;
import Patterns.StatePackage.State;
import PlayerObjects.Player;
import SupportingObjects.Token;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulation implements Originator, Serializable, Subscriber { //Observer, Builder
    private final int dicesNumber, NPlayer;
    private final boolean CUSTOM, CARD, SPECIALCARD;

    //SIMULATION COMPONENTS
    private final Board board;
    private final ArrayList<Player> players;
    private boolean canStop;

    public static class Builder {

        private final int NPlayers;
        private final boolean CUSTOM;
        private int dicesNumber = 0;
        private ArrayList<Player> players;
        private boolean CARD = false, SPECIALCARD = false;
        private ArrayList<String> playersNames;
        private State state;
        private Board board;
        public Builder(int X, int Y, int nPlayers, boolean custom) {
            this.NPlayers = nPlayers;
            this.CUSTOM = custom;
            this.players = new ArrayList<>();
            this.playersNames = new ArrayList<>();
        }

        public Builder(Board board, ArrayList<String> playersNames) {
            this.board = board;
            this.NPlayers = playersNames.size();
            this.CUSTOM = this.board.isCustom();
            this.playersNames = new ArrayList<>(playersNames);
        }

        public Builder(Simulation simulation) {
            this.NPlayers = simulation.NPlayer;
            this.CUSTOM = simulation.CUSTOM;
            this.CARD = simulation.CARD;
            this.SPECIALCARD = simulation.SPECIALCARD;
            this.dicesNumber = simulation.dicesNumber;
            this.players = simulation.players;
            this.board = simulation.board;
        }

        public Builder dicesNumber(int val) {
            this.dicesNumber = val;
            return this;
        }

        public Builder CARD(boolean val) {
            if(!CUSTOM && val) {
                throw new IllegalSimulationInitializzation("Cannot have cards for a STANDARD simulation");
            }
            else {
                this.CARD = val;
                return this;
            }
        }

        public Builder SPECIALCARD(boolean val) {
            if(!CUSTOM && val) {
                throw new IllegalSimulationInitializzation("Cannot have special cards for a STANDARD simulation");
            }
            else {
                this.SPECIALCARD = val;
                return this;
            }
        }
        public Builder Board(Board board) {
            if(this.board == null) {
                this.board = board;
            }
            else {
                throw new IllegalSimulationInitializzation("Cannot change simulation's board");
            }
            return this;
        }
        public Builder players(ArrayList<Player> players) {
            if(!(this.players.isEmpty() && this.playersNames.isEmpty() && this.NPlayers == players.size())) {
                throw new IllegalSimulationInitializzation("Illegal parameters");
            }
            else {
                this.players = new ArrayList<>(players);
            }
            return this;
        }
        public Builder playersNames(ArrayList<String> playersNames) {
            if(!(this.players.isEmpty() && this.playersNames.isEmpty() && this.NPlayers == players.size())) {
                throw new IllegalSimulationInitializzation("Illegal parameters");
            }
            else {
                this.playersNames = new ArrayList<>(playersNames);
            }
            return this;
        }
        public Builder State(State state) {
            this.state = state;
            return this;
        }
    }//BUILDER
    public class MementoSimulation implements Memento {
        private final Simulation simulation;

        private MementoSimulation(Simulation simulation) {
            this.simulation = simulation;
            saveMemento();
        }
        private boolean saveMemento() {
            try {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy_HH-mm");
                String formattedDateTime = currentDateTime.format(formatter);
                String folderPath = "C:\\LaddersAndSnakeFolder\\saves\\" + formattedDateTime + ".dat";

                FileOutputStream fos = new FileOutputStream(folderPath);

                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(this.simulation);
                fos.close();

                System.out.println("Object successfully saved in: " + folderPath);

            } catch (IOException e) {
                return false;
            }
            return true;
        }

        public Simulation getSimulation() {
            return simulation;
        }

        private Simulation originator() {
            return Simulation.this;
        }


    } //Memento
    public static class Backuper {
        static Simulation simulation;

        public static Simulation backup(Simulation simulation) {
            return simulation.backup(simulation);
        }
    } //Backupper

    public Simulation(Builder builder) {
        try {
            this.NPlayer = builder.NPlayers;
            this.dicesNumber = builder.dicesNumber;
            this.CUSTOM = builder.CUSTOM;
            this.CARD = builder.CARD;
            this.SPECIALCARD = builder.SPECIALCARD;
            this.canStop = false;

            this.board = builder.board;
            this.players = new ArrayList<>();
            for(String name : builder.playersNames) {
                this.players.add(new Player(name, this.board, this.dicesNumber));
            }
            this.schedulePlayers();
        } catch (Exception e) {
            throw new IllegalSimulationInitializzation("Invalid parameters, please try again");
        }
    }
    public boolean canStop() {
        return canStop;
    }
    public int getNPlayer() {
        return NPlayer;
    }
    public int getDicesNumber() {
        return dicesNumber;
    }
    public boolean isCUSTOM() {
        return CUSTOM;
    }
    public boolean hasCARD() {
        return CARD;
    }
    public boolean hasSPECIALCARD() {
        return SPECIALCARD;
    }
    public Board getBoard() {
        return board;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    private void schedulePlayers() {
        int index = new Random().nextInt(this.players.size());
        for(int i = index; i > 0; i--) {
            this.players.add(this.players.removeFirst());
        }

    }
    public void startSimulation() throws InterruptedException {
        for(Player player : players) {
            player.turn();
            TimeUnit.SECONDS.sleep(5);
        }
    }
    @Override
    public Memento save() {
        return new MementoSimulation(this);
    }

    @Override
    public Simulation backup(Simulation simulation) {
        return simulation;
    }

    @Override
    public void update(String message) {
        //I will use an if-statement because in a switch-statement I can't use case Token.CASE.name()
        String[] parts = message.split("-");
        for(int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        String msg;
        String eventType = parts[0];
        String playerName = parts[1];
        if(eventType.equals(Token.ANOTHER.name())) {
            msg = playerName + " threw a double six, so " + playerName + " will throw again the dices!\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.ALMOST.name())) {
            msg = playerName + " is almost arrived to the finish line!\n" + parts[2] + "cells left\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.ARRIVE.name())) {
            msg = playerName + " has just arrived on Cell n°: " + parts[2] + "\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.CLIMB.name())) {
            msg = "How lucky! " + playerName + " encounters a ladder!" + playerName + " goes to " + parts[2] + "\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.GOTOSLEEP.name())) {
            msg = playerName + " is very sleepy... How lucky! Here " + playerName + " has found an Inn, he will be able to rest for two turns without throwing the ";
            if(this.dicesNumber == 2) {
                msg = msg + "dices!\n";
            } else {
                msg = msg + "dice!\n";
            }
            notifica(msg);
        }
        else if(eventType.equals(Token.MOVEAGAIN.name())) {
            msg = "How lucky, a spring " + playerName + " jumps so high... About " + parts[2] + " cells!\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.PARKINGTERM.name())) {
            msg = playerName + " uses the 'Parking Term' card on " + parts[2] + "! Now " + playerName + " is free to go!\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.PICKACARD.name())) {
            msg = playerName + " picked a " + parts[3] + " card!\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.RETREAT.name())) {
            msg = "Too bad! " + playerName + " was almost there, too bad it will have to go back " + parts[2] + " cells...\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.SLICE.name())) {
            msg = "Oh crap! " + playerName + " encounters a snake!" + playerName + " goes to " + parts[2] + "\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.STANDSTILL.name())) {
            msg = playerName + " is very tired... How lucky, a bench! he will be able to rest for one turn without throwing the ";
            if(this.dicesNumber == 2) {
                msg = msg + "dices!\n";
            } else {
                msg = msg + "dice!\n";
            }
            notifica(msg);
        }
        else if(eventType.equals(Token.THROWAGAIN.name())) {
            msg = playerName + " is very lucky, he found two dices, and he really wants to throw again...\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.THROW.name())) {
            msg = playerName + " threw: " + parts[2] + "\n";
            notifica(msg);
        }
        else if(eventType.equals(Token.WIN.name())) {
            msg = playerName + " wins the game!\n";
            this.canStop = true;
            notifica(msg);
        }
    }

    private void notifica(String msg) {
        System.out.println(msg);
    }
}
