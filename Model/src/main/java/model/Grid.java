package model;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;


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
                grid.add(Square.getBasicSquare(GRID_SIZE), row, column);
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
        grid.add(Square.getPlayerSquare(player1.getID(), GRID_SIZE), player1.getCurrentCoordinate().X_COORDINATE, player1.getCurrentCoordinate().Y_COORDINATE);

    }

    public void startPositionP2(Player player2) {
        player2.setCurrentCoordinate(new Coordinate(0, GRID_SIZE - 1));
        grid.add(Square.getPlayerSquare(player2.getID(), GRID_SIZE), player2.getCurrentCoordinate().X_COORDINATE, player2.getCurrentCoordinate().Y_COORDINATE);
    }

    public void movePlayer(Player player, Coordinate newCoordinate) {
        changeCoordinatePlayer(player, newCoordinate, Square.getPlayerSquare(player.getID(), GRID_SIZE));
    }

    public void changeCoordinatePlayer(Player player, Coordinate newCoordinate, Square playerSquare) {
        Coordinate oldCoordinate = player.getCurrentCoordinate();
        player.setCurrentCoordinate(newCoordinate);
        player.addCoordinate(oldCoordinate);

        grid.add(Square.getBasicSquare(GRID_SIZE), oldCoordinate.X_COORDINATE, oldCoordinate.Y_COORDINATE);
        grid.add(playerSquare, player.getCurrentCoordinate().X_COORDINATE, player.getCurrentCoordinate().getY_COORDINATE());
    }

}