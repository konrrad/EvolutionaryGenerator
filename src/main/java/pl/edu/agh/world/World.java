package pl.edu.agh.world;

import com.google.common.collect.Multimap;
import org.jetbrains.annotations.NotNull;
import pl.edu.agh.animal.Animal;
import pl.edu.agh.animal.EnergyComparator;
import pl.edu.agh.coordinates.Orientation;
import pl.edu.agh.coordinates.Vector2;
import pl.edu.agh.map.Terrain;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class World {
    private final Multimap<Vector2, Animal> positionAnimalsMap;
    private final Terrain terrain;
    private final EnergyComparator energyComparator = new EnergyComparator();
    private final List<Orientation> possibleOrientations=Arrays.asList(Orientation.values());
    private final int moveEnergy;

    public World(Multimap<Vector2, Animal> positionAnimalsMap, Terrain terrain, int moveEnergy) {
        this.positionAnimalsMap = positionAnimalsMap;
        this.terrain = terrain;
        this.moveEnergy=moveEnergy;
    }

    public void runEpoch() {
        plant();
        moveAnimals();
        feedAnimals();
        copulateAnimals();
        removeDead();
    }

    private void moveAnimals() {
        for (Vector2 position : positionAnimalsMap.keySet()) {
            Collection<Animal> animalsOnPosition=positionAnimalsMap.get(position);
            animalsOnPosition.forEach(animal -> {
                animal.increaseLivingTime();
                animal.takeEnergy(moveEnergy);
                final Vector2 preferredDirectionVector=animal.getPreferredDirectionVector();
                final Vector2 newPosition=terrain.validatePosition(position.add(preferredDirectionVector));
                positionAnimalsMap.remove(position,animal);
                positionAnimalsMap.put(newPosition,animal);
            });
        }
    }

    //@TODO
    private void copulateAnimals() {
        for (Vector2 position : positionAnimalsMap.keySet()) {
            Collection<Animal> animalsOnPosition=positionAnimalsMap.get(position);
            if(animalsOnPosition.size()>=2)
            {
                List<Animal> parents=findTwoStrongest(animalsOnPosition);
                final Animal father=parents.get(0);
                final Animal mother=parents.get(1);
                final Animal newBorn=father.copulate(mother);
                positionAnimalsMap.put(findEmptyPositionAround(position),newBorn);
            }
        }
    }

    private Vector2 findEmptyPositionAround(Vector2 position)
    {
        Collections.shuffle(possibleOrientations);
        return possibleOrientations.stream()
                .map(Orientation::toUnitVector)
                .map(vector2 -> vector2.add(position))
                .filter(positionAround->!terrain.isGrown(positionAround))
                .filter(postionAround->!positionAnimalsMap.containsKey(postionAround))
                .findAny()
                .orElse(position.add(possibleOrientations.get(0).toUnitVector()));
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
