package PlayerObjects;

import Board.Grid.GridBoard.Board;
import Board.Grid.GridCells.Cell;
import Patterns.StatePackage.State;
import SupportingObjects.Dice.Dice;
import SupportingObjects.Position;
import Patterns.PlayerStatePackage.PlayerState;

public class Player extends PlayerAB {

    private State state;
    private Dice dices[];
    private int diceResult;
    private final int NORMAL = -1, LADDER = 0, SNAKE = 1, DICES = 2, SPRING = 3, BENCH = 4, INN = 5, PICKaCARD = 6, PARKINGTERM = 7;


    public Player(int id, Board board, int diceNumber) {
        super(id, board, diceNumber);
        setDicesNumber(this.dicesNumber);
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
    public void throwDice(Position position) {
        int criticalRange[] = board.getCriticalRange();
        int currentNumber = board.getNumberCell(board.getCell(position));
        if(this.dicesNumber == 1)
            this.diceResult = dices[0].throwDice();
        if(currentNumber >= criticalRange[0]  &&
                currentNumber <= criticalRange[1]) {
            this.almostWon(currentNumber);
            this.diceResult = dices[0].throwDice();
        }
        else {
            int sum = 0;
            for(Dice d : this.dices) {
                sum += d.throwDice();
            }
            this.diceResult = sum;
        }
    }

    @Override
    public void move(Position newPosition) {
        this.setPosition(newPosition);
        //board.updatePlayerPosition(this.position);
        int ACTION = board.getCell(this.position).triggerEffect();
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

    private Position computePosition(int diceResult) {
        int totalCells = this.board.getCellsNumber();
        int nextCellNumber = this.board.getCell(this.position).getNumber() + dicesNumber;
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
    public void turn() {
        if(this.state.move()) {
            throwDice(this.position);
            Position newPosition = this.computePosition(diceResult);
            move(newPosition);
            if (this.board.getCell(this.position).equals(this.board.getLastCell())) {
                this.win();
            }
        }
    }

    public void almostWon(int correntNumber) {
        this.sendNotification("ALMOST" + "-" + (this.board.getCellsNumber() - correntNumber));
    }

    @Override
    public void win() {
        this.sendNotification("WIN" + "-" + this.id);
    }

    @Override
    public void climbTheLadder() {
        this.state.setActiveState();
        Cell current = (Cell) this.board.getCell(this.position);
        //I cast it safely because the trigger effect is sent from the specific cell
        Position newPosition = current.getPASSIVE().getPosition();
        this.sendNotification("CLIMB" + "-" + newPosition);
        this.move(newPosition);
    }

    @Override
    public void sliceOnSnake() {
        this.state.setActiveState();
        Cell current = (Cell) this.board.getCell(this.position);
        //I cast it safely because the trigger effect is sent from the specific cell
        Position newPosition = current.getPASSIVE().getPosition();
        this.sendNotification("SLICE" + "-" + newPosition);
        this.move(newPosition);
    }

    @Override
    public void throwAgain() {
        this.state.setActiveState();
        this.throwDice(this.position);
        this.moveAgain(this.diceResult);
        this.sendNotification("THROWAGAIN" + "-" + this.diceResult);
    }

    @Override
    public void moveAgain(int diceResult) {
        this.state.setActiveState();
        Position newPosition = this.computePosition(diceResult);
        this.move(newPosition);
        this.sendNotification("MOVEAGAIN" + "-" + this.id);
    }

    @Override
    public void standStill() {
        this.state.setBlockedState(1);
        this.sendNotification("STANDSTILL" + "-" + 1);
    }

    @Override
    public void goToSleep() {
        this.state.setBlockedState(2);
        this.sendNotification("GOTOSLEEP" + "-" + 2);
    }

    @Override
    public void pickACard() {
        this.state.setActiveState();
        //TODO
        this.sendNotification("PICKACARD" + "-" + this.id);
    }

    @Override
    public void retreat(int plusValue) {
        this.state.setActiveState();
        this.sendNotification("RETREAT" + "-" + plusValue);
    }

}
