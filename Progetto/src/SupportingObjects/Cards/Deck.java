package SupportingObjects.Cards;

import Board.Grid.GridBoard.Board;
import Exceptions.InvalidDeckInstantiationException;
import SupportingObjects.Token;

import java.util.ArrayList;
import java.util.Collections;

public enum Deck {

    INSTANCE;
    private int numberOfCards, picked;
    private ArrayList<Card> cards;
    private Board board;
    private boolean ACTIVE;

    /*
    I will set the number of cards like this:
    a BENCH one and a DICES one for each pick a card cell;
    a INN one for every 2 pick a card cell;
    an SPRING one for every 3 pick a card cell;
    a PARKINGTERM one for every 4.
    To obtain a BENCH card or a DICES card with 32.78% (33%) of probability, a INN card with 16.39% (16%) of probability
    an SPRING card with 9.83% (10%) of probability and, if the observer wants, a PARKINGTERM card with 7.69% (8%) of probability.
     */

    public void generateDeck(Board board, boolean specialCard) {
        if(board.isCustom()) {
            this.cards = new ArrayList<>();
            this.board = board;
            int numberOfPickACardCells = this.board.getSpecialCellsNumber();
            if (numberOfPickACardCells == 0) {
                this.ACTIVE = false;
                throw new InvalidDeckInstantiationException("Cannot create a Deck of cards if there isn't not even a pick a card cell");
            }
            this.picked = 0;
            this.ACTIVE = true;
            int numberOfBenchAndDices = 2 * numberOfPickACardCells;
            int numberOfInn = numberOfPickACardCells / 2;
            int numberOfSpring = numberOfPickACardCells / 3;
            int numberOfParkingTerm = 0;
            if(specialCard) {
                numberOfParkingTerm = numberOfPickACardCells / 4;
            }
            this.numberOfCards = numberOfBenchAndDices + numberOfInn + numberOfSpring + numberOfParkingTerm;

            /*
            Since the number of pick a card cell cells is equal to the number of DICES cards and is equal to the number of BENCH cards
            and obviously is the highest I will use one of them as upperbound
             */
            this.fillDeck(numberOfPickACardCells, numberOfInn, numberOfSpring, numberOfParkingTerm);
        } else {
            this.ACTIVE = false;
            throw new InvalidDeckInstantiationException("Cannot instantiate a cards'Deck for a Standard Board");
        }
    }

    private void fillDeck(int bound, int numberOfInn, int numberOfSpring, int numberOfParkingTerm) {
        Card cardBENCH = new Card(Token.BENCH.name());
        Card cardDICES = new Card(Token.DICES.name());
        Card cardINN = new Card(Token.INN.name());
        Card cardSPRING = new Card(Token.SPRING.name());
        Card cardPARKINGTERM = new Card(Token.PARKINGTERM.name());
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

    public boolean isACTIVE() {
        return ACTIVE;
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
        this.picked++;
        Card pickedCard = cards.removeFirst();
        if(this.picked == this.getNumberOfCards()) {
            Collections.shuffle(this.cards);
            this.picked = 1;
        }
        return pickedCard;
    }
} //SINGLETON