package pl.edu.agh;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import pl.edu.agh.controller.GeneratorController;

public class Main extends Application {
    private Stage primaryStage;

    private GeneratorController generatorController;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("hello world");

        this.generatorController = new GeneratorController(primaryStage);
        this.generatorController.initRootLayout();
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });


    }


}
