package Board.SupportingObjects;

import java.util.Objects;

public class Position {

    int X, Y;

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }

    public Position(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    @Override
    public boolean equals(Object o) {
        if(o == null) return false;
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return getX() == position.getX() && getY() == position.getY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
