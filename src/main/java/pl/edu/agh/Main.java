package pl.edu.agh;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import pl.edu.agh.controller.StartingController;

public class Main extends Application {
    private Stage primaryStage;

    private StartingController startingController;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.startingController = new StartingController(primaryStage);
        this.startingController.initRootLayout();

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });


    }


}
