package model;

public class Game {
    public final int GRID_SIZE;
    public static final int MIN_GRID_SIZE = 10;
    public final Player player1;
    public final Player player2;

    public Game (int gridSize, Player player1, Player player2){
        this.GRID_SIZE = gridSize;
        this.player1 = player1;
        this.player2 = player2;
    }
}
