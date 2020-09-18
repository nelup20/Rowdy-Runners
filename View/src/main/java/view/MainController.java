package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Coordinate;
import model.Game;

public class MainController {
    @FXML
    private Button btnMoveUp;

    @FXML
    private Button btnMoveDown;

    @FXML
    private Button btnMoveLeft;

    @FXML
    private Button btnMoveRight;

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
    }




    @FXML
    void moveUp() {
        System.out.println("button press");
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE - 1));
        playerIsMoved();
        mainApp.startRound();

    }

    @FXML
    void moveDown() {
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE + 1));
        playerIsMoved();
        mainApp.startRound();
    }

    @FXML
    void moveLeft() {
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE - 1, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE));
        playerIsMoved();
        mainApp.startRound();
    }

    @FXML
    void moveRight() {
        game.playerMove(new Coordinate(game.getCurrentPlayer().getCurrentCoordinate().X_COORDINATE + 1, game.getCurrentPlayer().getCurrentCoordinate().Y_COORDINATE ));
        playerIsMoved();
        mainApp.startRound();
    }

    public void disableButtons(){
        btnMoveRight.setDisable(true);
        btnMoveLeft.setDisable(true);
        btnMoveDown.setDisable(true);
        btnMoveUp.setDisable(true);
    }

    public void checkPossibleMove() {
        disableButtons();
        if (game.getCurrentPlayer().getCurrentCoordinate().getY_COORDINATE() != 0) {
            btnMoveUp.setDisable(false);
        }
        if(game.getCurrentPlayer().getCurrentCoordinate().getY_COORDINATE() != 9){
            btnMoveDown.setDisable(false);
        }
        if(game.getCurrentPlayer().getCurrentCoordinate().getX_COORDINATE() != 0){
            btnMoveLeft.setDisable(false);
        }
        if(game.getCurrentPlayer().getCurrentCoordinate().getX_COORDINATE() != 9){
            btnMoveRight.setDisable(false);
        }
    }

    private void playerIsMoved(){
        isPlayerMoved = true;
    }

    public boolean isPlayerMoved(){
        return isPlayerMoved;
    }

}
