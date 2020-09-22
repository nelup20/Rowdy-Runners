package model.wallcreation;

import java.util.Random;

public class WallGridMaker {
    private final int SIZE;
    private final static double WALL_CHANCE_PERCENTAGE = 0.20;
    private char [][] wallGrid;
    private Random rand;

    public WallGridMaker(int size) {
        this.SIZE = size;
        this.wallGrid = new char[SIZE][SIZE];
        fillWallGridWithEmptySpots();
        rand = new Random();
    }



    public void createAllWallsOnTheGrid () {
        int maximumNumberOfWalls = (int) (WALL_CHANCE_PERCENTAGE * SIZE * SIZE / 2);
        int numberOfWalls = rand.nextInt(maximumNumberOfWalls)+1;
        for (int i = 0; i < numberOfWalls; i++) {
            boolean thereIsStillPlaceForANewWall = true;
            while (thereIsStillPlaceForANewWall) {
                Wall candidateWall = createRandomWall();

                if (wallIsOnTheGridAndThereIsEnoughSpaceAround(candidateWall)) {
                    if ( ! stillWithinTotalCoveragePercentage(candidateWall)) {
                        break;
                    }
                    if (wallIsValidToBeAddedOntoTheGrid(candidateWall)) {
                        addWallOntoGrid(candidateWall);
                        thereIsStillPlaceForANewWall = false;
                    }
                }
            }
        }
    }

    private boolean stillWithinTotalCoveragePercentage (Wall candidateWall) {
        return candidateWall.getLength() + countWallTiles() <= WALL_CHANCE_PERCENTAGE * SIZE * SIZE;
    }

    private boolean wallCandidateCoversStartingSpot (Wall candidateWall) {
        boolean startingSpotCovered = false;
        Coordinates rightUpperCorner = new Coordinates(SIZE-1,0);
        Coordinates lowerLeftCorner  = new Coordinates(0,SIZE-1);
        for (Coordinates coordinates : candidateWall.getTiles()){
            if (coordinates.X == rightUpperCorner.X && coordinates.Y == rightUpperCorner.Y){
                startingSpotCovered = true;
            } else if (coordinates.X == lowerLeftCorner.X && coordinates.Y == lowerLeftCorner.Y){
                startingSpotCovered = true;

            }
        }
        return startingSpotCovered;
    }

    private boolean thereIsEnoughSpaceOnWallCandidatesLine(Wall wall) {
        Direction direction = wall.getDirection();
        Direction backwards = Direction.turnClockWise(Direction.turnClockWise(direction));
        int spacesToAdd = wall.getLength();
        Coordinates lastTileOnTheLine = wall.getStart();
        boolean onTheGrid = true;
        while (onTheGrid){
            Coordinates next = new Coordinates(lastTileOnTheLine.X+direction.vector.X, lastTileOnTheLine.Y+direction.vector.Y);
            if ( ! coordinatesArePartOfTheGrid(next) ){
                break;
            } else {
                lastTileOnTheLine = next;
            }
        }

        int spacesTaken = 0;

        for (int i= 0; i < SIZE; i++) {
            if (wallGrid[lastTileOnTheLine.X][lastTileOnTheLine.Y] == 'W'){
                spacesTaken++;
            }
            Coordinates next = new Coordinates(lastTileOnTheLine.X+backwards.vector.X,
                    lastTileOnTheLine.Y+backwards.vector.Y);
            lastTileOnTheLine = next;
        }
        if (spacesTaken + spacesToAdd <= SIZE / 2){
            System.out.println("Spaces  taken = " +  spacesTaken);
            System.out.println("Spaces to add = " + spacesToAdd);
            return true;
        }
        return false;
    }

    private int countWallTiles(){
        int tileCount = 0;
        for (int i = 0; i < wallGrid.length; i++) {
            for (int j = 0; j < wallGrid.length; j++) {
                if (wallGrid[i][j] == 'W'){
                    tileCount++;
                };
            }
        }
        return tileCount;
    }


    private boolean checkIfAdjacentSpacesHaveBeenTakenAlready (Coordinates[] coordinates) {
        boolean atLeastOneAdjacentSpaceIsTakenAlready = false;
        for (Coordinates coordinatesCandidate : coordinates) {
            boolean outOfGrid = coordinateOutOfBounds(coordinatesCandidate.X)
                    || coordinateOutOfBounds(coordinatesCandidate.Y);
            if ( ! outOfGrid) {
                char testChar = wallGrid[coordinatesCandidate.X][coordinatesCandidate.Y];

                if (testChar == 'W'){

                    atLeastOneAdjacentSpaceIsTakenAlready = true;
                }
            }
        }
        return atLeastOneAdjacentSpaceIsTakenAlready;
    }

    private boolean coordinateOutOfBounds (int coordinate) {
        return coordinate > (SIZE -1) || coordinate < 0;
    }

