package Board.Grid.GridBoard;

import Board.Components.BoardComponent;
import Board.Grid.GridCells.StandardCell;
import Board.Grid.GridCells.Cell;
import Exceptions.NoSuchCellNumberException;
import SupportingObjects.Position;
import Exceptions.CellNotFoundException;
import Exceptions.IllegalPositioningException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public interface Grid extends Iterable {

    /**
     *
     * @return true if the board is a custom board
     */
    boolean isCustom();

    /**
     *
     * @return true if the board is a standard board
     */
    boolean isStandard();

    /**
     *
     * @return all grid's cells
     */
    ArrayList<Cell> getAllCells();

    /**
     *
     * @param cell
     * @return the number that corresponds to the cell's number
     */
    int getNumberCell(Cell cell);

    /**
     *
     * @return the board's number of cells
     */
    default int getCellsNumber() {
        return getAllCells().size();
    }

    /**
     *
     * @return the board's boardComponentCells' number
     */
    default int getBoardComponentCellsNumber() {
        return this.getBoardComponentCells().size();
    }

    /**
     *
     * @return the board's SpecialCells' number
     */
    default int getSpecialCellsNumber() {
        if(this.isCustom()) {
            return this.getSpecialCells().size();
        }
        return -1;
    }

    /**
     *
     * @return all assignable cells to assign a BoardComponent (Ladder or Snake),
     * so if the observer/system wants to put a ladder/snake he can do on one of these cells
     */
    default ArrayList<Cell> assignableCells() {
        ArrayList<Cell> boardCmponentAssignableCells = this.getAllCells();
        boardCmponentAssignableCells.removeAll(this.getSpecialCells());
        for(Cell cell : boardCmponentAssignableCells) {
            StandardCell cellBC = (StandardCell) cell;
            if(cellBC.containsBoardComponentActive() || cellBC.containsBoardComponentPassive()) {
                boardCmponentAssignableCells.remove(cell);
            }
        }
        return boardCmponentAssignableCells;
    }

    /**
     *
     * @param number
     * @return the cell that corresponds to this @param
     */
    default Cell getCellNumber(int number) {
        for(Cell c : getAllCells()) {
            if(c.getNumber() == number) {
                return c;
            }
        }
        throw new NoSuchCellNumberException("There is no cell with number: " + number);
    }

    /**
     *
     * @return the last cell of the board
     */
    default Cell getLastCell() {
        return getAllCells().getLast();
    }

    /**
     *
     * @return the range in which the player will thow only a dice
     */
    default int getCriticalValue() {
        return getLastCell().getNumber() - 6;
    }

    /**
     *
     * @param position
     * @return a Cell which has as its position @param position
     */
    default Cell getCell(Position position) throws CellNotFoundException {
        ArrayList<Cell> cells = getAllCells();
        for (Cell cell : cells) {
            if (cell.getPosition().equals(position)) {
                return cell;
            }
        }
        throw new CellNotFoundException("No cells exist at that position");
    }

    /**
     *
     * @return an ArrayList<CellAB> of Cells
     */
    default ArrayList<Cell> getBoardComponentCells() {
        ArrayList<Cell> cells = getAllCells();
        cells.removeIf(cell -> !(cell instanceof StandardCell));
        Collections.sort(cells);
        return cells;
    }

    /**
     *
     * @return an ArrayList<CellAB> of SpecialCells
     */
    default ArrayList<Cell> getSpecialCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        if(this.isCustom()) {
            cells = getAllCells();
            cells.removeAll(this.getBoardComponentCells());
            Collections.sort(cells);
        }
        return cells;
    }

    /**
     * sets the board's passive position in the specific cell
     * @param boardComponent
     * @return the completion of the insertion
     */
    default Cell setPassivePosition(BoardComponent boardComponent) {
        try {
            Position passivePosition = boardComponent.getPassivePosition();
            Cell wanted = getCell(passivePosition);
            if(wanted instanceof StandardCell) {
                StandardCell target = (StandardCell) getCell(passivePosition);
                target.setBoardComponent(boardComponent);
                return target;
            } else {
                throw new IllegalPositioningException("Try to insert a passive board component on a " + wanted.getClass());
            }
        } catch (Exception e)  {
            return null;
        }
    }

    /**
     *
     * @return the specific iterator for the board's structure
     */
    @Override
    Iterator<Cell> iterator();
}
