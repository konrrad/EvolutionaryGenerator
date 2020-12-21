package pl.edu.agh.statistics;

import lombok.Getter;

public class Epoch {
    private static int number_of_epochs=1;
    @Getter
    final int id;
    @Getter
    final int NumberOfLivingAnimals;
    @Getter
    final int NumberOfPlants;
    @Getter
    final int DominatingGenome;
    @Getter
    final double MeanEnergyForLiving;
    @Getter
    final double MeanLivingTimeForDead;
    @Getter
    final double MeanChildren;

    public Epoch(int numberOfLivingAnimals, int numberOfPlants, int dominatingGenome, double meanEnergyForLiving, double meanLivingTimeForDead, double meanChildren) {
        this.id = number_of_epochs++;
        NumberOfLivingAnimals = numberOfLivingAnimals;
        NumberOfPlants = numberOfPlants;
        DominatingGenome = dominatingGenome;
        MeanEnergyForLiving = meanEnergyForLiving;
        MeanLivingTimeForDead = meanLivingTimeForDead;
        MeanChildren = meanChildren;
    }
}
