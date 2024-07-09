package Board.Grid.GridBoard;

import org.junit.Assert;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    int X = 6;
    int Y = 8;
    boolean cstm = true;
    Board board = new Board(X, Y, cstm);

    @org.junit.jupiter.api.Test
    void getCELLNUMBER() {
        assertEquals(X*Y, board.getCELLNUMBER());
    }

    @org.junit.jupiter.api.Test
    void getX() {
    }

    @org.junit.jupiter.api.Test
    void getY() {
    }

    @org.junit.jupiter.api.Test
    void getBOARDCOMPONENTNUMBER() {
    }

    @org.junit.jupiter.api.Test
    void getSPECIALCELLSNUMBER() {
    }

    @org.junit.jupiter.api.Test
    void getBENCHNUMBERandDICESNUMBER() {
    }

    @org.junit.jupiter.api.Test
    void getINNNUMBERandSPRINGNUMBER() {
    }

    @org.junit.jupiter.api.Test
    void getPICKACARDNUMBER() {
    }

    @org.junit.jupiter.api.Test
    void isCustom() {
    }

    @org.junit.jupiter.api.Test
    void isStandard() {
    }

    @org.junit.jupiter.api.Test
    void getAllCells() {
        assertEquals(X*Y, board.getAllCells().size());
    }

    @org.junit.jupiter.api.Test
    void getNumberCell() {
    }

    @org.junit.jupiter.api.Test
    void testToString() {
    }

    @org.junit.jupiter.api.Test
    void iterator() {
    }
}