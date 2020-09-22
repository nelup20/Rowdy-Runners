package model;

public class Game {

    public static final int MIN_GRID_SIZE = 10;
    public final Player player1;
    public final Player player2;
    public final Grid grid;
    private Player currentPlayer;

    public Game(int gridSize, Player player1, Player player2) {
        this.grid = new Grid(gridSize);
        this.player1 = player1;
        this.player2 = player2;
        grid.setPlayers(player1, player2);
        currentPlayer = player1;
        System.out.println(player1.getCurrentCoordinate());
    }

    public Grid getGrid() {
        return grid;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void playerMove(Coordinate newCoordinate) {
        grid.movePlayer(currentPlayer, newCoordinate);
    }

    public int getPlayerTurnCount(int playerNumber) {
        return playerNumber == 0 ? player1.getTurnCount() : player2.getTurnCount();
    }

    public void pickUpItem() {

    }

    public void changePlayer(){
        if (currentPlayer.equals(player1)) {
            player1.increaseTurnCount();
            currentPlayer = player2;

        } else {
            player2.increaseTurnCount();
            currentPlayer = player1;
        }

    }


}
