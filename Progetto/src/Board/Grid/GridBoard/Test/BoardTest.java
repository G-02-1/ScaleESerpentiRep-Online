package Board.Grid.GridBoard.Test;

import Board.Grid.GridBoard.Board;
import SupportingObjects.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    int X = 6;
    int Y = 8;
    boolean cstm = true;
    boolean cards = true;
    Board board = new Board(X, Y, cstm, cards);

    @Test
    void getCELLNUMBER() {
        assertEquals(X*Y, board.getCELLNUMBER());
    }

    @Test
    void getBOARDCOMPONENTNUMBER() {
        boolean condition = cstm ? (int) (0.24 * (X*Y)) <= board.getBOARDCOMPONENTNUMBER() :
                (int) (0.4 * (X*Y)) <= board.getBOARDCOMPONENTNUMBER();

        if(cstm) {
            assertTrue(condition);
        } else {
            assertTrue(condition);
        }
    }

    @Test
    void getSPECIALCELLSNUMBER() {
        if(cstm) {
            assertTrue((int) (0.24 * (X*Y)) >= board.getSPECIALCELLSNUMBER());
        } else {
            assertEquals(0, board.getSPECIALCELLSNUMBER());
        }
    }

    @Test
    void getAllCells() {
        assertEquals(X*Y, board.getAllCells().size());
    }

    @Test
    void getNumberCell() {
        assertTrue(1 == board.getCell(new Position(0,0)).getNumber());
    }

    @Test
    void testToString() {
    }
}