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

    @Override
    public String toString() {
        return "(" + X + " , " + Y + ")";
    }
}
