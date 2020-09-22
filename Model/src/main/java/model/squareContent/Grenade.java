package model.squareContent;

import model.Coordinate;
import model.Player;

public class Grenade {

    private boolean isActive = false;
    private boolean isPickedUp = false;
    private Coordinate currentCoordinate;
    private Player owner;

    public Grenade(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isPickedUp() {
        return isPickedUp;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public Player getOwner() {
        return owner;
    }

    public void isPickedUp(Player player) {
        isPickedUp = true;
        currentCoordinate = null;
        owner = player;
    }

    public void placeGrenade(Coordinate coordinate) {
        currentCoordinate = coordinate;
    }

    public void activateGrenade() {
        isActive = true;
    }

    public void explode(Player player) {
        player.isHit();
    }

}