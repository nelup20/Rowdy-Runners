package model;

import java.util.Objects;

public class Coordinate {
    public final int X_COORDINATE;
    public final int Y_COORDINATE;

    public Coordinate(int xCoordinate, int yCoordinate) {
        this.X_COORDINATE = xCoordinate;
        this.Y_COORDINATE = yCoordinate;
    }

    public int getX_COORDINATE() {
        return X_COORDINATE;
    }

    public int getY_COORDINATE() {
        return Y_COORDINATE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return X_COORDINATE == that.X_COORDINATE &&
                Y_COORDINATE == that.Y_COORDINATE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X_COORDINATE, Y_COORDINATE);
    }
}
