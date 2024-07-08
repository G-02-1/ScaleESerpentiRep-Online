package Board.Grid.GridBoard;

import Board.Grid.GridCells.Cell;
import Board.Grid.GridCells.CellAB;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class Board implements GridIF {

    //private final int CELLNUMBER; //final cells's number
    //private HashMap<CellAB, ArrayList<Player>> structure;

    //molto lungo come costruttore
//    public Board(int CellNumber, int length, int height, int specialCellNumber) {
//        this.CELLNUMBER = CellNumber;
//        this.structure = new HashMap<>();
//        for(int i = 0; i < length; i++) {
//            for(int j = 0; j < height; j++) {
//                Position p = new Position(i, j);
//
//            }
//        }
//    }

    @Override
    public ArrayList<CellAB> getAllCells() {
        return null;
    }

    @Override
    public int getNumberCell(CellAB cell) {
        return 0;
    }

    @Override
    public Iterator<Cell> iterator() {
        return null;
    }
}
