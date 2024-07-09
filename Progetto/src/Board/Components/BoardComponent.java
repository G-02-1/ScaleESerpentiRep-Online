package Board.Components;

import Exceptions.BoardComponentInstantiationException;
import SupportingObjects.Position;

import java.util.LinkedList;

public class BoardComponent implements BoardComponentIF {

    private enum Type {
        LADDER, SNAKE;
    }

    private final Type type;
    private final Position ACTIVE, PASSIVE;

    public BoardComponent(String type, Position ACTIVE, Position PASSIVE) {
        if(type.equalsIgnoreCase("LADDER")) {
            if(ACTIVE.compareTo(PASSIVE) >= 0) throw new BoardComponentInstantiationException("Impossible to instantiate a LADDER with ACTIVE grater than PASSIVE position");
            this.type = Type.LADDER;
            this.ACTIVE = ACTIVE;
            this.PASSIVE = PASSIVE;
        }
        else if(type.equalsIgnoreCase("SNAKE")) {
            if(ACTIVE.compareTo(PASSIVE) < 0) throw new BoardComponentInstantiationException("Impossible to instantiate a SNAKE with PASSIVE grater than ACTIVE position");
            this.type = Type.SNAKE;
            this.ACTIVE = ACTIVE;
            this.PASSIVE = PASSIVE;
        }
        else {
            throw new BoardComponentInstantiationException("Invalid type: " + type);
        }
    }

    @Override
    public boolean isLadder() {
        return this.type.equals(Type.LADDER);
    }

    @Override
    public boolean isSnake() {
        return this.type.equals(Type.SNAKE);
    }

    @Override
    public Position getActivePosition() {
        return ACTIVE;
    }

    @Override
    public Position getPassivePosition() {
        return PASSIVE;
    }

    LinkedList<Position> crossed = new LinkedList<>();

     public void calculateExtension() {
        Position active = this.getActivePosition();
        Position passive = this.getPassivePosition();
        for(int i = active.getX(); i <= passive.getX(); i++) {
            for(int j = active.getY(); i <= passive.getY(); j++) {
                crossed.add(new Position(i, j));
            }
        }
    }

    @Override
    public LinkedList<Position> getExtension() {
        this.calculateExtension();
        return this.crossed;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {return false;}
        if(this == o) {return true;}
        if(!(o instanceof BoardComponent)) {return false;}
        BoardComponentIF boardComponent = (BoardComponent) o;
        return this.getActivePosition().equals(boardComponent.getActivePosition()) && this.getPassivePosition().equals(boardComponent.getPassivePosition());
    }
}
