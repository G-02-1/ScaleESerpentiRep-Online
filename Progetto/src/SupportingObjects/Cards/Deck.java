package SupportingObjects.Cards;

import Board.Grid.GridBoard.Board;

import java.util.ArrayList;
import java.util.Collections;

public enum Deck { //SINGLETON

    INSTANCE;

    private int numberOfCards;

    private ArrayList<Card> cards;

    private Board board;

    /*
    I will set the number of cards like this:
    a BENCH one and a DICES one for each special cell;
    a INN one for every 2 special cells;
    an SPRING one for every 3 special cells;
    a PARKINGTERM one  for every 4.
    To obtain a BENCH card or a DICES card with 32.78% (33%) of probability, a INN card with 16.39% (16%) of probability
    an SPRING card with 9.83% (10%) of probability and, if the observer wants, a PARKINGTERM card with 7.69% (8%) of probability.
     */

    public void GenerateDeck(Board board, boolean specialCard) {
        this.cards = new ArrayList<>();
        this.board = board;
        int numberOfSpecialCells = this.board.getSpecialCellsNumber();
        int numberOfBenchAndDices = 2 * numberOfSpecialCells;
        int numberOfInn = numberOfSpecialCells / 2;
        int numberOfSpring = numberOfSpecialCells / 3;
        int numberOfParkingTerm = 0;
        if(specialCard) {numberOfParkingTerm = numberOfSpecialCells / 4;}
        this.numberOfCards = numberOfBenchAndDices + numberOfInn + numberOfSpring + numberOfParkingTerm;

        /*
        Since the number of special cells is equal to the number of DICES cards and is equal to the number of BENCH cards
        and obviously is the highest I will use one of them as upperbound
         */
        this.fillDeck(numberOfSpecialCells, numberOfInn, numberOfSpring, numberOfParkingTerm);
    }

    private void fillDeck(int bound, int numberOfInn, int numberOfSpring, int numberOfParkingTerm) {
        Card cardBENCH = new Card("BENCH");
        Card cardDICES = new Card("DICES");
        Card cardINN = new Card("INN");
        Card cardSPRING = new Card("SPRING");
        Card cardPARKINGTERM = new Card("PARKINGTERM");
        for(int i = 0; i < bound; i++) {
            cards.add(cardBENCH.clone());
            cards.add(cardDICES.clone());
            if(i < numberOfInn) {
                cards.add(cardINN.clone());
            }
            if(i < numberOfSpring) {
                cards.add(cardSPRING.clone());
            }
            if(i < numberOfParkingTerm) {
                cards.add(cardPARKINGTERM.clone());
            }
        }
        Collections.shuffle(cards); //This method allows to shuffle the cards' deck
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    /**
     * This method allows to put a card to the bottom of the deck
     * @param card
     */
    public void putBottom(Card card) {
        cards.addLast(card);
    }

    /**
     *
     * @return the picked card
     */
    public Card pickACard() {
        return cards.removeFirst();
    }

}
