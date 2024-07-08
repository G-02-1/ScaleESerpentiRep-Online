package Board.Grid.GridCells;

import Board.Components.BoardComponent;
import Board.Components.BoardComponentIF;
import Board.Components.Ladder;
import Board.Components.Snake;
import Board.Grid.GridBoard.GridIF;
import SupportingObjects.Position;
import Exceptions.IllegalPositioningException;

public class Cell extends CellAB {

    private BoardComponentIF boardComponent = null;
    protected boolean ACTIVE = false;
    public Cell PASSIVE;

    public Cell(Position position, int number, GridIF board) {
        super(position, number, board);
    }

    public Cell getPASSIVE() {
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
        return this.boardComponent instanceof Snake && ACTIVE;
    }

    private boolean containLadder() {
        return this.boardComponent instanceof Ladder && ACTIVE;
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
                    PASSIVE = (Cell) board.setPassivePosition(boardComponent);
                    if(PASSIVE != null) {
                        this.boardComponent = boardComponent;
                        ACTIVE = true;
                    } else {
                        throw new IllegalPositioningException("setPassivePosition(boardComponent) failed!");
                    }
                }
                if(this.getPosition().equals(boardComponent.getPassivePosition())) {
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
            if(this.boardComponent instanceof Ladder) {
                sb.append("\nContains feet of a ladder");
            }
            if(this.boardComponent instanceof Snake) {
                sb.append("\nContains the head of a snake");
            }
        }
        if(this.containsBoardComponentPassive()) {
            sb.append("\nContains the head of a snake");
        }
        return sb.toString();
    }


}
