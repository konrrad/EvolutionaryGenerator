package pl.edu.agh.model.world;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import pl.edu.agh.model.animal.Animal;
import pl.edu.agh.model.config.ConfigProvider;
import pl.edu.agh.model.coordinates.Orientation;
import pl.edu.agh.model.coordinates.Vector2;
import pl.edu.agh.model.map.Terrain;
import pl.edu.agh.model.map.TerrainWithJungleInCenterAndSteppe;

public class WorldCreator {
    private final ConfigProvider configProvider;
    @Getter
    private Terrain terrain;

    public WorldCreator(ConfigProvider configProvider) {
        this.configProvider = configProvider;

    }

    public World createWorld()
    {
        terrain=new TerrainWithJungleInCenterAndSteppe(configProvider.getWidth()
                ,configProvider.getHeight()
                ,configProvider.getJungleRatio()
                ,configProvider.getPlantEnergy());
        Multimap<Vector2, Animal> map=ArrayListMultimap.create();
        for (int i = 0; i < configProvider.getNumberOfAnimals(); i++) {
            map.put(
                    new Vector2(configProvider.getWidth()/2,configProvider.getHeight()/2),
                    new Animal(Orientation.getRandomOrientation(),configProvider.getStartEnergy(),1));
        }
        System.out.println("ME"+configProvider.getMoveEnergy());
        return new World(map,terrain,configProvider.getMoveEnergy());
    }
}
