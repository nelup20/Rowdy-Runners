package model.wallcreation;

import java.util.Arrays;

public class Wall {
    private final Coordinates start;
    private final Direction direction;
    private final int length;
    private final Coordinates[] tiles;

    public Wall(Coordinates start, Direction direction, int length) {
        tiles = setTiles(start, direction,length);
        this.start = start;
        this.direction = direction;
        this.length = length;
    }

    private Coordinates[] setTiles (Coordinates start, Direction direction, int length) {
        Coordinates[] tiles = new Coordinates[length];
        for (int i = 0; i < length; i++) {
            int xCoordinate = start.X + i * direction.vector.X;
            int yCoordinate = start.Y + i * direction.vector.Y;
            Coordinates tile = new Coordinates(xCoordinate, yCoordinate);
            tiles[i] = tile;
        }
        return tiles;
    }

    public Coordinates[] getTiles() {
        return tiles;
    }

    @Override
    public String toString() {
        return "Wall{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    public Coordinates getStart() {
        return start;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getLength() {
        return length;
    }
}
