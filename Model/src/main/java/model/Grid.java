package model;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import model.squareContent.Grenade;
import model.wallcreation.Wall;
import model.wallcreation.WallGridMaker;
import model.wallcreation.WallMakerApp;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;


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

    public Set<Square> getSquares() {
        return squares;
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
        placeItems();
    }

    private void addWalls() {
        boolean[][] wallGrid = WallMakerApp.returnWallGridAsABoolean(GRID_SIZE);
        for (int row = 0; row < wallGrid.length; row++) {
            for (int column = 0; column < wallGrid.length; column++) {
                if (wallGrid[row][column]) {
                    getSquare(new Coordinate(column, row)).setWallFill();
                }
            }
        }

    }

    private void placeItems() {
        int itemsToPlace = GRID_SIZE / 3;
        int itemsPlaced = 0;
        while (itemsPlaced < itemsToPlace) {
            Coordinate coordinate = new Coordinate(new Random().nextInt(GRID_SIZE), new Random().nextInt(GRID_SIZE));
            if (checkEmptySquare(coordinate)) {
                getSquare(coordinate).addGrenade(new Grenade());
                itemsPlaced++;
            }
        }
    }

    private boolean checkEmptySquare(Coordinate coordinate) {
        return (!getSquare(coordinate).getWall() && getSquare(coordinate).getPlayer() == null);
    }

    public void addSquare(Square square) {
        squares.add(square);
    }

    public int getGridSize() {
        return GRID_SIZE;
    }

    public Square getSquare(Coordinate coordinate) {
        for (Square square : squares) {
            if (square.getCOORDINATE().equals(coordinate)) {
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
        getSquare(player1.getCurrentCoordinate()).setPlayer(player1);
    }

    public void startPositionP2(Player player2) {
        player2.setCurrentCoordinate(new Coordinate(0, GRID_SIZE - 1));

        getSquare(player2.getCurrentCoordinate()).getPlayerSquare(player2);
        getSquare(player2.getCurrentCoordinate()).setPlayer(player2);
    }

    public void movePlayer(Player player, Coordinate newCoordinate) {
        Coordinate oldCoordinate = player.getCurrentCoordinate();

        player.setCurrentCoordinate(newCoordinate);
        player.addCoordinate(oldCoordinate);

        getSquare(oldCoordinate).getBasicSquare();
        getSquare(oldCoordinate).setPlayer(null);

        getSquare(player.getCurrentCoordinate()).getPlayerSquare(player);
        getSquare(player.getCurrentCoordinate()).setPlayer(player);
    }

    private Predicate<Square> grenadeNotActive = square -> !square.getGrenade().isActive();
    private Predicate<Square> grenadeNotNull = square -> square.getGrenade() != null;
    private Predicate<Square> grenadeIsPickedUp = square -> square.getGrenade().isPickedUp();


    public void setGrenadeActive(Player player) {
        List<Square> squaresWithGrenades = squares.stream().filter(grenadeNotNull.and(grenadeNotActive)).filter(grenadeIsPickedUp).collect(Collectors.toList());
        if (squaresWithGrenades.size() > 0) {
            for (Square square : squaresWithGrenades) {
                if (!player.getCurrentCoordinate().equals(square.getCOORDINATE())) {
                    square.getGrenade().activateGrenade();
                    System.out.println(square.getGrenade().isActive());
                }
                System.out.println(square.getGrenade().isActive());
            }
        }
    }
}