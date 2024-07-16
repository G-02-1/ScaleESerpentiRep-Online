package Patterns.StatePackage;

import Exceptions.InvalidStateInstantiationException;
import PlayerObjects.Player;

public class BlockedPlayerState extends State {

    private int turns;

    public BlockedPlayerState(Object o, int turns) {
        if(!(o instanceof Player)) {
            throw new InvalidStateInstantiationException("Cannot instantiate an BlockedPlayerState for a not Player object");
        } else {
            this.o = (Player) o;
            this.turns = turns;
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
        if(turns != 0) {
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
}
