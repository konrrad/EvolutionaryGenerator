package pl.edu.agh.statistics;

import pl.edu.agh.model.animal.Animal;
import pl.edu.agh.model.world.World;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AnimalStatisticsCounter implements StatisticsCounter {

    private final World world;

    public AnimalStatisticsCounter(World world) {
        this.world=world;
    }

    @Override
    public int getNumberOfLivingAnimals() {
        return world.getPositionAnimalsMap().size();
    }

    @Override
    public int getNumberOfPlants() {
        return world.getTerrain().getNumberOfPlants();
    }

    @Override
    public int getDominatingGenome() {
        return world.getPositionAnimalsMap().values().stream()
                .map(animal -> animal.genome.getDominatingGene())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);
    }

    @Override
    public double getMeanEnergyForLiving() {
        return world.getPositionAnimalsMap().values().stream()
                .mapToInt(Animal::getEnergy)
                .average()
                .orElse(0);

    }

    @Override
    public double getMeanLivingTimeForDead() {
        return world.getDeadAnimals().stream()
                .mapToInt(Animal::getLivingTime)
                .average()
                .orElse(0);
    }

    @Override
    public double getMeanChildren() {
        return world.getPositionAnimalsMap().values().stream()
                .mapToInt(Animal::getNumberOfChildren)
                .average()
                .orElse(0);
    }
}
