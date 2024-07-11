package Board.Grid.GridCells;

import Board.Grid.GridBoard.Grid;
import SupportingObjects.Position;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public abstract class Cell implements Comparable<Cell>, Serializable {

    protected final Position position;

    protected Grid board;
    protected int number;

    public Cell(Position position, int number, Grid board) {
        this.position = position;
        this.number = number;
        this.board = board;
    }

    public int getPosX() {
        return this.position.getX();
    }

    public int getPosY() {
        return this.position.getY();
    }

    public Position getPosition() {
        return this.position;
    }

    public int getNumber() {
        return this.number;
    }

    public ArrayList<Position> getDistancePosition(Cell cell) {
        return this.position.computeDistance(cell.position);
    }
    public int getDistanceNumber(Cell cell) {
        return this.position.computeDistance(cell.position).size();
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public abstract int triggerEffect();

    @Override
    public String toString() {
        return "Cell n°: " + this.getNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof StandardCell)) return false;
        StandardCell gridCell = (StandardCell) o;
        return getPosX() == gridCell.getPosX() && getPosY() == gridCell.getPosY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosX(), getPosY());
    }

    @Override
    public int compareTo(Cell c) {
        if(this.number < c.number) {return -1;}
        if(this.number == c.number) {return 0;}
        else {return 1;}
    }
}
