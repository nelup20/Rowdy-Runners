package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Coordinate;
import model.Game;
import model.wallcreation.Direction;

public class MainController {
    @FXML
    private Button btnMoveUp;

    @FXML
    private Button btnMoveDown;

    @FXML
    private Button btnMoveLeft;

    @FXML
    private Button btnMoveRight;

    @FXML
    private Text playersTurnCountText;

    @FXML
    private Button btnEndTurn;

    @FXML
    private Button btnPlaceItem;

    @FXML
    private Button btnPickItemUp;



    private Stage mainStage;
    private Game game;
    private MainApp mainApp;
    private boolean isPlayerMoved = false;
    private boolean isPickUpActionDone = false;
    private boolean isPlaceItemDone = false;
    private boolean endedTurn = false;

    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }

    public void setGame(Game game) {
        this.game = game;
        updatePlayersTurnCount();


        btnMoveUp.setOnKeyPressed(event -> {
            moveOnKeyPress(event);
        });

        btnMoveDown.setOnKeyPressed(event -> {
            moveOnKeyPress(event);
        });

        btnMoveLeft.setOnKeyPressed(event -> {
            moveOnKeyPress(event);
        });

        btnMoveRight.setOnKeyPressed(event -> {
            moveOnKeyPress(event);
        });

        btnEndTurn.setOnKeyPressed(event -> {
            moveOnKeyPress(event);
        });

        btnPickItemUp.setOnKeyPressed(event -> {
            moveOnKeyPress(event);
        });

        btnPlaceItem.setOnKeyPressed(event -> {
            moveOnKeyPress(event);
        });
    }


    @FXML
    void moveUp() {
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE - 1));
        playerIsMoved();
        updatePlayersTurnCount();
        mainApp.startRound();

    }

    @FXML
    void moveDown() {
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE + 1));
        playerIsMoved();
        updatePlayersTurnCount();
        mainApp.startRound();
    }

    @FXML
    void moveLeft() {
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE - 1, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE));
        playerIsMoved();
        updatePlayersTurnCount();
        mainApp.startRound();
    }

    @FXML
    void moveRight() {
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE + 1, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE ));
        playerIsMoved();
        updatePlayersTurnCount();
        mainApp.startRound();
    }

    @FXML
    void endTurn() {
        game.changePlayer();
        isPlayerMoved = false;
        isPickUpActionDone = false;
        isPlaceItemDone = false;
        mainApp.startRound();


    }

    @FXML
    void pickUpItem() {
        game.pickUpItem();
        isPickUpActionDone = true;
        mainApp.startRound();
    }

    @FXML
    void placeItem() {
        game.placeItem();
        isPlaceItemDone = true;
        mainApp.startRound();

    }



    private void moveOnKeyPress(KeyEvent event){
        if(event.getCode() == KeyCode.UP && playerCanMove(KeyCode.UP)){
                moveUp();
        }

        if(event.getCode() == KeyCode.DOWN && playerCanMove(KeyCode.DOWN)){
                moveDown();
        }

        if(event.getCode() == KeyCode.LEFT && playerCanMove(KeyCode.LEFT)){
                moveLeft();
        }

        if(event.getCode() == KeyCode.RIGHT && playerCanMove(KeyCode.RIGHT)){
                moveRight();
        }

        if(event.getCode() == KeyCode.E){
                endTurn();
        }
    }


    public void checkPossibleActions(){
        if(game.getCurrentPlayer().isStunned()){
            endTurn();
        }
        checkPossibleMove();
        checkPossibleToPickUpItem();
        checkPossibleToPlaceAItem();

    }




    public void disableMoveButtons(){
        btnMoveRight.setDisable(true);
        btnMoveLeft.setDisable(true);
        btnMoveDown.setDisable(true);
        btnMoveUp.setDisable(true);
    }

    private void disablePickUpButton(){
        btnPickItemUp.setDisable(true);
    }

    private void disablePlaceItemButton(){btnPlaceItem.setDisable(true);}

    public void checkPossibleMove() {
        disableMoveButtons();
        if(!isPlayerMoved) {
            int currentPlayerYCoordinate = getPlayerYCoordinate();
            int currentPlayerXCoordinate = getPlayerXCoordinate();

            int gameGridSize = game.getGrid().getGridSize();

            boolean isSquareAboveAWall = checkIfWallIsNextToPlayer(Direction.UP);
            boolean isSquareBelowAWall = checkIfWallIsNextToPlayer(Direction.DOWN);
            boolean isSquareLeftAWall = checkIfWallIsNextToPlayer(Direction.LEFT);
            boolean isSquareRightAWall = checkIfWallIsNextToPlayer(Direction.RIGHT);

            boolean isSquareAboveAPlayer = checkIfSquareIsOccupied(Direction.UP);
            boolean isSquareBelowAPlayer = checkIfSquareIsOccupied(Direction.DOWN);
            boolean isSquareLeftAPlayer = checkIfSquareIsOccupied(Direction.LEFT);
            boolean isSquareRightAPlayer = checkIfSquareIsOccupied(Direction.RIGHT);

            boolean isLightTrailAboveAPlayer = checkIfLightTrailIsNextToPlayer(Direction.UP);
            boolean isLightTrailBelowAPlayer = checkIfLightTrailIsNextToPlayer(Direction.DOWN);
            boolean isLightTrailLeftAPlayer = checkIfLightTrailIsNextToPlayer(Direction.LEFT);
            boolean isLightTrailRightAPlayer = checkIfLightTrailIsNextToPlayer(Direction.RIGHT);



            if (currentPlayerYCoordinate != 0 && !isSquareAboveAWall && !isSquareAboveAPlayer && !isLightTrailAboveAPlayer) {
                btnMoveUp.setDisable(false);

            } else if (currentPlayerYCoordinate != gameGridSize - 1 && !isSquareBelowAWall && !isSquareBelowAPlayer && !isLightTrailBelowAPlayer) {
                btnMoveDown.setDisable(false);

            } else if (currentPlayerXCoordinate != 0 && !isSquareLeftAWall && !isSquareLeftAPlayer && !isLightTrailLeftAPlayer) {
                btnMoveLeft.setDisable(false);

            } else if (currentPlayerXCoordinate != gameGridSize - 1 && !isSquareRightAWall && !isSquareRightAPlayer && !isLightTrailRightAPlayer) {
                btnMoveRight.setDisable(false);
            } else {
                endGame();
            }
        }
    }

    private boolean playerCanMove(KeyCode keyCode){
        if(isPlayerMoved){
            return false;
        }
        int currentPlayerYCoordinate = getPlayerYCoordinate();
        int currentPlayerXCoordinate = getPlayerXCoordinate();

        int gameGridSize = game.getGrid().getGridSize();

        boolean isSquareAboveAWall = checkIfWallIsNextToPlayer(Direction.UP);
        boolean isSquareBelowAWall = checkIfWallIsNextToPlayer(Direction.DOWN);
        boolean isSquareLeftAWall = checkIfWallIsNextToPlayer(Direction.LEFT);
        boolean isSquareRightAWall = checkIfWallIsNextToPlayer(Direction.RIGHT);

        boolean isSquareAboveAPlayer = checkIfSquareIsOccupied(Direction.UP);
        boolean isSquareBelowAPlayer = checkIfSquareIsOccupied(Direction.DOWN);
        boolean isSquareLeftAPlayer = checkIfSquareIsOccupied(Direction.LEFT);
        boolean isSquareRightAPlayer = checkIfSquareIsOccupied(Direction.RIGHT);

        boolean isLightTrailAboveAPlayer = checkIfLightTrailIsNextToPlayer(Direction.UP);
        boolean isLightTrailBelowAPlayer = checkIfLightTrailIsNextToPlayer(Direction.DOWN);
        boolean isLightTrailLeftAPlayer = checkIfLightTrailIsNextToPlayer(Direction.LEFT);
        boolean isLightTrailRightAPlayer = checkIfLightTrailIsNextToPlayer(Direction.RIGHT);


        if (currentPlayerYCoordinate != 0 && keyCode == KeyCode.UP && !isSquareAboveAWall && !isSquareAboveAPlayer && !isLightTrailAboveAPlayer) {
            return true;
        }
        if(currentPlayerYCoordinate != gameGridSize - 1 && keyCode == KeyCode.DOWN && !isSquareBelowAWall && !isSquareBelowAPlayer && !isLightTrailBelowAPlayer){
            return true;
        }
        if(currentPlayerXCoordinate != 0 && keyCode == KeyCode.LEFT && !isSquareLeftAWall && !isSquareLeftAPlayer && !isLightTrailLeftAPlayer){
            return true;
        }
        if(currentPlayerXCoordinate != gameGridSize - 1 && keyCode == KeyCode.RIGHT && !isSquareRightAWall && !isSquareRightAPlayer && !isLightTrailRightAPlayer){
            return true;
        } else {

            // YEah yeah I know this is very inefficient, this whole file is looking kinda spaghetti-ish so we should refactor later
            // Maybe make a checkPossibleMove method that check possible moves and then returns an enum of possible moves like PossibleMoves.UP, and if it returns .NONE then endgame
            checkPossibleMove();
            return false;
        }
    }

    private void playerIsMoved(){
        isPlayerMoved = true;
        game.getGrid().setGrenadeActive(game.getCurrentPlayer());
    }


    public void updatePlayersTurnCount(){
        playersTurnCountText.setText("Player 1's turns: " + game.getPlayerTurnCount(1) + "\nPlayer 2's turns: " + game.getPlayerTurnCount(2));
    }

    private int getPlayerYCoordinate(){
        return game.getCurrentPlayer().getCurrentCoordinate().getY_COORDINATE();
    }

    private int getPlayerXCoordinate(){
        return game.getCurrentPlayer().getCurrentCoordinate().getX_COORDINATE();
    }


    private boolean checkIfWallIsNextToPlayer(Direction direction){
        int currentPlayerYCoordinate = getPlayerYCoordinate();
        int currentPlayerXCoordinate = getPlayerXCoordinate();

        int gameGridSize = game.getGrid().getGridSize();

        if(currentPlayerYCoordinate != 0 && direction == Direction.UP){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate, currentPlayerYCoordinate - 1)).getWall();

        } else if(currentPlayerYCoordinate != gameGridSize - 1 && direction == Direction.DOWN){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate, currentPlayerYCoordinate + 1)).getWall();

        } else if(currentPlayerXCoordinate != 0 && direction == Direction.LEFT){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate - 1, currentPlayerYCoordinate)).getWall();

        } else if(currentPlayerXCoordinate != gameGridSize - 1 && direction == Direction.RIGHT){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate + 1, currentPlayerYCoordinate)).getWall();

        } else {
            return false;
        }
    }

    private boolean checkIfSquareIsOccupied(Direction direction){
        int currentPlayerYCoordinate = getPlayerYCoordinate();
        int currentPlayerXCoordinate = getPlayerXCoordinate();

        int gameGridSize = game.getGrid().getGridSize();

        if(currentPlayerYCoordinate != 0 && direction == Direction.UP){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate, currentPlayerYCoordinate - 1)).isOccupiedByPlayer();

        } else if(currentPlayerYCoordinate != gameGridSize - 1 && direction == Direction.DOWN){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate, currentPlayerYCoordinate + 1)).isOccupiedByPlayer();

        } else if(currentPlayerXCoordinate != 0 && direction == Direction.LEFT){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate - 1, currentPlayerYCoordinate)).isOccupiedByPlayer();

        } else if(currentPlayerXCoordinate != gameGridSize - 1 && direction == Direction.RIGHT){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate + 1, currentPlayerYCoordinate)).isOccupiedByPlayer();

        } else {
            return false;
        }

    }

    private boolean checkIfLightTrailIsNextToPlayer(Direction direction){
        int currentPlayerYCoordinate = getPlayerYCoordinate();
        int currentPlayerXCoordinate = getPlayerXCoordinate();

        int gameGridSize = game.getGrid().getGridSize();

        if(direction == Direction.UP && currentPlayerYCoordinate != 0){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate, currentPlayerYCoordinate - 1)).getTrail() == null ? false : true;

        } else if(direction == Direction.DOWN && currentPlayerYCoordinate != gameGridSize - 1){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate, currentPlayerYCoordinate + 1)).getTrail() == null ? false : true;

        } else if(direction == Direction.LEFT && currentPlayerXCoordinate != 0){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate - 1, currentPlayerYCoordinate)).getTrail() == null ? false : true;

        } else if(direction == Direction.RIGHT && currentPlayerXCoordinate != gameGridSize - 1){
            return game.getGrid().getSquare(new Coordinate(currentPlayerXCoordinate + 1, currentPlayerYCoordinate)).getTrail() == null ? false : true;

        } else {
            return false;
        }
    }

    private void checkPossibleToPickUpItem(){
        disablePickUpButton();
        if(!isPickUpActionDone){
            if(game.getGrid().getSquare(game.getCurrentPlayer().getCurrentCoordinate()).getGrenade() != null){
                if(!game.getGrid().getSquare(game.getCurrentPlayer().getCurrentCoordinate()).getGrenade().isPickedUp()){
                    btnPickItemUp.setDisable(false);
                }
            }
        }

    }

    private void checkPossibleToPlaceAItem(){
        disablePlaceItemButton();
        if(!isPlaceItemDone) {
            if (game.getCurrentPlayer().hasItems() && game.getGrid().getSquare(game.getCurrentPlayer().getCurrentCoordinate()).getGrenade() == null) {
                btnPlaceItem.setDisable(false);
            }
        }
    }

    private void endGame(){
        String winningPlayer = game.getCurrentPlayer().equals(game.player1) ? "Player 2" : "Player 1";
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(mainStage);
        alert.setTitle( winningPlayer + " Won!");
        alert.setContentText("GAME ENDED");
        alert.showAndWait();
        System.exit(1);
    }
}
