package view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Game;
import model.Player;

import java.lang.reflect.Field;

public class MainController {
    @FXML
    private Button btnPlay;

    @FXML
    private TextField fldPlayer1;

    @FXML
    private TextField fldPlayer2;

    @FXML
    private TextField fldGridSize;

    private Stage startStage;
    private boolean gameCanStart = false;
    private Game game;


    @FXML
    void startGame() {
        if (isInputValid()) {
            game = new Game(Integer.parseInt(fldGridSize.getText()), new Player(fldPlayer1.getText()), new Player(fldPlayer2.getText()));
            gameCanStart = true;
            startStage.close();
        }

    }

    public Game getGame() {
        return game;
    }


    public boolean gameCanStart() {
        return gameCanStart;
    }

    public void setStartStage(Stage startStage) {
        this.startStage = startStage;
    }

    private boolean isInputValid() {
        String errorMessage = "";
        if (fldPlayer1IsNotValid()) {
            errorMessage += "No valid player 1\n";
        }
        if (fldPlayer2IsNotValid()) {
            errorMessage += "No valid player 2\n";
        }
        if (playersEquals()) {
            errorMessage += "Use different names for the players";
        }
        if (fldGridSizeIsNotValid()) {
            errorMessage += "No valid gridsize\n";
        } else if (!equalsOrHigherDefaultGridSize()) {
            errorMessage += "Given value is not a number or is lower then " + Game.MIN_GRID_SIZE;
        }

        if (noErrorMessage(errorMessage)) {
            return true;
        } else {
            showError(errorMessage);
            return false;
        }
    }

    private boolean fldPlayer1IsNotValid() {
        return (fldPlayer1.getText() == null || fldPlayer1.getText().length() == 0);
    }

    private boolean fldPlayer2IsNotValid() {
        return (fldPlayer2.getText() == null || fldPlayer2.getText().length() == 0);
    }

    private boolean playersEquals() {
        return fldPlayer1.getText().equals(fldPlayer2.getText());
    }

    private boolean fldGridSizeIsNotValid() {
        return (fldGridSize.getText() == null || fldGridSize.getText().length() == 0);
    }

    private boolean noErrorMessage(String errorMessage) {
        return errorMessage.length() == 0;
    }

    private boolean equalsOrHigherDefaultGridSize() {
        boolean result = false;
        try {
            if (Integer.parseInt(fldGridSize.getText()) >= Game.MIN_GRID_SIZE) {
                result = true;
            }
        } catch (NumberFormatException e) {
            e.getMessage();
        }
        return result;
    }

    private void showError(String errorMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.initOwner(startStage);
        alert.setTitle("Invalid fields");
        alert.setContentText(errorMessage);
        alert.showAndWait();
    }


}
