package Patterns.StatePackage;

import Exceptions.InvalidStateInstantiationException;
import PlayerObjects.Player;

public class BlockedPlayerState extends State {

    private int turns;

    public BlockedPlayerState(Player player) {
        if(!(o instanceof Player)) {
            throw new InvalidStateInstantiationException("Cannot instantiate an BlockedPlayerState for a not Player object");
        } else {
            this.o = (Player) o;
        }
    }

    @Override
    public void setActiveState() {
        //Do nothing
    }

    @Override
    public void setBlockedState(int turns) {
        if(this.o instanceof Player) {
            Player player = (Player) o;
            this.turns = turns;
            player.changeState(new BlockedPlayerState(player));
        }
    }

    @Override
    public boolean move() {
        if(turns > 0) {
            turns--;
            return false;
        }
        else {
            if(this.o instanceof Player) {
                Player player = (Player) o;
                player.changeState(new ActivePlayerState(player));
            }
            return true;
        }
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
