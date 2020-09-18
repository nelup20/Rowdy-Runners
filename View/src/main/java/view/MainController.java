package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Coordinate;
import model.Game;

public class MainController {
    private Stage mainStage;
    private Game game;

    public void setMainStage(Stage mainStage){this.mainStage = mainStage;}
    public void setGame(Game game){this.game = game;}

    @FXML
    private Button btnMoveUp;

    @FXML
    void moveUp() {
        game.getGrid().movePlayer(game.getCurrentPlayer(), new Coordinate(5,5));

    }

}
