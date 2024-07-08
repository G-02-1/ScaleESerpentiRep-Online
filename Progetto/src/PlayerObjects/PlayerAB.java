package PlayerObjects;

import Board.Grid.GridBoard.Board;
import SupportingObjects.Position;
import Patterns.ObserverComunication.Manager;

import java.util.Objects;

public abstract class PlayerAB extends Manager implements PlayerIF {

    protected int id;

    protected Position position;

    protected Board board;
    protected int dicesNumber;

    public PlayerAB(int id, Board board, int diceNumber) {
        this.id = id;
        this.board = board;
        this.dicesNumber = diceNumber;
        this.position = new Position(0,0);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof PlayerAB)) return false;
        PlayerAB player = (PlayerAB) o;
        return Objects.equals(position, player.position) && Objects.equals(board, player.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, board, dicesNumber);
    }

    @Override
    public String toString() {
        return "Player's id: " + this.id + ", at position: " + this.position.toString();
    }
}
