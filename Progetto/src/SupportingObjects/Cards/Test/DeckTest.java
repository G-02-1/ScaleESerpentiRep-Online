package SupportingObjects.Cards.Test;

import Board.Grid.GridBoard.Board;
import Exceptions.InvalidDeckInstantiationException;
import SupportingObjects.Cards.Card;
import SupportingObjects.Cards.Deck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    int X = 6;
    int Y = 8;
    boolean cstm = true;
    boolean specialCard = true;
    Board board = new Board(X, Y, cstm);
    int pickACardCells = this.board.getPICKACARDNUMBER();

    @Test
    void generateDeck() {
        if(!this.cstm) {
            assertThrows(InvalidDeckInstantiationException.class, () -> {
                Deck.INSTANCE.generateDeck(board, specialCard);
            });
        } else {
            Deck.INSTANCE.generateDeck(board, specialCard);
            assertTrue(Deck.INSTANCE.isACTIVE());
        }
    }

    @Test
    void getNumberOfCards() {
        if(!this.cstm) {
            assertThrows(InvalidDeckInstantiationException.class, () -> {
                Deck.INSTANCE.generateDeck(board, specialCard);
            });
        } else {
            int expectedNumberOfCards = (int) ((0.33 * 2 * this.pickACardCells) + (0.16 * this.pickACardCells) +
                    (0.1 * this.pickACardCells) + (0.8 * this.pickACardCells));
            boolean condition = Deck.INSTANCE.getNumberOfCards() >= expectedNumberOfCards;
            assertTrue(condition);
        }
    }

    //there are no pickACard() or putBottom() tests because by chance, using clone(), all cards are instances of the same class
}