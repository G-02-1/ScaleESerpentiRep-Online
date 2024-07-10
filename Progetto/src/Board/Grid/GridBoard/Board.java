package Board.Grid.GridBoard;

import Board.Grid.GridCells.SpecialCell;
import Board.Grid.GridCells.StandardCell;
import Board.Grid.GridCells.Cell;
import Exceptions.BoardComponentInstantiationException;
import Exceptions.BoardInstantiationException;
import Exceptions.CellNotFoundException;
import PlayerObjects.Player;
import SupportingObjects.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

public class Board implements Grid {

    private final int CELLNUMBER, X, Y, NORMALCELLNUMBER, BOARDCOMPONENTNUMBER, SPECIALCELLSNUMBER, BENCHNUMBERandDICESNUMBER, INNNUMBERandSPRINGNUMBER, PICKACARDNUMBER;
    private final boolean CUSTOM;
    private HashMap<Cell, ArrayList<Player>> structure;

    public Board(int X, int Y, boolean custom) {
        this.CELLNUMBER = X * Y;
        int expectedSpecialCellsNumber = 0;
        if (this.CELLNUMBER < 20 || this.CELLNUMBER > 225) { //My decision
            throw new BoardInstantiationException("Impossible to instantiate a board with " + this.CELLNUMBER + ", cell");
        } else if (X < 4 || Y < 5 || X > 15 || Y > 15) { //My decision (4 * 5 = 20, 15 * 15 = 225)
            throw new BoardInstantiationException("Impossible to instantiate a board " + X + " wide and " + Y + " high");
        }
        else {
            this.X = X;
            this.Y = Y;
            this.CUSTOM = custom;
            if (this.CUSTOM) {
                //special cells' number is maximum 25% of total cells' number
                expectedSpecialCellsNumber = (int) (0.25 * this.CELLNUMBER);
                //if custom: board components' number is at least 24% of total cells' number
                this.BOARDCOMPONENTNUMBER = (int) (0.24 * this.CELLNUMBER) % 2 == 0 ? (int) (0.24 * this.CELLNUMBER) : (int) (0.24 * this.CELLNUMBER) + 1;
                //BENCH and DICES cells' number is maximum 16% of special cells' number
                this.BENCHNUMBERandDICESNUMBER = (int) (0.16 * expectedSpecialCellsNumber);
                //INN and SPRING cells' number is maximum 12% of special cells' number
                this.INNNUMBERandSPRINGNUMBER = (int) (0.12 * expectedSpecialCellsNumber);
                //PICKACARD cells' number is maximum 44% of special cells' number
                this.PICKACARDNUMBER = (int) (0.44 * expectedSpecialCellsNumber);
                //Effective special cells' number
                this.SPECIALCELLSNUMBER = (2 * this.BENCHNUMBERandDICESNUMBER) + (2 * this.INNNUMBERandSPRINGNUMBER) + this.PICKACARDNUMBER;
            } else {
                this.SPECIALCELLSNUMBER = this.BENCHNUMBERandDICESNUMBER = this.INNNUMBERandSPRINGNUMBER = this.PICKACARDNUMBER = 0;
                //if custom: board components' number is at least 40% of total cells' number
                this.BOARDCOMPONENTNUMBER = (int) (0.4 * this.CELLNUMBER) % 2 == 0 ? (int) (0.4 * this.CELLNUMBER) : (int) (0.4 * this.CELLNUMBER) + 1;
            }
            this.NORMALCELLNUMBER = this.CELLNUMBER - this.SPECIALCELLSNUMBER;
            this.structure = new HashMap<>();
            this.initializeCells();
        }
    }

    private void initializeCells() {
        ArrayList<String> newDisposition = newDisposition();
        ArrayList<Cell> boardCells = new ArrayList<>();
        int number = 1;
        int X = 0;
        int Y = 0;
        for(String type : newDisposition) {
            if(type.equals("STANDARD")) {
                boardCells.add(new StandardCell(new Position(X, Y), number, this));
                number++;
                X++;
            }
            if(type.equals("BENCH")) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, "BENCH"));
                number++;
                X++;
            }
            if(type.equals("DICES")) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, "DICES"));
                number++;
                X++;
            }
            if(type.equals("INN")) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, "INN"));
                number++;
                X++;
            }
            if(type.equals("SPRING")) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, "SPRING"));
                number++;
                X++;
            }
            if(type.equals("PICKACARD")) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, "PICKACARD"));
                number++;
                X++;
            }
            if(X == this.X) {
                X = 0;
                Y++;
            }
        }
        for(Cell cell : boardCells) {
            this.structure.put(cell, new ArrayList<>());
        }
    }

    private ArrayList<String> newDisposition() {
        ArrayList<String> newDisposition = new ArrayList<>();
        int standardCounter = this.NORMALCELLNUMBER;
        int BENCHDICESCounter = this.BENCHNUMBERandDICESNUMBER;
        int INNSPRINGCounter = this.INNNUMBERandSPRINGNUMBER;
        int PICKACARDCounter = this.PICKACARDNUMBER;
        while(standardCounter > 0) {
            newDisposition.add("STANDARD");
            standardCounter--;
        }
        while(BENCHDICESCounter > 0) {
            newDisposition.add("BENCH");
            newDisposition.add("DICES");
            BENCHDICESCounter--;
        }
        while(INNSPRINGCounter > 0) {
            newDisposition.add("INN");
            newDisposition.add("SPRING");
            INNSPRINGCounter--;
        }
        while(PICKACARDCounter > 0) {
            newDisposition.add("PICKACARD");
            PICKACARDCounter--;
        }
        Collections.shuffle(newDisposition);
        return newDisposition;
    }

    public int getCELLNUMBER() {
        return CELLNUMBER;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public int getBOARDCOMPONENTNUMBER() {
        return BOARDCOMPONENTNUMBER;
    }

    public int getSPECIALCELLSNUMBER() {
        return SPECIALCELLSNUMBER;
    }

    public int getBENCHNUMBERandDICESNUMBER() {
        return BENCHNUMBERandDICESNUMBER;
    }

    public int getINNNUMBERandSPRINGNUMBER() {
        return INNNUMBERandSPRINGNUMBER;
    }

    public int getPICKACARDNUMBER() {
        return PICKACARDNUMBER;
    }

    @Override
    public boolean isCustom() {
        return this.CUSTOM;
    }

    @Override
    public boolean isStandard() {
        return !isCustom();
    }

    @Override
    public ArrayList<Cell> getAllCells() {
        ArrayList<Cell> cells = new ArrayList<>(this.structure.keySet());
        Collections.sort(cells);
        return cells;
    }

    @Override
    public int getNumberCell(Cell cell) {
        ArrayList<Cell> cells = this.getAllCells();
        if(cells.contains(cell)) {
            return cells.indexOf(cell);
        }
        throw new CellNotFoundException("The cell: " + cell.toString() + " isn't in the board");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int columns = 0;
        for(Cell cell : getAllCells()) {
            sb.append("[ " + cell.toString() + "]");
            columns++;
            if(columns < this.X) {
                sb.append(", ");
            } else {
                sb.append("\n");
                columns = 0;
            }
        }
        return sb.toString();
    }

    @Override
    public Iterator<Cell> iterator() {
        return this.structure.keySet().iterator();
    }
}
