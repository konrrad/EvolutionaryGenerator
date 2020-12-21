package pl.edu.agh.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.agh.model.config.ConfigProvider;
import pl.edu.agh.model.config.JSONConfigProvider;
import pl.edu.agh.model.world.World;
import pl.edu.agh.model.world.WorldCreator;
import pl.edu.agh.view.WorldGridPane;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GeneratorController {

    private static final String configFilename = "parameters.json";
    private final ConfigProvider jsonConfigProvider = new JSONConfigProvider(new File(configFilename));
    private final WorldCreator worldCreator = new WorldCreator(jsonConfigProvider);
    private final World world = worldCreator.createWorld();
    private final Timer timer = new Timer();
    @FXML
    public VBox vbox;
    private Stage primaryStage;
    private WorldGridPane gridPane = new WorldGridPane(world, jsonConfigProvider.getWidth(), jsonConfigProvider.getHeight());


    public GeneratorController(Stage primaryStage) {
        this.primaryStage = primaryStage;

    }


    public GeneratorController() {
    }

    public void initRootLayout() {
        this.primaryStage.setTitle("Generator");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(GeneratorController.class
                .getResource("/GeneratorView.fxml"));
        try {
            BorderPane rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void initialize() {
        vbox.getChildren().add(gridPane);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(world.isAnyoneAlive())
                    Platform.runLater(() -> simulate());
            }
        };
        timer.scheduleAtFixedRate(timerTask, 1000L, 1000L);

    }

    public void simulate() {
        world.runEpoch();
        vbox.getChildren().clear();
        gridPane.update();
        vbox.getChildren().add(gridPane);
    }
}
