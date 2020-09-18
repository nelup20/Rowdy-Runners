package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.Game;
import model.Grid;

import java.io.IOException;

public class MainApp extends Application {
    private Game game;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Pane startScreen;
    private BorderPane board;
    private MainController mainController;


    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("Rowdy Runners");
        initStartScreen();
        showStartScreen();
    }

    private void initStartScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/RootLayout.fxml"));
            rootLayout = loader.load();
            mainController = loader.getController();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showStartScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/Mainview.fxml"));
            startScreen = loader.load();

            Stage startStage = new Stage();
            startStage.initOwner(primaryStage);
            Scene scene = new Scene(startScreen);
            startStage.setScene(scene);

            mainController = loader.getController();
            mainController.setStartStage(startStage);
            startStage.showAndWait();
            if (mainController.gameCanStart()) {
                game = mainController.getGame();
                System.out.println(game.player1);
                setGrid();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void setGrid() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/GameBoard.fxml"));
            board = loader.load();
            board.setCenter(game.getGrid().getGridPane());

            rootLayout.setCenter(board);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}