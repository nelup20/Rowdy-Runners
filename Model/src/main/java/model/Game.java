package model;

public class Game {

    public static final int MIN_GRID_SIZE = 10;
    public final Player player1;
    public final Player player2;
    public final Grid grid;
    private Player currentPlayer;
    private boolean isPlayerMoved = false;

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

    public boolean isPlayerMoved(){return isPlayerMoved;}

    public void playerMoved(){
        this.isPlayerMoved = !isPlayerMoved;
    }

    public void playerMove(Coordinate newCoordinate) {
        grid.movePlayer(currentPlayer, newCoordinate);
        activeGrenadeOnSquare();
        isPlayerMoved = true;
        getGrid().setGrenadeActive(getCurrentPlayer());

    }

    private void activeGrenadeOnSquare() {
        if(grid.getSquare(currentPlayer.getCurrentCoordinate()).getGrenade() != null && grid.getSquare(currentPlayer.getCurrentCoordinate()).getGrenade().isActive() ){
            currentPlayer.isHit();
            grid.getSquare(currentPlayer.getCurrentCoordinate()).removeGrenade();
            grid.getSquare(currentPlayer.getCurrentCoordinate()).showPlayerStunned(currentPlayer);
        }
    }

    public int getPlayerTurnCount(int playerNumber) {
        return playerNumber == 0 ? player1.getTurnCount() : player2.getTurnCount();
    }

    public void pickUpItem() {
        currentPlayer.addGrenade(grid.getSquare(currentPlayer.getCurrentCoordinate()).getGrenade());
        grid.getSquare(currentPlayer.getCurrentCoordinate()).pickUpGrenade(currentPlayer);
    }

    public void placeItem() {
        grid.getSquare(currentPlayer.getCurrentCoordinate()).placeGrenade(currentPlayer);

    }

    public void changePlayer() {
        isPlayerMoved = false;
        if(!otherPlayerIsStunned(currentPlayer.equals(player1)? player2 : player1)) {
            if (currentPlayer.equals(player1)) {
                player1.increaseTurnCount();
                currentPlayer = player2;


            } else {
                player2.increaseTurnCount();
                currentPlayer = player1;

            }
        }
    }

    private boolean otherPlayerIsStunned(Player player){
        if(!player.isStunned()){
            grid.getSquare(player.getCurrentCoordinate()).setPlayer(player);
        }
        return player.isStunned();
    }


}
