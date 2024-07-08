package Patterns.StatePackage;

public abstract class State {

     protected Object o;
     public State(Object o) {
            this.o = o;
        }

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

}
