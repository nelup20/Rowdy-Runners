package model.wallcreation;

public class WallMakerApp {
    public static void main(String[] args) {
        boolean[][] wallGridAsABoolean = returnWallGridAsABoolean(11);
        System.out.println("The space at coordinates (2,2) is " + wallGridAsABoolean[2][2]);
    }

    public static boolean[][] returnWallGridAsABoolean(int size){
        WallGridMaker wallGridMaker = new WallGridMaker(size);
        wallGridMaker.createAllWallsOnTheGrid();
        wallGridMaker.drawWallGrid();   // draw walls in terminal my x axis points downward and y axis rightward (makes no difference for starting points)
        boolean[][] wallGridAsABoolean = wallGridMaker.getWallsAsBooleanArray();
        return wallGridAsABoolean;
    }
}
