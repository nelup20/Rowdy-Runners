package model;

import javafx.scene.layout.GridPane;


public class Grid {
    public final int GRID_SIZE;
    private GridPane grid = new GridPane();


    public Grid(int size) {
        this.GRID_SIZE = size;
        createGrid();
    }

    public GridPane getGridPane() {
        return grid;
    }

    private void createGrid() {

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                grid.add(Square.createBasicSquare(GRID_SIZE), row, column);
            }
        }
    }

    public int getGridSize() {
        return GRID_SIZE;
    }



    public void setPlayers(Player player1, Player player2) {
        startPositionP1(player1);
        startPositionP2(player2);
    }

    public void startPositionP1(Player player1) {
        player1.setCurrentCoordinate(new Coordinate(GRID_SIZE - 1, 0));
        grid.add(Square.createPlayerSquare(1, GRID_SIZE), player1.getCurrentCoordinate().X_COORDINATE, player1.getCurrentCoordinate().Y_COORDINATE);

    }

    public void startPositionP2(Player player2) {
        player2.setCurrentCoordinate(new Coordinate(0, GRID_SIZE - 1));
        grid.add(Square.createPlayerSquare(2, GRID_SIZE), player2.getCurrentCoordinate().X_COORDINATE, player2.getCurrentCoordinate().Y_COORDINATE);
    }

    public void movePlayer(Player player, Coordinate newCoordinate) {
        if (player.getId() == 0) {
            changeCoordinatePlayer(player, newCoordinate, Square.createPlayerSquare(1, GRID_SIZE));
            System.out.println(player);
        } else {
            changeCoordinatePlayer(player, newCoordinate, Square.createPlayerSquare(2, GRID_SIZE));
            System.out.println(player);
        }


    }

    public void changeCoordinatePlayer(Player player, Coordinate newCoordinate, Square playerSquare){
        Coordinate oldCoordinate = player.getCurrentCoordinate();
        player.setCurrentCoordinate(newCoordinate);
        player.addCoordinate(oldCoordinate);

        grid.add(Square.createBasicSquare(GRID_SIZE), oldCoordinate.X_COORDINATE,oldCoordinate.Y_COORDINATE);
        grid.add(playerSquare, player.getCurrentCoordinate().X_COORDINATE, player.getCurrentCoordinate().getY_COORDINATE());
    }

}