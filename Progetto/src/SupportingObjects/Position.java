package SupportingObjects;

import java.util.ArrayList;
import java.util.Objects;

public class Position implements Comparable<Position> {

    int X, Y;

    public int getX() { //width --> number of columns
        return X;
    }

    public int getY() {
        return Y;
    } //height --> row's number

    public Position(int X, int Y) {
        this.X = X;
        this.Y = Y;
    }

    public ArrayList<Position> computeDistance(Position p) {
        ArrayList<Position> distance = new ArrayList<>();
        for(int i = this.X; i <= p.X; i++) {
            for(int j = this.Y; j <= p.Y; j++) {
                distance.add(new Position(i, j));
            }
        }
        return distance;
    }

    public boolean isValid() {
        return this.X >= 0 && this.Y >= 0;
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

    @Override
    public String toString() {
        return "X=" + X + ", Y=" + Y;
    }

    @Override
    public int compareTo(Position p) {
        if(this.Y < p.Y) {
            return -1; //Y minors come first
        }
        if(this.Y == p.Y && this.X < p.X) {
            return -1; //if same Y then X minors come first
        }
        if(this.Y > p.Y) {
            return 0; //Y major come later than Y minors but first of major X
        } else {
            return 1; //Y and X major come last
        }
    }

//    public Position add(Position position) {
//        return new Position(this.X + position.X, this.Y + position.Y);
//    }
}
