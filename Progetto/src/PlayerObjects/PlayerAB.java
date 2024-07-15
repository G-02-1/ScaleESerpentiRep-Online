package PlayerObjects;

import Board.Grid.GridBoard.Board;
import Board.Grid.GridCells.Cell;
import SupportingObjects.Position;
import Patterns.ObserverComunication.Manager;

import java.util.Objects;

public abstract class PlayerAB extends Manager implements PlayerIF {

    protected String name;
    protected int currentNumber, dicesNumber;

    protected Position position;
    protected Cell currentCell;

    protected Board board;

    public String getName() {
        return name;
    }

    public PlayerAB(String name, Board board, int diceNumber) {
        this.name = name;
        this.board = board;
        this.dicesNumber = diceNumber;
        this.position = new Position(0,0);
    }

    protected void setPosition(Position position) {
        this.board.removePlayer(position, (Player) this);
        this.position = position;
        this.updateCurrentCell();
        this.updateCurrentNumber();
        this.board.addPlayer(position, (Player) this);
    }

    private void updateCurrentCell() {
        this.currentCell = this.board.getCell(this.position);
    }

    private void updateCurrentNumber() {
        try {
            this.currentNumber = this.board.getNumberCell(this.currentCell);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof PlayerAB)) return false;
        PlayerAB player = (PlayerAB) o;
        return Objects.equals(position, player.position) && Objects.equals(board, player.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, board, dicesNumber);
    }

    @Override
    public String toString() {
        return "Player: " + this.name + ", at position: " + this.position.toString();
    }
}
