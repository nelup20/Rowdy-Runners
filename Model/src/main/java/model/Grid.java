package model;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import model.wallcreation.WallGridMaker;
import model.wallcreation.WallMakerApp;

import java.util.HashSet;
import java.util.Set;


public class Grid {
    public final int GRID_SIZE;
    private Set<Square> squares = new HashSet<>();
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
                Square square = new Square(GRID_SIZE, new Coordinate(row, column));
                addSquare(square);
                grid.add(square, row, column);
            }
        }
        addWalls();
    }

    private void addWalls(){
        boolean[][] wallGrid = WallMakerApp.returnWallGridAsABoolean(GRID_SIZE);
        for (int row = 0; row < wallGrid.length; row++){
            for (int column = 0; column < wallGrid.length; column++){
                if(wallGrid[row][column]){
                    getSquare(new Coordinate(column, row)).setWallFill();
                }
            }
        }

    }

    public void addSquare(Square square) {
        squares.add(square);
    }

    public int getGridSize() {
        return GRID_SIZE;
    }

    public Square getSquare(Coordinate coordinate){
        for (Square square:squares) {
           if (square.getCOORDINATE().equals(coordinate)){
               return square;
           }
        }
        throw new NullPointerException("Square with " + coordinate + " is not part of the board and impossible to use.");
    }


    /*
    Setting up the start position for both players.
    */
    public void setPlayers(Player player1, Player player2) {
        startPositionP1(player1);
        startPositionP2(player2);
    }

    public void startPositionP1(Player player1) {
        player1.setCurrentCoordinate(new Coordinate(GRID_SIZE - 1, 0));
        getSquare(player1.getCurrentCoordinate()).getPlayerSquare(player1);
    }

    public void startPositionP2(Player player2) {
        player2.setCurrentCoordinate(new Coordinate(0, GRID_SIZE - 1));
        getSquare(player2.getCurrentCoordinate()).getPlayerSquare(player2);
    }

    public void movePlayer(Player player, Coordinate newCoordinate) {
        Coordinate oldCoordinate = player.getCurrentCoordinate();
        player.setCurrentCoordinate(newCoordinate);
        player.addCoordinate(oldCoordinate);
        getSquare(oldCoordinate).getBasicSquare();
        getSquare(player.getCurrentCoordinate()).getPlayerSquare(player);
    }
}