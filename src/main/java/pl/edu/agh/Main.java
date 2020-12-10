package pl.edu.agh;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        stage.setTitle("JavaFX and Gradle");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
