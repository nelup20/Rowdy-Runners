package model.wallcreation;

import java.util.Arrays;

/** This class is just for experimenting and checking
 *
 */

public class PlayGround {
    public static void main(String[] args) {

        Wall wall = new Wall(new Coordinates(4,4),Direction.RIGHT,3);
        Coordinates[] tilesAround = WallGridMakerBis.tilesAroundAWall(wall);
        System.out.println(wall);
        System.out.println(Arrays.toString(tilesAround));
        char[] charArray = new char[1];
        charArray[0] = 'W';
        Direction up = Direction.UP;
        Direction right = Direction.turnClockWise(up);
        System.out.println("Right = " + right);
        System.out.println('.' == charArray[0]);




    }

}
