package Patterns.StatePackage;

import Exceptions.InvalidStateInstantiationException;
import PlayerObjects.Player;

public class ActivePlayerState extends State {

    public ActivePlayerState(Object o) {
        if(!(o instanceof Player)) {
            throw new InvalidStateInstantiationException("Cannot instantiate an ActivePlayerState for a not Player object");
        } else {
            this.o = (Player) o;
        }
    }

    @Override
    public void setActiveState() {
        if(this.o instanceof Player) {
            Player player = (Player) o;
            player.changeState(new ActivePlayerState(player));
        }
    }

    @Override
    public void setBlockedState(int turns) {
        //Do nothing
    }

    @Override
    public boolean move() {
        return true;
    }

    @Override
    public void setAutomaticModeState() {
        //Simulation not Player, so do nothing
    }

    @Override
    public void setManualModeState() {
        //Simulation not Player, so do nothing
    }

    @Override
    public boolean manual() {
        //Simulation not Player, so return false
        return false;
    }

}
