package Board.Components;

import Board.SupportingObjects.Position;

import java.util.LinkedList;

public class Snake extends BoardComponent {

    private final Position ACTIVE, PASSIVE;

    public Snake(Position active, Position passive) {
        this.ACTIVE = active;
        this.PASSIVE = passive;
    }

    @Override
    public Position getActivePosition() {
        return ACTIVE;
    }

    @Override
    public Position getPassivePosition() {
        return PASSIVE;
    }

    @Override
    public LinkedList<Position> getExtension() {
        this.calculateExtension();
        return this.crossed;
    }
}