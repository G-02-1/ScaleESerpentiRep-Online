package PlayerObjects;

import Board.Grid.GridBoard.Board;
import Board.Grid.GridCells.StandardCell;
import Patterns.StatePackage.ActivePlayerState;
import Patterns.StatePackage.State;
import SupportingObjects.Cards.Card;
import SupportingObjects.Cards.Deck;
import SupportingObjects.Dice.Dice;
import SupportingObjects.Position;
import SupportingObjects.Token;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Player extends PlayerAB {

    private State state;
    private ArrayList<Card> PARKINGTERMcards;
    private Dice dices[];
    private int diceResult;
    private final int NORMAL = -1, LADDER = 0, SNAKE = 1, DICES = 2, SPRING = 3, BENCH = 4, INN = 5, PICKaCARD = 6;

    public Player(String name, Board board, int diceNumber) {
        super(name, board, diceNumber);
        this.state = new ActivePlayerState(this);
        setDicesNumber(this.dicesNumber);
        PARKINGTERMcards = new ArrayList<>();
    }

    private void setDicesNumber(int dicesNumber) {
        this.dices = new Dice[dicesNumber];
        for(int i = 0; i < dicesNumber; i++) {
            dices[i] = new Dice();
        }
    }

    @Override
    public void changeState(State playerState) {
        this.state = playerState;
    }

    public State getState() {
        return state;
    }


    @Override
    public void turn() throws InterruptedException {
        if(this.state.move()) { //mechanism that returns true if the player is active and false if it is blocked (decreasing the waiting turns)
            boolean anotherTurn = throwDice(this.position);
            TimeUnit.SECONDS.sleep(4);
            Position newPosition = this.computePosition(diceResult);
            this.move(newPosition);
            if (this.currentCell.equals(this.board.getLastCell())) {
                this.win();
            }
            if(anotherTurn) {
                this.sendNotification(Token.ANOTHER.name() + "-" + this.name);
                this.turn();
            }
        }
    }

    @Override
    public boolean throwDice(Position position) {
        int criticalValue = board.getCriticalValue();
        if(this.dicesNumber == 1)
            this.diceResult = dices[0].throwDice();
        this.sendNotification(Token.THROW.name() + "-" + this.name + "-" + this.diceResult);
        if(this.currentNumber >= criticalValue) {
            this.almostWon();
            this.diceResult = dices[0].throwDice();
            this.sendNotification(Token.THROW.name() + "-" + this.name + "-" + this.diceResult);
        }
        else {
            int sum = 0;
            for(Dice d : this.dices) {
                sum += d.throwDice();
            }
            this.diceResult = sum;
            this.sendNotification(Token.THROW.name() + "-" + this.name + "-" + sum);
            return sum == 12;
        }
        return false;
    }

    private void almostWon() {
        this.sendNotification(Token.ALMOST.name() + "-" + this.name + "-" + (this.board.getCellsNumber() - this.currentNumber));
    }

    private Position computePosition(int diceResult) {
        int totalCells = this.board.getCellsNumber();
        int nextCellNumber = this.currentNumber + dicesNumber;
        if(totalCells < nextCellNumber) {
            int surplus = totalCells - nextCellNumber;
            this.retreat(surplus);
            return board.getCellNumber(totalCells - surplus).getPosition();
            //if the number thrown is greater than the total number of cells, then retreat
        }
        else if(totalCells == nextCellNumber) {
            return board.getCellNumber(nextCellNumber).getPosition();
            //if the number thrown is equals to the total number of cells, then win -> in turn()
        }
        else {
            return board.getCellNumber(nextCellNumber).getPosition();
        }
    }

    @Override
    public void move(Position newPosition) throws InterruptedException {
        try {
            this.setPosition(newPosition);
            this.sendNotification(Token.ARRIVE.name() + "-" + this.name + "-" + this.currentNumber);
            TimeUnit.SECONDS.sleep(4);
            int ACTION = this.currentCell.triggerEffect();
            this.manageAction(ACTION);
        } catch (Exception e) {
            System.out.println("Simulation interrupted");
        }
    }

    private void manageAction(int ACTION) throws InterruptedException {
        switch (ACTION) {
            case LADDER:
                this.climbTheLadder();
                break;
            case SNAKE:
                this.sliceOnSnake();
                break;
            case DICES:
                this.throwAgain();
                break;
            case SPRING:
                this.moveAgain(this.diceResult);
                break;
            case BENCH:
                this.standStill();
                break;
            case INN:
                this.goToSleep();
                break;
            case PICKaCARD:
                this.pickACard();
                break;
            default:
                this.state.setActiveState();
        }
    }

    @Override
    public void win() {
        this.sendNotification(Token.WIN.name() + "-" + this.name);
    }

    @Override
    public void climbTheLadder() throws InterruptedException {
        StandardCell current = (StandardCell) this.currentCell;
        //I cast it safely because the trigger effect is sent from the specific cell
        Position newPosition = current.getPASSIVE().getPosition();
        this.sendNotification(Token.CLIMB.name() + "-" + this.name + "-" + this.board.getCell(newPosition).toString());
        this.move(newPosition);
    }

    @Override
    public void sliceOnSnake() throws InterruptedException {
        StandardCell current = (StandardCell) this.currentCell;
        //I cast it safely because the trigger effect is sent from the specific cell
        Position newPosition = current.getPASSIVE().getPosition();
        this.sendNotification(Token.SLICE.name() + "-" + this.name + "-" + this.board.getCell(newPosition).toString());
        this.move(newPosition);
    }

    @Override
    /*
    I assumed that a player can only take advantage of one bonus and that therefore
    if he rolls 12 from a DICES cell or from a DICES card, then he will not throw the dices again
     */
    public void throwAgain() throws InterruptedException {
        this.throwDice(this.position);
        this.sendNotification(Token.THROWAGAIN.name() + "-" + this.name);
        Position newPosition = this.computePosition(this.diceResult);
        this.move(newPosition);
    }

    @Override
    public void moveAgain(int diceResult) throws InterruptedException {
        this.sendNotification(Token.MOVEAGAIN.name() + "-" + this.name + "-" + this.diceResult);
        Position newPosition = this.computePosition(this.diceResult);
        this.move(newPosition);
    }

    @Override
    public void standStill() {
        this.state.setBlockedState(1);
        this.sendNotification(Token.STANDSTILL.name() + "-" + this.name);
        /*
        with a 50% of probability the player with BlockedState will use the PARKINGTERM card
        unless it's near the finish line
         */
        if(new Random().nextInt(2) == 1 || nearFinishLine()) {
            if(!PARKINGTERMcards.isEmpty()) {
                this.sendNotification(Token.PARKINGTERM.name() + "-" + this.name + "-" + this.currentCell.toString());
                this.usePARKINGTERMcard(PARKINGTERMcards.removeFirst());
            }
        }
    }

    private boolean nearFinishLine() {
        try {
            return board.getNumberCell(board.getCell(this.position)) <= this.board.getCellsNumber() - 12;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void goToSleep() {
        this.state.setBlockedState(2);
        this.sendNotification(Token.GOTOSLEEP.name() + "-" + this.name);
    }

    @Override
    public void pickACard() throws InterruptedException {
        Card picked = Deck.INSTANCE.pickACard();
        this.sendNotification(Token.PICKACARD.name() + "-" + this.name + "-" + picked.toString());
        if(picked.isPARKINGTERM()) {
            PARKINGTERMcards.add(picked);
        } else {
            useACard(picked);
        }
    }

    public void useACard(Card card) throws InterruptedException {
        int ACTION = card.triggerEffect();
        this.manageAction(ACTION);
        Deck.INSTANCE.putBottom(card);
    }

    private void usePARKINGTERMcard(Card PARKINGTERM) {
        this.state.setActiveState();
        Deck.INSTANCE.putBottom(PARKINGTERM);
    }

    @Override
    public void retreat(int plusValue) {
        this.sendNotification(Token.RETREAT.name() + "-" + this.name + "-" + plusValue);
    }

}
