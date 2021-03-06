package model;

import model.squareContent.Grenade;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player {
    public static int idCounter = 0;
    public static final int STUNNED_TURNS = 3;

    public final String NAME;
    public final int ID;

    private boolean isAlive = true;
    private List<Coordinate> pastCoordinates;
    private Coordinate currentCoordinate;
    private Grenade[] grenades;
    private int turnsStunned;
    private int turnCount = 0;
    private int inventoryCounter = 0;
    private boolean isStunned = false;

    public Player(String name) {
        this.NAME = name;
        this.ID = idCounter;
        grenades = new Grenade[6];
        idCounter++;
        pastCoordinates = new ArrayList<>();

    }

    public int getID() {
        return ID;
    }

    public int getTurnCount(){
        return turnCount;
    }

    public void increaseTurnCount(){
        turnCount += 1;
    }

    public void setCurrentCoordinate(Coordinate coordinate) {
        this.currentCoordinate = coordinate;
    }

    public void addCoordinate(Coordinate coordinate) {
        pastCoordinates.add(coordinate);
    }

    public boolean addGrenade(Grenade grenade){
        if(inventoryCounter < grenades.length){
            grenades[inventoryCounter] = grenade;
            inventoryCounter++;
            return true;
        }
        return false;
    }

    public Coordinate getCurrentCoordinate() {
        return currentCoordinate;
    }

    public List<Coordinate> getPastCoordinates() {
        return pastCoordinates;
    }

    public void isHit() {
        turnsStunned = STUNNED_TURNS;
        isStunned = true;
    }

    public boolean hasItems(){
        return inventoryCounter > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(NAME, player.NAME);
    }

    @Override
    public int hashCode() {
        return Objects.hash(NAME);
    }

    @Override
    public String toString() {
        return NAME;
    }

    public Grenade getGrenade() {
        Grenade result;
        inventoryCounter--;
        result = grenades[inventoryCounter];
        grenades[inventoryCounter] = null;
        return result;
    }

    public boolean isStunned(){
        boolean result = false;
        if(turnsStunned >= 0 ){
            result = true;
            turnsStunned--;
        }else{
            isStunned = false;
        }
        return result;
    }
}
