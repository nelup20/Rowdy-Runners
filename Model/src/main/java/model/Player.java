package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    private final String name;
    private boolean isAlive = true;
    private List<Coordinate> pastCoordinates;
    private Coordinate currentCoordinate;

    public Player(String name) {
        this.name = name;
        pastCoordinates = new ArrayList<>();
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
