package Board.Grid.GridCells;

import Board.Components.BoardComponent;
import Board.Components.BoardComponentIF;
import Board.Grid.GridBoard.Grid;
import SupportingObjects.Position;
import Exceptions.IllegalPositioningException;

public class StandardCell extends Cell {

    private BoardComponentIF boardComponent = null;
    protected boolean ACTIVE = false;
    public StandardCell PASSIVE;

    public StandardCell(Position position, int number, Grid board) {
        super(position, number, board);
    }

    public StandardCell getPASSIVE() {
        return PASSIVE;
    }

    @Override
    public int triggerEffect() {
        if(this.containsBoardComponentActive()) {
            if(this.containLadder()) {
                return 0;
            }
            if(this.containSnake()) {
                return 1;
            }
        }
        return -1;
    }

    private boolean containSnake() {
        return this.boardComponent.isSnake() && ACTIVE;
    }

    private boolean containLadder() {
        return this.boardComponent.isLadder() && ACTIVE;
    }

    public boolean containsBoardComponentActive() {
        return this.boardComponent != null && this.ACTIVE;
    }

    public boolean containsBoardComponentPassive() {
        return this.boardComponent != null && !this.ACTIVE;
    }

    public void setBoardComponent(BoardComponent boardComponent) throws IllegalPositioningException {
        try {
            if(containsBoardComponentActive() || containsBoardComponentPassive()) {
                throw new IllegalPositioningException("cannot insert a BoardComponent into a cell that already " +
                        "contains one's active or passive position");
            }
            else {
                if(this.getPosition().equals(boardComponent.getActivePosition())) {
                    PASSIVE = (StandardCell) board.setPassivePosition(boardComponent);
                    if(PASSIVE != null) {
                        this.boardComponent = boardComponent;
                        ACTIVE = true;
                    } else {
                        throw new IllegalPositioningException("setPassivePosition(boardComponent) failed!");
                    }
                }
                else if(this.getPosition().equals(boardComponent.getPassivePosition())) {
                    this.boardComponent = boardComponent;
                    ACTIVE = false;
                }
                else {
                    throw new IllegalPositioningException("Illegal positioning: unmatched position");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Cell n°: " + this.getNumber());
        if(this.containsBoardComponentActive()) {
            if(this.boardComponent.isLadder()) {
                sb.append("\nContains feet of a ladder");
            }
            if(this.boardComponent.isSnake()) {
                sb.append("\nContains the head of a snake");
            }
        }
        if(this.containsBoardComponentPassive()) {
            sb.append("\nContains the head of a snake");
        }
        return sb.toString();
    }


}
