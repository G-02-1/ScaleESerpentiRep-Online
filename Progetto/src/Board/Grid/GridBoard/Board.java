package Board.Grid.GridBoard;

import Board.Components.BoardComponent;
import Board.Grid.GridCells.SpecialCell;
import Board.Grid.GridCells.StandardCell;
import Board.Grid.GridCells.Cell;
import Exceptions.BoardComponentInstantiationException;
import Exceptions.BoardInstantiationException;
import Exceptions.CellNotFoundException;
import PlayerObjects.Player;
import SupportingObjects.Position;
import SupportingObjects.Token;

import java.util.*;

public class Board implements Grid {

    private final int CELLNUMBER, X, Y, NORMALCELLNUMBER, BOARDCOMPONENTNUMBER, SPECIALCELLSNUMBER, BENCHNUMBERandDICESNUMBER, INNNUMBERandSPRINGNUMBER, PICKACARDNUMBER;
    private final boolean CUSTOM, CARDS;
    private HashMap<Cell, ArrayList<Player>> structure;

    public Board(int X, int Y, boolean custom, boolean cards) {
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
            this.CARDS = cards;
            if (this.CUSTOM) {
                //special cells' number is maximum 25% of total cells' number
                expectedSpecialCellsNumber = (int) (0.25 * this.CELLNUMBER);
                //if custom: board components' number is at least 24% of total cells' number
                this.BOARDCOMPONENTNUMBER = (int) (0.24 * this.CELLNUMBER) % 2 == 0 ? (int) (0.24 * this.CELLNUMBER) : (int) (0.24 * this.CELLNUMBER) + 1;
                //BENCH and DICES cells' number is maximum 16% of special cells' number
                 int BAndDnumber = (int) (0.16 * expectedSpecialCellsNumber);
                //INN and SPRING cells' number is maximum 12% of special cells' number
                this.INNNUMBERandSPRINGNUMBER = (int) (0.12 * expectedSpecialCellsNumber);
                int pickaCardNumber = 0;
                if(this.CARDS) {
                    //PICKACARD cells' number is maximum 44% of special cells' number
                    pickaCardNumber = (int) (0.44 * expectedSpecialCellsNumber);
                } else {
                    BAndDnumber = BAndDnumber + (int) (0.44 * expectedSpecialCellsNumber);
                }
                this.PICKACARDNUMBER = pickaCardNumber;
                this.BENCHNUMBERandDICESNUMBER = BAndDnumber;
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
            if(type.equals(Token.STANDARD.name())) {
                boardCells.add(new StandardCell(new Position(X, Y), number, this));
                number++;
                X++;
            }
            if(type.equals(Token.BENCH.name())) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, Token.BENCH.name()));
                number++;
                X++;
            }
            if(type.equals(Token.DICES.name())) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, Token.DICES.name()));
                number++;
                X++;
            }
            if(type.equals(Token.INN.name())) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, Token.INN.name()));
                number++;
                X++;
            }
            if(type.equals(Token.SPRING.name())) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, Token.SPRING.name()));
                number++;
                X++;
            }
            if(type.equals(Token.PICKACARD.name())) {
                boardCells.add(new SpecialCell(new Position(X, Y), number, this, Token.PICKACARD.name()));
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
            newDisposition.add(Token.STANDARD.name());
            standardCounter--;
        }
        while(BENCHDICESCounter > 0) {
            newDisposition.add(Token.BENCH.name());
            newDisposition.add(Token.DICES.name());
            BENCHDICESCounter--;
        }
        while(INNSPRINGCounter > 0) {
            newDisposition.add(Token.INN.name());
            newDisposition.add(Token.SPRING.name());
            INNSPRINGCounter--;
        }
        while(PICKACARDCounter > 0) {
            newDisposition.add(Token.PICKACARD.name());
            PICKACARDCounter--;
        }
        Collections.shuffle(newDisposition);
        return newDisposition;
    }

    public void putBoardComponent() {
        int numberOfLadder = this.BOARDCOMPONENTNUMBER / 2;
        int numberOfSnake = this.BOARDCOMPONENTNUMBER / 2;
        final int maxExtension = 3;
        boolean goOn = true;
        while(goOn) {
            ArrayList<Cell> assignable = this.assignableCells();
            StandardCell candidate = (StandardCell) assignable.get(new Random().nextInt(assignable.size()));
            ArrayList<StandardCell> candidates = findRange(candidate, assignable, maxExtension);
            StandardCell passive = findPassive(candidate, candidates);
            if(candidate.compareTo(passive) == -1 && numberOfLadder > 0) {
                candidate.setBoardComponent(new BoardComponent(Token.LADDER.name(), candidate.getPosition(), passive.getPosition()));
                numberOfLadder--;
            }
            if(candidate.compareTo(passive) == 1 && numberOfSnake > 0) {
                candidate.setBoardComponent(new BoardComponent(Token.SNAKE.name(), candidate.getPosition(), passive.getPosition()));
                numberOfSnake--;
            }
            if(numberOfLadder == 0 && numberOfSnake == 0) {
                goOn = false;
            }
        }
    }

    private ArrayList<StandardCell> findRange(Cell candidate, ArrayList<Cell> assignable, int maxExtension) {
        ArrayList<StandardCell> range = new ArrayList<>();
        for(Cell cell: assignable) {
            if(cell.getPosition().getX() <= candidate.getPosition().getX() + maxExtension && cell.getPosition().getX() >= candidate.getPosition().getX() - maxExtension &&
                    cell.getPosition().getY() <= candidate.getPosition().getY() + maxExtension && cell.getPosition().getY() >= candidate.getPosition().getY() - maxExtension) {
                range.add((StandardCell) cell);
            }
        }
        return range;
    }

    private StandardCell findPassive(StandardCell candidate, ArrayList<StandardCell> candidates) {
        StandardCell cell = candidates.get(new Random().nextInt(candidates.size()));
        while(cell.getPosition().getY() == candidate.getPosition().getY()) {
            cell = candidates.get(new Random().nextInt(candidates.size()));
        }
        return cell;
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
    public void addPlayer(Position position, Player player) {
        this.structure.get(this.getCell(position)).add(player);
    }

    @Override
    public void removePlayer(Position position, Player player) {
        this.structure.get(this.getCell(position)).remove(player);
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
