package pl.edu.agh.model.world;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import pl.edu.agh.model.animal.Animal;
import pl.edu.agh.model.animal.EnergyComparator;
import pl.edu.agh.model.coordinates.Orientation;
import pl.edu.agh.model.coordinates.Vector2;
import pl.edu.agh.model.map.Terrain;

import java.util.*;
import java.util.stream.Collectors;

public class World {
    @Getter
    private final Multimap<Vector2, Animal> positionAnimalsMap;
    @Getter
    private final Terrain terrain;
    private final Comparator<Animal> energyComparator = new EnergyComparator();
    private final List<Orientation> possibleOrientations = Arrays.asList(Orientation.values());
    private final int moveEnergy;

    public World(Multimap<Vector2, Animal> positionAnimalsMap, Terrain terrain, int moveEnergy) {
        this.positionAnimalsMap = positionAnimalsMap;
        this.terrain = terrain;
        this.moveEnergy = moveEnergy;
    }

    public void runEpoch() {
        plant();
        moveAnimals();
        feedAnimals();
        copulateAnimals();
        removeDead();
    }

    private void moveAnimals() {
        final Multimap<Vector2, Animal> toRemove = ArrayListMultimap.create();
        final Multimap<Vector2, Animal> toAdd = ArrayListMultimap.create();
        for (Vector2 position : positionAnimalsMap.keySet()) {
            Collection<Animal> animalsOnPosition = positionAnimalsMap.get(position);
            animalsOnPosition.forEach(animal -> {
                animal.increaseLivingTime();
                animal.takeEnergy(this.moveEnergy);
                final Vector2 preferredDirectionVector = animal.getPreferredDirectionVector();
                final Vector2 newPosition = terrain.validatePosition(position.add(preferredDirectionVector));
                toRemove.put(position, animal);
                toAdd.put(newPosition, animal);
            });
        }
        toRemove.entries().forEach(vector2AnimalEntry -> positionAnimalsMap.remove(vector2AnimalEntry.getKey(), vector2AnimalEntry.getValue()));
        positionAnimalsMap.putAll(toAdd);
    }

    private void copulateAnimals() {
        for (Vector2 position : positionAnimalsMap.keySet()) {
            Collection<Animal> animalsOnPosition = positionAnimalsMap.get(position);
            if (animalsOnPosition.size() >= 2) {
                List<Animal> parents = findTwoStrongest(animalsOnPosition);
                final Animal father = parents.get(0);
                final Animal mother = parents.get(1);
                if (!father.canCopulate() || !mother.canCopulate()) return;
                final Animal newBorn = father.copulate(mother);
                positionAnimalsMap.put(findEmptyPositionAround(position), newBorn);
            }
        }
    }


    private Vector2 findEmptyPositionAround(Vector2 position) {
        Collections.shuffle(possibleOrientations);
        return possibleOrientations.stream()
                .map(Orientation::toUnitVector)
                .map(vector2 -> vector2.add(position))
                .filter(positionAround -> !terrain.isGrown(positionAround))
                .filter(positionAround -> !positionAnimalsMap.containsKey(positionAround))
                .map(terrain::validatePosition)
                .findAny()
                .orElse(position.add(possibleOrientations.get(0).toUnitVector()));
    }

    private void removeDead() {
        Multimap<Vector2, Animal> toRemove = ArrayListMultimap.create();
        for (Vector2 position : positionAnimalsMap.keySet()) {
            Collection<Animal> animalsOnPosition = positionAnimalsMap.get(position);
            animalsOnPosition.forEach(animal -> {
                if (!animal.isAlive()) toRemove.put(position, animal);
            });
        }
        toRemove.keySet().forEach(vector2 -> {
            toRemove.get(vector2).forEach(animal -> positionAnimalsMap.remove(vector2, animal));
        });
    }

    private List<Animal> findTwoStrongest(final Collection<Animal> animals) {
        return animals.stream().sorted(energyComparator).skip(animals.size() - 2).collect(Collectors.toList());
    }

    private void feedAnimals() {
        for (Vector2 position : positionAnimalsMap.keySet()) {
            Collection<Animal> animalsOnPosition = positionAnimalsMap.get(position);
            if (!animalsOnPosition.isEmpty())
                feedStrongest(animalsOnPosition, terrain.getEnergyForPosition(position));
            terrain.deletePlant(position);
        }
    }

    private void feedStrongest(final Collection<Animal> animals, final int energy) {
        Collection<Animal> strongestAnimals = findStrongest(animals);
        strongestAnimals.forEach(animal -> animal.eat((int) ((float)energy / (float)strongestAnimals.size())));
    }

    private List<Animal> findStrongest(@NotNull final Collection<Animal> animals) {
        final int maxEnergy = findMaximumEnergy(animals);
        return animals.stream().filter(animal -> animal.getEnergy() == maxEnergy).collect(Collectors.toList());
    }

    private int findMaximumEnergy(@NotNull final Collection<Animal> animals) {
        return animals.stream().max(energyComparator).map(Animal::getEnergy).orElseThrow(IllegalArgumentException::new);
    }

    private void plant() {
        this.terrain.plant(positionAnimalsMap.keySet());
    }

    public boolean isAnyoneAlive()
    {
        return this.positionAnimalsMap.size()>0;
    }
}
