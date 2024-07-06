package Board;

import Board.Components.BoardComponent;
import Board.SupportingObjects.Position;
import Exceptions.CellNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;

public interface BoardIF extends Iterable {

    /**
     *
     * @return all grid's cells
     */
    ArrayList<Cell> getCells();

    /**
     *
     * @param cell
     * @return the number that corresponds to the cell's number
     */
    int getNumberOfCell(Cell cell);

    /**
     *
     * @return the range in which the player will thow only one dice
     */
    default int[] getCriticalRange() {
        Cell last = getLastCell();
        int sixBef = last.getNumber() - 6;
        int[] criticalRange = {sixBef, last.getNumber()};
        return criticalRange;
    }

    /**
     *
     * @return the last cell of the board
     */
    default Cell getLastCell() {
        return getCells().getLast();
    }

    /**
     *
     * @param position
     * @return a Cell which has as its position @param position
     */
    default Cell getCell(Position position) throws CellNotFoundException {
        ArrayList<Cell> cells = getCells();
        Iterator<Cell> it = cells.iterator();
        while(it.hasNext()) {
            Cell cell = it.next();
            if(cell.getPosition().equals(position)) {
                return cell;
            }
        }
        throw new CellNotFoundException("No cells exist at that position");
    }

    default void setPassivePosition(BoardComponent boardComponent) {
        try {
            Position passivePosition = boardComponent.getPassivePosition();
            Cell target = getCell(passivePosition);
            target.setPassivePositionBC(passivePosition);
        } catch (Exception e)  {
            e.printStackTrace();
        }
    }

    @Override
    Iterator<Cell> iterator();
}