    private boolean coordinatesArePartOfTheGrid (Coordinates coordinates) {
        boolean toReturn = true;
        int x = coordinates.X;
        int y = coordinates.Y;

        if (coordinateOutOfBounds(coordinates.X)) {
            toReturn = false;
        } else if (coordinateOutOfBounds(coordinates.Y)){
            toReturn = false;
        }
        return toReturn;
    }

    private boolean checkIfWallIsOnTheGrid (Wall wall) {
        boolean toReturn = true;
        for (Coordinates coordinates : wall.getTiles()){
            if ( ! coordinatesArePartOfTheGrid(coordinates)){
                toReturn = false;
            }
        }
        return toReturn;
    }

    private boolean wallIsOnTheGridAndThereIsEnoughSpaceAround(Wall candidateWall) {
        return checkIfWallIsOnTheGrid(candidateWall)
                && ! checkIfAdjacentSpacesHaveBeenTakenAlready(tilesAroundAWall(candidateWall));
    }

    private boolean wallIsValidToBeAddedOntoTheGrid(Wall candidateWall) {
        return        stillWithinTotalCoveragePercentage(candidateWall)
                && thereIsEnoughSpaceOnWallCandidatesLine(candidateWall)
                && ! wallCandidateCoversStartingSpot(candidateWall);
    }

    /** this method just creates a 1D array which contains all the tiles around a wall
     *  the indexes and tile names are conforming to the diagram underneath
     *
     *  /////////// backleft (first for loop .....................)
     *  /////////// back      start  Wall    Wall     Wall  farend
     *  /////////// backright (second for loop ...................)
     *
     * @param wall
     * @return
     */
    public static Coordinates[] tilesAroundAWall (Wall wall) {
        int numberOfAdjacentTiles = 2 * wall.getTiles().length + 6;
        final int wallLength = wall.getLength();

        Direction direction         = wall.getDirection();
        Direction backwards         = Direction.turnClockWise(Direction.turnClockWise(direction));
        Direction leftTurned        = Direction.turnCounterClockWise(direction);
        Direction rightTurned       = Direction.turnClockWise(direction);

        Coordinates[] adjacentTiles = new Coordinates[numberOfAdjacentTiles];

        Coordinates start = wall.getTiles()[0];


        Coordinates back        = Coordinates.moveTowardsDirection(start, backwards, 1);
        Coordinates farEnd      = Coordinates.moveTowardsDirection(start, direction,     wallLength);
        Coordinates backLeft    = Coordinates.moveTowardsDirection(back,  leftTurned, 1);
        Coordinates backRight   = Coordinates.moveTowardsDirection(back,  rightTurned,1);

        adjacentTiles[0] = back;
        adjacentTiles[1] = backLeft;
        adjacentTiles[2] = backRight;
        adjacentTiles[3] = farEnd;

        int index = 0;

        for (int i = 1; i <= wallLength + 1; i++) {
            index = 3 + i;
            adjacentTiles[index]
                    = new Coordinates(backLeft.X + direction.vector.X*i,backLeft.Y + direction.vector.Y*i);
        }

        for (int i = 1; i <= wallLength + 1; i++){
            index = 3 + wallLength + i + 1;
            adjacentTiles[index]
                    = new Coordinates( backRight.X + direction.vector.X*i, backRight.Y + direction.vector.Y*i);
        }

        return adjacentTiles;
    }

    private Wall createRandomWall () {
        int wallOfMinimalTwoSizeRandomBound = SIZE / 2 - 1;
        int length = rand.nextInt(wallOfMinimalTwoSizeRandomBound) + 2;
        Direction direction = Direction.createRandomDirection();
        Coordinates startCoordinates = Coordinates.createRandomCoordinate(SIZE);
        return new Wall(startCoordinates, direction, length);
    }

    private void addWallOntoGrid (Wall wall) {
        Coordinates[] coordinates = wall.getTiles();
        for (Coordinates coordinatesInWall : coordinates) {
            wallGrid[coordinatesInWall.X][coordinatesInWall.Y] = 'W';
        }
    }

    public void drawWallGrid() {
        String toReturn = "";
        for (int i = 0; i < wallGrid.length; i++) {
            for (int j = 0; j < wallGrid.length; j++) {
                System.out.print(" " + wallGrid[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    private void fillWallGridWithEmptySpots() {
        for (int i = 0; i < wallGrid.length; i++) {
            for (int j = 0; j < wallGrid.length; j++) {
                wallGrid[i][j] = '.';
            }
        }
    }

    public boolean[][] getWallsAsBooleanArray () {
        boolean[][] wallGridBooleanVersion = new boolean[SIZE][SIZE];
        for (int i = 0; i < wallGrid.length; i++) {
            for (int j = 0; j < wallGrid.length; j++) {
                wallGridBooleanVersion[i][j] = false;
            }
        }
        for (int i = 0; i < wallGrid.length; i++) {
            for (int j = 0; j < wallGrid.length; j++) {
                if (wallGrid[i][j] == 'W') {
                    wallGridBooleanVersion[i][j] = true;
                }
            }
        }
        return wallGridBooleanVersion;
    }


}


