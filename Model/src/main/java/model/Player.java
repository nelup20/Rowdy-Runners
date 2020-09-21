package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    public final String name;
    public final int id;
    private boolean isAlive = true;
    private List<Coordinate> pastCoordinates;
    private Coordinate currentCoordinate;
    public static int idCounter = 0;
    private boolean isStunned = false;

    public Player(String name) {
        this.name = name;
        this.id = idCounter;
        idCounter++;
        pastCoordinates = new ArrayList<>();

    }

    public int getId() {
        return id;
    }

    public void setCurrentCoordinate(Coordinate coordinate){
        this.currentCoordinate = coordinate;
    }

    public void addCoordinate(Coordinate coordinate){
        pastCoordinates.add(coordinate);
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void isHit() {
        isStunned = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }

}
