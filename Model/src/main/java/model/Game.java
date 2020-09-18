package model;

public class Game {

    public static final int MIN_GRID_SIZE = 10;
    public final Player player1;
    public final Player player2;
    public final Grid grid;

    public Game(int gridSize, Player player1, Player player2) {
        this.grid = new Grid(gridSize);
        this.player1 = player1;
        this.player2 = player2;
        grid.setPlayers(player1, player2);
    }

    public Grid getGrid() {
        return grid;
    }


}
