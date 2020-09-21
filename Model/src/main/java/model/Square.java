package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.squareContent.Grenade;
import model.squareContent.LightTrail;
import model.squareContent.Wall;

import java.util.Objects;

public class Square extends Rectangle {

    private Grenade grenade;
    private Wall wall;
    private LightTrail trail;
    private Player player;
    private Coordinate COORDINATE;

    public static final Image greenCar = new Image("/greenCar.png");
    public static final Image orangeCar = new Image("/orangeCar.png");



    // Constructor


    public Square(int gridSize, Coordinate coordinate) {
        super(600 / gridSize, 600 / gridSize);
        this.COORDINATE = coordinate;
        this.setStroke(Color.GRAY);
        this.setFill(Color.rgb(235, 231, 209)); //Color = Sand
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

    public void setGrenade(Grenade grenade) {
        this.grenade = grenade;
    }

    public Wall getWall() {
        return wall;
    }

    public void setWall(Wall wall) {
        this.wall = wall;
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

    // Behavior
    public void getBasicSquare(){
        this.setStroke(Color.GRAY);
        this.setFill(Color.rgb(235, 231, 209));
    }

    public void getPlayerSquare(int playerNumber){
        Image carColor = playerNumber == 1 ? greenCar : orangeCar;

        getBasicSquare();
        this.setFill(new ImagePattern(carColor));
    }

    //A square is equal when the have the same coordinates:

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
