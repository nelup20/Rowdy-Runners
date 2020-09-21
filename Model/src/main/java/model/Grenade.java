package model;

public class Grenade {
    private boolean isActive = false;
    private boolean isPickedUp = false;
    private Coordinate currentCoordinate;
    private Player owner;

    public Grenade(Coordinate currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
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
