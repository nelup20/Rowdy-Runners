package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Grid {
    public final int GRID_SIZE;
    public static final Image greenCar = new Image("/greenCar.png");
    public static final Image orangeCar = new Image("/orangeCar.png");
    private GridPane grid = new GridPane();


    public Grid(int size) {
        this.GRID_SIZE = size;
        createGrid();
    }

    private void createGrid() {

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                grid.add(getBasicSquare(), row, column);
            }
        }
    }

    private Rectangle getBasicSquare() {
        Rectangle square = new Rectangle(600 / GRID_SIZE, 600 / GRID_SIZE);
        square.setStroke(Color.GRAY);
        square.setFill(Color.rgb(235, 231, 209));
        return square;
    }

    public void setPlayers(Player player1, Player player2) {
        startPositionP1(player1);
        startPositionP2(player2);
    }

    public void startPositionP1(Player player1) {
        player1.setCurrentCoordinate(new Coordinate(GRID_SIZE - 1, 0));
        Rectangle player1Square = getBasicSquare();
        player1Square.setFill(new ImagePattern(greenCar));
        grid.add(player1Square, player1.getCurrentCoordinate().X_COORDINATE, player1.getCurrentCoordinate().Y_COORDINATE);

    }

    public void startPositionP2(Player player2) {
        player2.setCurrentCoordinate(new Coordinate(0, GRID_SIZE - 1));
        Rectangle player1Square = getBasicSquare();
        player1Square.setFill(new ImagePattern(orangeCar));
        grid.add(player1Square, player2.getCurrentCoordinate().X_COORDINATE, player2.getCurrentCoordinate().Y_COORDINATE);
    }

    public GridPane getGridPane() {
        return grid;
    }
}