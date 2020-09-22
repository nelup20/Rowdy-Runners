package view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.skin.TextInputControlSkin;
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


    private Stage mainStage;
    private Game game;
    private MainApp mainApp;
    private boolean isPlayerMoved = false;

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
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE + 1, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE ));
        playerIsMoved();
        updatePlayersTurnCount();
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
    }



    private boolean playerCanMove(KeyCode keyCode){
        int currentPlayerYCoordinate = getPlayerYCoordinate();
        int currentPlayerXCoordinate = getPlayerXCoordinate();

        int gameGridSize = game.getGrid().getGridSize();

        boolean isSquareAboveAWall = checkIfWallIsNextToPlayer(Direction.UP);
        boolean isSquareBelowAWall = checkIfWallIsNextToPlayer(Direction.DOWN);
        boolean isSquareLeftAWall = checkIfWallIsNextToPlayer(Direction.LEFT);
        boolean isSquareRightAWall = checkIfWallIsNextToPlayer(Direction.RIGHT);


        if (currentPlayerYCoordinate != 0 && keyCode == KeyCode.UP && !isSquareAboveAWall) {
            return true;
        }
        if(currentPlayerYCoordinate != gameGridSize - 1 && keyCode == KeyCode.DOWN && !isSquareBelowAWall){
            return true;
        }
        if(currentPlayerXCoordinate != 0 && keyCode == KeyCode.LEFT && !isSquareLeftAWall){
            return true;
        }
        if(currentPlayerXCoordinate != gameGridSize - 1 && keyCode == KeyCode.RIGHT && !isSquareRightAWall){
            return true;
        } else {
            return false;
        }
    }



    public void disableButtons(){
        btnMoveRight.setDisable(true);
        btnMoveLeft.setDisable(true);
        btnMoveDown.setDisable(true);
        btnMoveUp.setDisable(true);
    }

    public void checkPossibleMove() {
        disableButtons();
        int currentPlayerYCoordinate = getPlayerYCoordinate();
        int currentPlayerXCoordinate = getPlayerXCoordinate();

        int gameGridSize = game.getGrid().getGridSize();

        boolean isSquareAboveAWall = checkIfWallIsNextToPlayer(Direction.UP);
        boolean isSquareBelowAWall = checkIfWallIsNextToPlayer(Direction.DOWN);
        boolean isSquareLeftAWall = checkIfWallIsNextToPlayer(Direction.LEFT);
        boolean isSquareRightAWall = checkIfWallIsNextToPlayer(Direction.RIGHT);


        if (currentPlayerYCoordinate != 0 && !isSquareAboveAWall) {
            btnMoveUp.setDisable(false);
        }
        if(currentPlayerYCoordinate != gameGridSize - 1 && !isSquareBelowAWall){
            btnMoveDown.setDisable(false);
        }
        if(currentPlayerXCoordinate != 0 && !isSquareLeftAWall){
            btnMoveLeft.setDisable(false);
        }
        if(currentPlayerXCoordinate != gameGridSize - 1 && !isSquareRightAWall){
            btnMoveRight.setDisable(false);
        }
    }

    private void playerIsMoved(){
        isPlayerMoved = true;
    }

    public boolean isPlayerMoved(){
        return isPlayerMoved;
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
}
