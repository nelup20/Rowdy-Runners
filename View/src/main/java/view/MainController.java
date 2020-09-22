package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Coordinate;
import model.Game;
import model.Square;
import model.squareContent.Grenade;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

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
    private boolean pickUpActionDone = false;
    private boolean roundEnded = false;


    public void setMainApp(MainApp mainApp) {
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
    }


    @FXML
    void moveUp() {
        System.out.println("button press");
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
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE + 1, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE));
        playerIsMoved();
        updatePlayersTurnCount();
        mainApp.startRound();
    }

    @FXML
    void endTurn() {

    }

    @FXML
    void pickUpItem() {
        pickUpActionDone = true;
        game.pickUpItem();

    }

    @FXML
    void placeItem() {

    }


    private void moveOnKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.UP && playerCanMove(KeyCode.UP)) {
            moveUp();
        }

        if (event.getCode() == KeyCode.DOWN && playerCanMove(KeyCode.DOWN)) {
            moveDown();
        }

        if (event.getCode() == KeyCode.LEFT && playerCanMove(KeyCode.LEFT)) {
            moveLeft();
        }

        if (event.getCode() == KeyCode.RIGHT && playerCanMove(KeyCode.RIGHT)) {
            moveRight();
        }
    }


    private boolean playerCanMove(KeyCode keyCode) {
        if (game.getCurrentPlayer().getCurrentCoordinate().getY_COORDINATE() != 0 && keyCode == KeyCode.UP) {
            return true;
        }
        if (game.getCurrentPlayer().getCurrentCoordinate().getY_COORDINATE() != game.getGrid().getGridSize() - 1 && keyCode == KeyCode.DOWN) {
            return true;
        }
        if (game.getCurrentPlayer().getCurrentCoordinate().getX_COORDINATE() != 0 && keyCode == KeyCode.LEFT) {
            return true;
        }
        if (game.getCurrentPlayer().getCurrentCoordinate().getX_COORDINATE() != game.getGrid().getGridSize() - 1 && keyCode == KeyCode.RIGHT) {
            return true;
        } else {
            return false;
        }
    }

    public void disableMoveButtons() {
        btnMoveRight.setDisable(true);
        btnMoveLeft.setDisable(true);
        btnMoveDown.setDisable(true);
        btnMoveUp.setDisable(true);
    }

    public void disablePickUpButton(){
        btnPickItemUp.setDisable(true);
    }


    private void playerIsMoved() {
        isPlayerMoved = true;
    }

    public boolean getMoveAction(){
        return isPlayerMoved;
    }

    public boolean getPickUpAction(){
        return pickUpActionDone;
    }


    public void updatePlayersTurnCount() {
        playersTurnCountText.setText("Player 1's turns: " + game.getPlayerTurnCount(0) + "\nPlayer 2's turns: " + game.getPlayerTurnCount(1));
    }


    //In this block it will check if there are available actions.
    public void checkPossibleActions() {
        checkPossibleMovement();
        checkPickUpItem();
    }

    private void checkPossibleMovement(){
        disableMoveButtons();
        if(!isPlayerMoved) {
            if (game.getCurrentPlayer().getCurrentCoordinate().getY_COORDINATE() != 0) {
                btnMoveUp.setDisable(false);
            }
            if (game.getCurrentPlayer().getCurrentCoordinate().getY_COORDINATE() != game.getGrid().getGridSize() - 1) {
                btnMoveDown.setDisable(false);
            }
            if (game.getCurrentPlayer().getCurrentCoordinate().getX_COORDINATE() != 0) {
                btnMoveLeft.setDisable(false);
            }
            if (game.getCurrentPlayer().getCurrentCoordinate().getX_COORDINATE() != game.getGrid().getGridSize() - 1) {
                btnMoveRight.setDisable(false);
            }
        }

    }

    public void checkPickUpItem() {
        disablePickUpButton();
        if(pickUpActionDone) {
            if (game.getGrid().getSquare(game.getCurrentPlayer().getCurrentCoordinate()).getGrenade() != null) {
                if (!game.getGrid().getSquare(game.getCurrentPlayer().getCurrentCoordinate()).getGrenade().isPickedUp()) {
                    btnPickItemUp.setDisable(false);
                }
            }
        }
    }


}
