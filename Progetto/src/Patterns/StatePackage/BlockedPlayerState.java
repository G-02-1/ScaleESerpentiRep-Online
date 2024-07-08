package Patterns.StatePackage;

import PlayerObjects.Player;

public class BlockedPlayerState extends State {

    private int turns;

    public BlockedPlayerState(Player player) {
        super(player);
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

}
