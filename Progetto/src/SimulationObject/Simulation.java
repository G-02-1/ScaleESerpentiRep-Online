package SimulationObject;

import Board.Grid.GridBoard.Board;
import Exceptions.IllegalSimulationInitializzation;
import Patterns.ObserverComunication.Subscriber;
import Patterns.StatePackage.State;
import PlayerObjects.Player;
import SupportingObjects.Token;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Simulation implements Subscriber { //Facade, Observer, Builder

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
        private boolean CARD = false, SPECIALCARD = false;
        private ArrayList<String> playersNames;


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
            this.playersNames = new ArrayList<>();
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
        public Builder playerName(String name) {
            if(this.playersNames.size() == this.NPlayers) {
                throw new IllegalSimulationInitializzation("Cannot insert other player's name, player's list is full");
            }
            else {
                this.playersNames.add(name);
            }
            return this;
        }
    }//BUILDER

    private Simulation(Builder builder) {
        try {
            this.X = builder.X;
            this.Y = builder.Y;
            this.NPlayer = builder.NPlayers;
            this.dicesNumber = builder.dicesNumber;
            this.CUSTOM = builder.CUSTOM;
            this.CARD = builder.CARD;
            this.SPECIALCARD = builder.SPECIALCARD;

            this.board = new Board(this.X, this.Y, this.CUSTOM);
            this.players = new ArrayList<>();
            for(String name : builder.playersNames) {
                this.players.add(new Player(name, this.board, this.dicesNumber));
                this.players.getFirst().addSubscriber(this);
            }
        } catch (Exception e) {
            throw new IllegalSimulationInitializzation("Invalid parameters, please try again");
        }
    }

    private void schedulePlayers() {
        Player first = this.players.get(new Random().nextInt(this.players.size()));
        int indexOfFirst = this.players.indexOf(first);
        for(int i = indexOfFirst; i > 0; i--) {
            this.players.add(this.players.removeFirst());
        }

    }

    //RIVEDERE SONO MOLTO STANCO
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

    private void notifica(String msg) {
        System.out.println(msg);
    }

    public void changeState(State state) {
        this.state = state;
    }

    /*
    AVVIA SI ISCRIVE AL MANAGER, quando istanzia un giocatore, fa anche la addSubscriver(this)
     */
}
