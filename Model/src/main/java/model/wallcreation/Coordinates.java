package model.wallcreation;

import java.util.Random;

public class Coordinates {
    public final int X;
    public final int Y;

    public Coordinates(int x, int y) {
        X = x;
        Y = y;
    }

    public static Coordinates createRandomCoordinate (int bound) {
        Random rand = new Random();
        int x = rand.nextInt(bound);
        int y = rand.nextInt(bound);
        return new Coordinates(x,y);
    }

    public static Coordinates moveTowardsDirection(Coordinates coordinates, Direction direction, int numberOfTimes){
        return new Coordinates (
                coordinates.X + direction.vector.X * numberOfTimes,
                coordinates.Y + direction.vector.Y*numberOfTimes
                                );
    }

    @Override
    public String toString() {
        return "(" + X + " , " + Y + ")";
    }
}
