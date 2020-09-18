package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.Game;

import java.io.IOException;

public class MainApp extends Application {
    private Game game;
    private Stage primaryStage;
    private BorderPane rootLayout;
    private Pane startScreen;
    private BorderPane board;
    private StartController startController;



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
            startController = loader.getController();
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

            startController = loader.getController();
            startController.setStartStage(startStage);
            startStage.showAndWait();
            if (startController.gameCanStart()) {
                game = startController.getGame();
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
