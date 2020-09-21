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
        boolean gridIsNotFinishedYet = true;
        int maximumNumberOfWalls = (int) (WALL_CHANCE_PERCENTAGE * SIZE * SIZE / 2);
        int numberOfWalls = rand.nextInt(maximumNumberOfWalls)+1;
        for (int i = 0; i < numberOfWalls; i++) {
            boolean thereIsStillPlaceForANewWall = true;
            while (thereIsStillPlaceForANewWall) {
                Wall candidateWall = createRandomWall();
                Coordinates[] tilesAroundAWall = tilesAroundAWall(candidateWall);

                boolean candidateIsWithinBoundaries   =
                        checkIfWallIsOnTheGrid(candidateWall);
                boolean candidateHasEnoughSpaceAround =
                        ! checkIfAdjacentSpacesHaveBeenTakenAlready(tilesAroundAWall
                                (candidateWall));

                if (       candidateIsWithinBoundaries
                        && candidateHasEnoughSpaceAround
                        ) {
                    System.out.println("Total Coverage okay " + stillWithinTotalCoveragePercentage(candidateWall));
                    System.out.println("Line  Coverage okay " + thereIsEnoughSpaceOnWallCandidatesLine(candidateWall));
                    if ( ! stillWithinTotalCoveragePercentage(candidateWall)) {
                        break;
                    }
                    if (       stillWithinTotalCoveragePercentage(candidateWall)
                            && thereIsEnoughSpaceOnWallCandidatesLine(candidateWall)
                            && ! wallCandidateCoversStartingSpot(candidateWall)) {   //////////////////////
                        addWallOntoGrid(candidateWall);
                        thereIsStillPlaceForANewWall = false;

                    }
                }

                // TODO
                // check if wall covers starting spot : (0,9) and (9,0)
                // check if wall coverage percentage not exceeded
                // check if length bounds are okey : MAY NOT BE OFFGRID ANYMORE !!!!
            }

        }

    }

    private boolean stillWithinTotalCoveragePercentage (Wall candidateWall) {
        return candidateWall.getLength() + countWallTiles() <= WALL_CHANCE_PERCENTAGE * SIZE * SIZE;
    }

    private boolean wallCandidateCoversStartingSpot (Wall candidateWall) {
        boolean startingSpotCovered = false;
        Coordinates rightUpperCorner = new Coordinates(9,0);
        Coordinates lowerLeftCorner  = new Coordinates(0,9);
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
        return coordinate > 9 || coordinate < 0;
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

    public static Coordinates[] tilesAroundAWall (Wall wall) {
        int numberOfAdjacentTiles = 2 * wall.getTiles().length + 6;
        final int length = wall.getLength();

        Direction direction         = wall.getDirection();
        Direction leftTurned        = Direction.turnCounterClockWise(direction);
        Direction rightTurned       = Direction.turnClockWise(direction);

        Coordinates[] adjacentTiles = new Coordinates[numberOfAdjacentTiles];

        Coordinates start = wall.getTiles()[0];
        Coordinates back        = new Coordinates(start.X - direction.vector.X,         start.Y - direction.vector.Y );
        Coordinates farEnd      = new Coordinates(start.X + direction.vector.X * length,start.Y + direction.vector.Y*length);
        Coordinates backLeft    = new Coordinates(back.X  + leftTurned.vector.X,        back.Y  + leftTurned.vector.Y);
        Coordinates backRight   = new Coordinates(back.X  + rightTurned.vector.X,       back.Y  + rightTurned.vector.Y);

        adjacentTiles[0] = back;
        adjacentTiles[1] = backLeft;
        adjacentTiles[2] = backRight;
        adjacentTiles[3] = farEnd;

        int index = 0;

        for (int i = 1; i <= length + 1; i++) {
            index = 3 + i;
            adjacentTiles[index]
                    = new Coordinates(backLeft.X + direction.vector.X*i,backLeft.Y + direction.vector.Y*i);
        }

        for (int i = 1; i <= length + 1; i++){
            index = 3 + length + i + 1;
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
