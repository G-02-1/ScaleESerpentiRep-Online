package Board.Components;

import Board.SupportingObjects.Position;

import java.util.LinkedList;

public abstract class BoardComponent implements BoardComponentIF {

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
    public boolean equals(Object o) {
        if (o == null) {return false;}
        if(this == o) {return true;}
        if(!(o instanceof BoardComponent)) {return false;}
        BoardComponentIF boardComponent = (BoardComponent) o;
        return this.getActivePosition().equals(boardComponent.getActivePosition()) && this.getPassivePosition().equals(boardComponent.getPassivePosition());
    }
}
