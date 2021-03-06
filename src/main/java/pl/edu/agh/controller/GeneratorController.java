package pl.edu.agh.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.agh.model.animal.Animal;
import pl.edu.agh.model.config.ConfigProvider;
import pl.edu.agh.model.config.JSONConfigProvider;
import pl.edu.agh.model.world.World;
import pl.edu.agh.model.world.WorldCreator;
import pl.edu.agh.statistics.*;
import pl.edu.agh.view.WorldGridPane;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneratorController {

    public static final long DELAY = 1000L;
    private static final String configFilename = "parameters.json";
    private final ConfigProvider jsonConfigProvider = new JSONConfigProvider(new File(configFilename));
    private final WorldCreator worldCreator = new WorldCreator(jsonConfigProvider);
    private final World world = worldCreator.createWorld();
    private final StatisticsCounter statisticsCounter = new AnimalStatisticsCounter(world);
    @FXML
    public VBox vbox;
    @FXML
    public ListView<Animal> listView;
    @FXML
    public Label livingAnimalsLabel;
    @FXML
    public Label plantsLabel;
    @FXML
    public Label dominatingGenomeLabel;
    @FXML
    public Label meanEnergyLivingLabel;
    @FXML
    public Label meanLivingTimeLabel;
    @FXML
    public Label meanChildrenLabel;
    @FXML
    public Button detailsButton;
    @FXML
    public TextField numOfEpochsField;
    @FXML
    public Label numOfEpochsPassed;

    private Timer timer;
    private Stage primaryStage;
    private WorldGridPane gridPane = new WorldGridPane(world, jsonConfigProvider.getWidth(), jsonConfigProvider.getHeight());
    private boolean running = false;
    private TimerTask timerTask;
    private StatisticsRepository statisticsRepository=new AnimalStatisticsRepository();



    public GeneratorController() {
    }

    private void initTaskAndTimer() {
        this.timer = new Timer();
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                if (world.isAnyoneAlive())
                    Platform.runLater(() -> simulate());
            }
        };
    }

    public void initRootLayout(Stage primaryStage) {
        this.primaryStage=primaryStage;
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
        initTaskAndTimer();
        timer.scheduleAtFixedRate(timerTask, DELAY, DELAY);

    }

    public void simulate() {
        world.runEpoch();
        vbox.getChildren().clear();
        gridPane.update();
        vbox.getChildren().add(gridPane);
        updateStatistics();
    }

    private void updateStatistics() {
        Epoch epoch=new Epoch(statisticsCounter.getNumberOfLivingAnimals(),
                statisticsCounter.getNumberOfPlants(),
                statisticsCounter.getDominatingGenome(),
                statisticsCounter.getMeanEnergyForLiving(),
                statisticsCounter.getMeanLivingTimeForDead(),
                statisticsCounter.getMeanChildren());
        statisticsRepository.insert(epoch);
        livingAnimalsLabel.setText(String.format("Living Animals: %d", epoch.getNumberOfLivingAnimals()));
        plantsLabel.setText(String.format("Plants: %d", epoch.getNumberOfPlants()));
        dominatingGenomeLabel.setText(String.format("Dominating Genome: %d", epoch.getDominatingGenome()));
        meanEnergyLivingLabel.setText(String.format("Mean Energy Living: %.02f", epoch.getMeanEnergyForLiving()));
        meanLivingTimeLabel.setText(String.format("Mean Living Time Dead: %.02f", epoch.getMeanLivingTimeForDead()));
        meanChildrenLabel.setText(String.format("Mean Children: %.02f", epoch.getMeanChildren()));
        numOfEpochsPassed.setText(String.format("Epochs passed: %d",world.getEpochsPassed()));
    }

    public void startToggle(ActionEvent actionEvent) {
        running = !running;
        if (!running) {
            detailsButton.setDisable(true);
            initTaskAndTimer();
            timer.scheduleAtFixedRate(timerTask, DELAY, DELAY);
        } else
        {
            timer.cancel();
            detailsButton.setDisable(false);
        }

    }

    public void openDetailsView(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader loader=new FXMLLoader(GeneratorController.class
                    .getResource("/DetailsView.fxml"));
            root = loader.load();
            DetailsController detailsController=loader.getController();
            Collection<Animal> animalsForStatistics= Stream.concat(world.getPositionAnimalsMap().values().stream(),world.getDeadAnimals().stream()).collect(Collectors.toList());
            detailsController.init(animalsForStatistics);
            Stage stage = new Stage();
            stage.setScene(new Scene(root, 1000, 600));
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void dumpToFile(ActionEvent actionEvent) {
        try
        {
            statisticsRepository.dumpToFileAfterEpochs(Integer.parseInt(numOfEpochsField.getText()));
        }catch (Exception e)
        {
            e.printStackTrace();
        }


    }
}
