package pl.edu.agh.model.config;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class JSONConfigProvider implements ConfigProvider {
    private final JSONParser jsonParser;
    private int width;
    private int height;
    private int startEnergy;
    private int moveEnergy;
    private int plantEnergy;
    private double jungleRatio;
    private int numberOfAnimals;

    public JSONConfigProvider(File filename) {
        this.jsonParser=new JSONParser();
        try
        {
            FileReader reader=new FileReader(filename);
            JSONObject obj=(JSONObject) jsonParser.parse(reader);
            this.width= ((Long) obj.get("width")).intValue();
            this.height= ((Long) obj.get("height")).intValue();
            this.startEnergy= ((Long) obj.get("startEnergy")).intValue();
            this.moveEnergy= ((Long) obj.get("moveEnergy")).intValue();
            this.plantEnergy= ((Long) obj.get("plantEnergy")).intValue();
            this.jungleRatio= (Double) obj.get("jungleRatio");
            this.numberOfAnimals= ((Long) obj.get("numberOfAnimals")).intValue();
        } catch (ParseException | IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public int getWidth() {
        return this.width;
    }

    @Override
    public int getHeight() {
        return this.height;
    }

    @Override
    public int getStartEnergy() {
        return this.startEnergy;
    }

    @Override
    public int getMoveEnergy() {
        return this.moveEnergy;
    }

    @Override
    public int getPlantEnergy() {
        return this.plantEnergy;
    }

    @Override
    public double getJungleRatio() {
        return this.jungleRatio;
    }

    @Override
    public int getNumberOfAnimals() {
        return this.numberOfAnimals;
    }
}
