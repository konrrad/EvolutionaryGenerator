package pl.edu.agh.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import pl.edu.agh.model.animal.Animal;
import pl.edu.agh.model.coordinates.Vector2;
import pl.edu.agh.model.map.Terrain;
import pl.edu.agh.model.world.World;

import java.util.Map;

public class WorldGridPane extends GridPane {
    public static final int SIZE = 50;
    public static final ColumnConstraints COLUMN_CONSTRAINTS = new ColumnConstraints();
    public static final RowConstraints ROW_CONSTRAINTS = new RowConstraints();

    static {
        COLUMN_CONSTRAINTS.setPrefWidth(50);
        ROW_CONSTRAINTS.setPrefHeight(50);

    }

    private final World world;
    private final Terrain terrain;
    private final int width;
    private final int height;
    private final IntToColorMapper intToColorMapper=new IntToColorMapper();

    public WorldGridPane(World world, int width, int height) {
        super();
        this.width = width;
        this.height = height;
        this.world = world;
        this.terrain = world.getTerrain();
        this.setGridLinesVisible(true);
        setRows(height);
        setCols(width);
        addPlants();
        addAnimals();
        setPrefSize(400, 400);

    }

    private void addAnimals() {
        var positionAnimalMap = world.getPositionAnimalsMap();
        for (Map.Entry<Vector2, Animal> entry : positionAnimalMap.entries()) {
            Circle circle = new Circle(10);
            System.out.println(entry.getValue());
            circle.setFill(intToColorMapper.mapPercentToColor((float)entry.getValue().getEnergy()/(float)Animal.getMINIMUM_REPRODUCTION_ENERGY()));
            add(circle, entry.getKey().X, entry.getKey().Y);
            setHalignment(circle, HPos.CENTER);
            setValignment(circle, VPos.CENTER);
        }
    }


    private void setRows(int height) {
        for (int i = 0; i < height; i++) {
            getRowConstraints().add(ROW_CONSTRAINTS);
        }
    }

    private void setCols(int width) {
        for (int i = 0; i < width; i++) {
            getColumnConstraints().add(COLUMN_CONSTRAINTS);
        }
    }

    private void addPlants() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (terrain.isGrown(new Vector2(x, y))) {
                    addPlant(y, x);
                }
            }
        }
    }

    private void addPlant(int row, int col) {
        this.add(new Rectangle(SIZE, SIZE, Color.GREEN), col, row);
    }


    public void update() {
        setGridLinesVisible(false);
        getChildren().clear();
        addPlants();
        addAnimals();
        setGridLinesVisible(true);
    }


}
