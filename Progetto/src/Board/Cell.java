package Board;

import Board.Components.BoardComponent;
import Board.Components.BoardComponentIF;
import Board.Components.Ladder;
import Board.Components.Snake;
import Board.SupportingObjects.Position;
import Exceptions.IllegalPositioningException;

import java.util.Objects;

public class Cell {

    private final Position position;
    private BoardComponentIF boardComponent = null;
    private Position PassivePositionBC = null;
    private BoardIF board;
    private int number;

    public Cell(Position position, int number, BoardIF board) {
        this.position = position;
        this.number = number;
        this.board = board;
    }

    public int getPosX() {
        return position.getX();
    }

    public int getPosY() {
        return position.getY();
    }

    public Position getPosition() {
        return position;
    }

    public int getNumber() {
        return number;
    }

    public boolean containSnake() {
        return boardComponent instanceof Snake;
    }

    public boolean containLadder() {
        return boardComponent instanceof Ladder;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean containsBoardComponentActive() {
        return this.boardComponent != null;
    }

    public boolean containsBoardComponentPassive() {
        return this.PassivePositionBC != null;
    }

    public void setPassivePositionBC(Position passivePositionBC) {
        if(containsBoardComponentActive() || containsBoardComponentPassive()) {
            throw new IllegalPositioningException("cannot insert a BoardComponent into a cell that already contains one's active or passive position");
        }
        else {
            if(this.getPosition().equals(passivePositionBC))
                PassivePositionBC = passivePositionBC;
        }
    }

    public void setBoardComponent(BoardComponent boardComponent) throws IllegalPositioningException {
        if(containsBoardComponentActive() || containsBoardComponentPassive()) {
            throw new IllegalPositioningException("cannot insert a BoardComponent into a cell that already contains one's active or passive position");
        }
        else {
            if(!(this.getPosition().equals(boardComponent.getActivePosition())))
                throw new IllegalPositioningException("Cell accepts only the active position of a BoardComponent");
            else {
                this.boardComponent = boardComponent;
                board.setPassivePosition(boardComponent);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Cell)) return false;
        Cell gridCell = (Cell) o;
        return getPosX() == gridCell.getPosX() && getPosY() == gridCell.getPosY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPosX(), getPosY());
    }
}
