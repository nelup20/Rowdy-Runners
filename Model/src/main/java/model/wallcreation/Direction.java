package model.wallcreation;

import java.util.Random;

public enum Direction {
    UP (new Coordinates(-1,0)),
    RIGHT (new Coordinates(0,1)),
    LEFT (new Coordinates(0,-1)),
    DOWN (new Coordinates(1,0));

    public final Coordinates vector;



    Direction(Coordinates vector) {
        this.vector = vector;
    }

    public static Direction createRandomDirection () {
        Random rand = new Random();
        Direction direction = Direction.UP;
        int choice = rand.nextInt(4);
        switch (choice) {
            case 0 : direction = UP;        break;
            case 1 : direction = DOWN;      break;
            case 2 : direction = LEFT;      break;
            case 3 : direction = RIGHT;     break;
        }

        return direction;
    }

    public static Direction turnClockWise (Direction direction) {
        Direction turnedDirection = UP;
        switch (direction) {
            case UP         : turnedDirection = RIGHT;  break;
            case RIGHT      : turnedDirection = DOWN;   break;
            case DOWN       : turnedDirection = LEFT;   break;
            case LEFT       : turnedDirection = UP;     break;
        }
        return turnedDirection;
    }

    public static Direction turnCounterClockWise (Direction direction) {
        Direction turnedDirection = UP;
        switch (direction) {
            case UP         : turnedDirection = LEFT;       break;
            case RIGHT      : turnedDirection = UP;         break;
            case DOWN       : turnedDirection = RIGHT;      break;
            case LEFT       : turnedDirection = DOWN;       break;
        }
        return turnedDirection;
    }




}
