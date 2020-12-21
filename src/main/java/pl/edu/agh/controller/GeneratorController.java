package pl.edu.agh.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.agh.model.animal.Animal;
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
    private Timer timer;
    @FXML
    public VBox vbox;
    @FXML
    public ListView<Animal> listView;
    private Stage primaryStage;
    private WorldGridPane gridPane = new WorldGridPane(world, jsonConfigProvider.getWidth(), jsonConfigProvider.getHeight());
    private boolean running=false;
    private TimerTask timerTask;
    public static final long DELAY=1000L;


    public GeneratorController(Stage primaryStage) {
        this.primaryStage = primaryStage;
//        listView.setCellFactory(lv->new ListCell<Animal>(){
//            @Override
//            protected void updateItem(Animal item, boolean empty) {
//                super.updateItem(item, empty);
//                if(!empty)
//                {
//                    setText(item.getEnergy())
//                }
//            }
//        });
    }

    private void initTask()
    {
        this.timer=new Timer();
        this.timerTask=new TimerTask() {
            @Override
            public void run() {
                if(world.isAnyoneAlive())
                    Platform.runLater(() -> simulate());
            }
        };
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
        initTask();
        timer.scheduleAtFixedRate(timerTask, DELAY, DELAY);

    }

    public void simulate() {
        world.runEpoch();
        vbox.getChildren().clear();
        gridPane.update();
        vbox.getChildren().add(gridPane);
    }

    public void startToggle(ActionEvent actionEvent) {
        running=!running;
        if(!running)
        {
            initTask();
            timer.scheduleAtFixedRate(timerTask, 1000L, 1000L);
        }
        else
            timer.cancel();
    }
}
