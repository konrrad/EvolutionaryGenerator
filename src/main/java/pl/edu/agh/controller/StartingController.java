package pl.edu.agh.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class StartingController {

    private Stage stage;

    public StartingController(Stage primaryStage) {
        this.stage = primaryStage;
    }

    public StartingController() {
    }

    @FXML
    public void initialize() {

    }

    public void initRootLayout() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(StartingController.class
                    .getResource("/StartingView.fxml"));
            root = loader.load();
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newSimulation() {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(GeneratorController.class
                    .getResource("/GeneratorView.fxml"));
            root = loader.load();
            GeneratorController generatorController = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1000, 600));
            generatorController.initRootLayout(stage);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
