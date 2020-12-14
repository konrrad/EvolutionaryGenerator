package pl.edu.agh.world;

import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import pl.edu.agh.animal.Animal;
import pl.edu.agh.animal.EnergyComparator;
import pl.edu.agh.coordinates.Vector2;
import pl.edu.agh.map.Terrain;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class World {
    private final Multimap<Vector2, Animal> positionAnimalsMap;
    private final Terrain terrain;
    private final EnergyComparator energyComparator = new EnergyComparator();

    public World(Multimap<Vector2, Animal> positionAnimalsMap, Terrain terrain) {
        this.positionAnimalsMap = positionAnimalsMap;
        this.terrain = terrain;
    }

    public void runEpoch() {
        plant();
        moveAnimals();
        feedAnimals();
        copulateAnimals();
        removeDead();
    }

    //@TODO
    private void moveAnimals() {
        positionAnimalsMap.values().forEach(animal -> {

        });
    }

    private void copulateAnimals() {
        positionAnimalsMap.keySet().stream()
                .map(positionAnimalsMap::get)
                .filter(animals -> animals.size()>=2)
                .map(this::findTwoStrongest)
                .forEach(animals -> {
                    animals.get(0).copulate(animals.get(1));
                });
    }

    private void removeDead()
    {
        for (Vector2 position : positionAnimalsMap.keySet()) {
            Collection<Animal> animalsOnPosition = positionAnimalsMap.get(position);
            animalsOnPosition.forEach(animal -> {
                if(!animal.isAlive()) positionAnimalsMap.remove(position,animal);
            });
        }
    }

    private List<Animal> findTwoStrongest(final Collection<Animal> animals) {
        return animals.stream().sorted(energyComparator).skip(animals.size() - 2).collect(Collectors.toUnmodifiableList());
    }

    private void feedAnimals() {
        for (Vector2 position : positionAnimalsMap.keySet()) {
            Collection<Animal> animalsOnPosition = positionAnimalsMap.get(position);
            if(!animalsOnPosition.isEmpty())
                feedTheStrongest(animalsOnPosition, terrain.getEnergyForPosition(position));
                terrain.deletePlant(position);
        }
    }

    private void feedTheStrongest(final Collection<Animal> animals, final int energy) {
        findTheStrongest(animals).eat(energy);
    }

    private Animal findTheStrongest(@NotNull final Collection<Animal> animals) {
        return animals.stream().max(energyComparator).orElseThrow(IllegalArgumentException::new);
    }

    private void plant() {
        this.terrain.plant(positionAnimalsMap.keySet());
    }
}
