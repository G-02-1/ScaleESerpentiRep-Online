package SimulationObject;

import Board.Grid.GridBoard.Board;
import Exceptions.IllegalSimulationInitializzation;
import Patterns.Memento.Memento;
import Patterns.Memento.Originator;
import Patterns.ObserverComunication.Subscriber;
import Patterns.StatePackage.AutomaticModeState;
import Patterns.StatePackage.State;
import PlayerObjects.Player;
import SupportingObjects.Token;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulation implements Subscriber, Originator { //Facade, Observer, Builder

    private State state;
    private final int dicesNumber, X, Y, NPlayer;
    private final boolean CUSTOM, CARD, SPECIALCARD;

    //SIMULATION COMPONENTS
    private final Board board;
    private final ArrayList<Player> players;

    public class Builder {

        private final int X, Y, NPlayers;
        private final boolean CUSTOM;
        private int dicesNumber = 0;
        private ArrayList<Player> players;
        private boolean CARD = false, SPECIALCARD = false;
        private ArrayList<String> playersNames;
        private State state;
        private Board board;


        public Builder(int X, int Y, int nPlayers, boolean custom) {
            if(X == 0 && Y == 0) {
                this.X = 10;
                this.Y = 10;
            } else {
                this.X = X;
                this.Y = Y;
            }
            this.NPlayers =nPlayers;
            this.CUSTOM = custom;
            this.players = new ArrayList<>();
            this.playersNames = new ArrayList<>();
        }

        public Builder(Board board, ArrayList<String> playersNames) {
            this.X = board.getX();
            this.Y = board.getY();
            this.NPlayers = playersNames.size();
            this.CUSTOM = board.isCustom();
            this.playersNames = new ArrayList<>(playersNames);
        }

        public Builder(Simulation simulation) {
            this.state = simulation.state;
            this.X = simulation.X;
            this.Y = simulation.Y;
            this.NPlayers = simulation.NPlayer;
            this.CUSTOM = simulation.CUSTOM;
            this.CARD = simulation.CARD;
            this.SPECIALCARD = simulation.SPECIALCARD;
            this.dicesNumber = simulation.dicesNumber;
            this.players = simulation.players;
            this.board = simulation.board;
        }

        public Builder dicesNumber(int val) {
            if(!CUSTOM) {
                throw new IllegalSimulationInitializzation("Cannot choose the dices' number for a STANDARD simulation");
            }
            else {
                this.dicesNumber = val;
                return this;
            }
        }
        public Builder CARD(boolean val) {
            if(!CUSTOM) {
                throw new IllegalSimulationInitializzation("Cannot have cards for a STANDARD simulation");
            }
            else {
                this.CARD = val;
                return this;
            }
        }
        public Builder SPECIALCARD(boolean val) {
            if(!CUSTOM) {
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
            this.simulation = new Simulation(new Builder(simulation.X, simulation.Y, simulation.NPlayer,
                    simulation.isCUSTOM()).State(simulation.state).dicesNumber(simulation.dicesNumber).CARD(simulation.CARD)
                    .SPECIALCARD(simulation.SPECIALCARD).Board(simulation.board).players(simulation.players));
            saveMemento();
        }

        private boolean saveMemento() {
            try {
                LocalDateTime currentDateTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy_HH-mm");
                String formattedDateTime = currentDateTime.format(formatter);
                String folderPath = "C:\\LaddersAndSnakeFolder\\saves\\" + formattedDateTime + ".dat";

                FileOutputStream fos = new FileOutputStream(folderPath);

                // Crea un flusso di output di oggetti per serializzare l'oggetto
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                oos.writeObject(this.simulation); // Scrive l'oggetto su un file
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

    private Simulation(Builder builder) {
        try {
            this.state = builder.state;
            this.X = builder.X;
            this.Y = builder.Y;
            this.NPlayer = builder.NPlayers;
            this.dicesNumber = builder.dicesNumber;
            this.CUSTOM = builder.CUSTOM;
            this.CARD = builder.CARD;
            this.SPECIALCARD = builder.SPECIALCARD;

            this.board = new Board(this.X, this.Y, this.CUSTOM, this.CARD);
            this.players = new ArrayList<>();
            for(String name : builder.playersNames) {
                this.players.add(new Player(name, this.board, this.dicesNumber));
                this.players.getFirst().addSubscriber(this);
            }
        } catch (Exception e) {
            throw new IllegalSimulationInitializzation("Invalid parameters, please try again");
        }
    }

    public State getState() {
        return state;
    }
    public int getX() {
        return X;
    }
    public int getY() {
        return Y;
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
        Player first = this.players.get(new Random().nextInt(this.players.size()));
        int indexOfFirst = this.players.indexOf(first);
        for(int i = indexOfFirst; i > 0; i--) {
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
            notifica(msg);
        }
    }

    @Override
    public Memento save() {
        return new MementoSimulation(this);
    }

    @Override
    public Simulation backup(Memento memento) {
        MementoSimulation mementoSimulation = (MementoSimulation) memento;
        return new Simulation(new Builder(mementoSimulation.getSimulation()));
    }

    private void notifica(String msg) {
        System.out.println(msg);
    }

    public void changeState(State state) {
        this.state = state;
    }
}
