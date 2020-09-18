package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Grid {
    public GridPane grid = new GridPane();


    public Grid(int size) {
        createGrid(size);
    }

    private void createGrid(int size) {

        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                grid.add(getBasicSquare(size), row, column);

            }
        }
    }

    private Rectangle getBasicSquare(int size){
        Rectangle square = new Rectangle(600/size, 600/size);
        square.setStroke(Color.GRAY);
        square.setFill(Color.rgb(235,231,209));
        return square;
    }

    public GridPane getGridPane() {
        return grid;
    }
}