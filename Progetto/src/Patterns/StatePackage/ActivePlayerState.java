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
        if(this.o instanceof Player) {
            Player player = (Player) o;
            player.changeState(new BlockedPlayerState(player, turns));
        }
    }

    @Override
    public boolean move() {
        return true;
    }
}
