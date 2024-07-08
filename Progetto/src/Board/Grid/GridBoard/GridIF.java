package Board.Grid.GridBoard;

import Board.Components.BoardComponent;
import Board.Grid.GridCells.Cell;
import Board.Grid.GridCells.CellAB;
import Exceptions.NoSuchCellNumberException;
import SupportingObjects.Position;
import Exceptions.CellNotFoundException;
import Exceptions.IllegalPositioningException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public interface GridIF extends Iterable {

    /**
     *
     * @return the board's number of cells
     */
    int getCellsNumber();

    /**
     *
     * @return all grid's cells
     */
    ArrayList<CellAB> getAllCells();

    /**
     *
     * @param cell
     * @return the number that corresponds to the cell's number
     */
    int getNumberCell(CellAB cell);

    /**
     *
     * @param number
     * @return the cell that corresponds to this @param
     */
    default CellAB getCellNumber(int number) {
        for(CellAB c : getAllCells()) {
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
    default CellAB getLastCell() {
        return getAllCells().getLast();
    }

    /**
     *
     * @return the range in which the player will thow only a dice
     */
    default int[] getCriticalRange() {
        CellAB last = getLastCell();
        int sixBef = last.getNumber() - 6;
        int[] criticalRange = {sixBef, last.getNumber()};
        return criticalRange;
    }

    /**
     *
     * @param position
     * @return a Cell which has as its position @param position
     */
    default CellAB getCell(Position position) throws CellNotFoundException {
        ArrayList<CellAB> cells = getAllCells();
        for (CellAB cell : cells) {
            if (cell.getPosition().equals(position)) {
                return cell;
            }
        }
        throw new CellNotFoundException("No cells exist at that position");
    }

    /**
     *
     * @return an ArrayList<Cell> whose pair is like (activeCell, passiveCell)
     */
    default ArrayList<CellAB> getBoardComponentCells() {
        ArrayList<CellAB> cells = getAllCells();
        ArrayList<CellAB> boardComponentCells = new ArrayList<>();
        for (CellAB cell : cells) {
            if (cell instanceof Cell) {
                if (((Cell) cell).containsBoardComponentActive()) {
                    boardComponentCells.add(cell);
                    boardComponentCells.add(((Cell) cell).PASSIVE);
                }
            }

        }
        Collections.sort(boardComponentCells);
        return boardComponentCells;
    }

    /**
     * sets the board's passive position in the specific cell
     * @param boardComponent
     * @return the completion of the insertion
     */
    default CellAB setPassivePosition(BoardComponent boardComponent) {
        try {
            Position passivePosition = boardComponent.getPassivePosition();
            CellAB wanted = getCell(passivePosition);
            if(wanted instanceof Cell) {
                Cell target = (Cell) getCell(passivePosition);
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
