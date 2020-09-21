package model.wallcreation;

public class WallMakerApp {
    public static void main(String[] args) {
        boolean[][] wallGridAsABoolean = returnWallGridAsABoolean(12);
        System.out.println("The space at coordinates (2,2) is " + wallGridAsABoolean[2][2]);
    }

<<<<<<< HEAD
    public static boolean[][] returnWallGridAsABoolean(){
        WallGridMakerBis wallGridMakerBis = new WallGridMakerBis();
        wallGridMakerBis.createAllWallsOnTheGrid();
        wallGridMakerBis.drawWallGrid();   // draw walls in terminal my x axis points downward and y axis rightward (makes no difference for starting points)
        boolean[][] wallGridAsABoolean = wallGridMakerBis.getWallsAsBooleanArray();
=======
    public static boolean[][] returnWallGridAsABoolean(int size){
        WallGridMaker wallGridMaker = new WallGridMaker(size);
        wallGridMaker.createAllWallsOnTheGrid();
        wallGridMaker.drawWallGrid();   // draw walls in terminal my x axis points downward and y axis rightward (makes no difference for starting points)
        boolean[][] wallGridAsABoolean = wallGridMaker.getWallsAsBooleanArray();
>>>>>>> 2111f37da88b27a674b7f19803a998e4793cc6fc
        return wallGridAsABoolean;
    }
}
