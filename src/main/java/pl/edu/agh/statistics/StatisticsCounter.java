package pl.edu.agh.statistics;

public interface StatisticsCounter {
    int getNumberOfLivingAnimals();
    int getNumberOfPlants();
    int getDominatingGenome();
    double getMeanEnergyForLiving();
    double getMeanLivingTimeForDead();
    double getMeanChildren();
}

