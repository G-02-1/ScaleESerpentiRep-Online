package Patterns.StatePackage;

import PlayerObjects.Player;

public class ActivePlayerState extends State {

    public ActivePlayerState(Player player) {
        super(player);
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

}
