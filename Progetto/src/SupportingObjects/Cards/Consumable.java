package SupportingObjects.Cards;

import Patterns.PrototypePackage.Prototype;
import Patterns.StatePackage.State;

public interface Consumable extends Prototype {

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
