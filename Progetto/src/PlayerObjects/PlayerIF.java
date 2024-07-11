package PlayerObjects;

import Patterns.StatePackage.State;
import SupportingObjects.Position;

import java.io.Serializable;

public interface PlayerIF extends Serializable {

    /**
     * This method change the player's state
     * @param playerState
     */
    void changeState(State playerState);

    /**
     * This method sets the diceResult
     * @param position
     * @return true if the player must throw the dices again (ONLY IF HE PLAYS WITH TWO DICES)
     */
    boolean throwDice(Position position);

    /**
     * Move the player on the cell at position player.getPosition() + diceResult
     */
    void move(Position newPosition) throws InterruptedException;

    /**
     * This method simulates a player's turn
     */
    void turn() throws InterruptedException;

    /**
     * This method allows the player to climb the ladder
     */
    void climbTheLadder() throws InterruptedException;

    /**
     * This method allows the player to slice on the snake
     */
    void sliceOnSnake() throws InterruptedException;

    /**
     * This method allows the player to throw again the dices
     */
    void throwAgain() throws InterruptedException;

    /**
     * This method allows the player to move again for previous dice's result
     * @param diceResult
     */
    void moveAgain(int diceResult) throws InterruptedException;

    /**
     * This method stop the player for one turn
     */
    void standStill();

    /**
     * This method stop the player for two turn
     */
    void goToSleep();

    /**
     * This method allows the player to pick a card
     */
    void pickACard() throws InterruptedException;

    /**
     * This method makes the player go backwards
     * @param plusValue
     */
    void retreat(int plusValue);

    /**
     * This method comunicates to all other player that this player has won
     */
    void win();
}
