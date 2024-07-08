package SupportingObjects.Cards;

import Patterns.StatePackage.State;

public interface Consumable extends Cloneable {

    /**
     *
     * @return a code that identifies the action
     */
    int triggerEffect();

    /**
     * This method allows to change the cards' state
     * @param state
     */
    void changeState(State state);

}
