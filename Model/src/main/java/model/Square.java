package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import model.squareContent.*;

public class Square extends Rectangle {

    private Grenade grenade;
    private Wall wall;
    private LightTrail trail;
    private Player player;

    public static final Image greenCar = new Image("/greenCar.png");
    public static final Image orangeCar = new Image("/orangeCar.png");



    // Constructor
    public Square() {

    }

    public Square(double width, double height) {
        super(width, height);
    }

    public Square(double width, double height, Paint fill) {
        super(width, height, fill);
    }

    public Square(double x, double y, double width, double height) {
        super(x, y, width, height);
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


    // Behavior
    public static Square createBasicSquare(int gridSize){
        Square square = new Square(600 / gridSize, 600 / gridSize);
        square.setStroke(Color.GRAY);
        square.setFill(Color.rgb(235, 231, 209));
        return square;
    }

    public static Square createPlayerSquare(int playerNumber, int gridSize){
        Image carColor = playerNumber == 1 ? greenCar : orangeCar;

        Square playerSquare = Square.createBasicSquare(gridSize);
        playerSquare.setFill(new ImagePattern(carColor));
        return playerSquare;
    }
}
