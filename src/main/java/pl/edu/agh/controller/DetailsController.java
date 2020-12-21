package pl.edu.agh.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import pl.edu.agh.model.animal.Animal;

import java.util.Collection;

public class DetailsController {
    ObservableList<Animal> animalObservableList = FXCollections.observableArrayList();
    @FXML
    ListView<Animal> animalsListView;
    @FXML
    Label nameLabel;
    @FXML
    Label genomeLabel;
    @FXML
    Label dominatingGene;
    @FXML
    Label childrenLabel;
    @FXML
    Label descendantsLabel;
    @FXML
    Label deathTimeLabel;


    public DetailsController() {
    }

    @FXML
    public void initialize() {
        animalsListView.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Animal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.toString());
                }
            }
        });
        animalsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                nameLabel.setText("NAME:" + newValue.toString());
                genomeLabel.setText("GENOME:" + newValue.genome.toString());
                dominatingGene.setText("DOMINATING GENE:" + newValue.genome.getDominatingGene());
                childrenLabel.setText("CHILDREN:" + newValue.getNumberOfChildren());
                descendantsLabel.setText("DESCENDANTS:" + newValue.getNumberOfDescendants());
                deathTimeLabel.setText("DEATH TIME: " + (newValue.isAlive() ? "STILL ALIVE" : newValue.getDeathTime()));

            }
        });
        animalsListView.setItems(animalObservableList);

    }

    public void init(Collection<Animal> animals) {
        animalObservableList.addAll(animals);
    }
}
