package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.squareContent.Grenade;
import model.squareContent.LightTrail;

import java.util.Objects;

public class Square extends Rectangle {

    private Grenade grenade;
    private boolean wall;
    private LightTrail trail;
    private Player player;
    private Coordinate COORDINATE;

    public static final Image GREEN_CAR = new Image("/greenCar.png");
    public static final Image ORANGE_CAR = new Image("/orangeCar.png");
    public static final Image WALL = new Image("/brickwall.png");
    public static final Image GRENADE = new Image("/grenade.png");
    public static final Color SAND_COLOR = Color.rgb(235, 231, 209); //Color = Sand


    // Constructor


    public Square(int gridSize, Coordinate coordinate) {
        super(600 / gridSize, 600 / gridSize);
        this.COORDINATE = coordinate;
        this.setStroke(Color.GRAY);
        this.setFill(SAND_COLOR);
    }

    public Square(double width, double height, Paint fill, Coordinate coordinate) {
        super(width, height, fill);
        this.COORDINATE = coordinate;
    }

    public Square(double x, double y, double width, double height, Coordinate coordinate) {
        super(x, y, width, height);
        this.COORDINATE = coordinate;
    }


    // Getters & Setters

    public Grenade getGrenade() {
        return grenade;
    }

    public boolean getWall() {
        return wall;
    }

    public LightTrail getTrail() {
        return trail;
    }

    public void setTrail(LightTrail trail) {
        this.trail = trail;
    }

    public Coordinate getCOORDINATE() {
        return COORDINATE;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // Behavior
    public void getBasicSquare() {
        this.setStroke(Color.GRAY);
        this.setFill(SAND_COLOR);
        if (grenade != null && (!grenade.isActive() && !grenade.isPickedUp())) {
        this.setFill(new ImagePattern(GRENADE));
        }
    }

    public void getPlayerSquare(Player player) {
        Image carColor = player.getID() == 1 ? GREEN_CAR : ORANGE_CAR;
        this.setFill(new ImagePattern(carColor));
    }

    public void setWallFill() {
        this.setFill(new ImagePattern(WALL));
        wall = true;
    }

    public void addGrenade(Grenade grenade) {
        this.grenade = grenade;
        if (!grenade.isPickedUp()) {
            this.setFill(new ImagePattern(GRENADE));
        }
    }


    //A square is equal when they have the same coordinates:

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Square square = (Square) o;
        return Objects.equals(COORDINATE, square.COORDINATE);
    }

    @Override
    public int hashCode() {
        return Objects.hash(COORDINATE);
    }
}
