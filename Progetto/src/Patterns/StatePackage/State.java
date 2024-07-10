package Patterns.StatePackage;

public abstract class State {

     protected Object o;

    /**
     * This method sets the player's state to ACTIVE
     */
    public abstract void setActiveState();

    /**
     * This method sets the player's state to BLOCKED
     */
    public abstract void setBlockedState(int turns);

    /**
     *
     * @return true if the player can throw
     */
    public abstract boolean move();

    /**
     * This method sets the simulation's state to communication state
     */
    public abstract void setAutomaticModeState();

    /**
     * This method sets the simulation's state to stopped state
     */
    public abstract void setManualModeState();

    /**
     *
     * @return true if the simulation can stop
     */
    public abstract boolean manual();
}
