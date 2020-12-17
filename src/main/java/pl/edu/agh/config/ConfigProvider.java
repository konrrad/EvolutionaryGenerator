package pl.edu.agh.config;

public interface ConfigProvider {
    int getWidth();
    int getHeight();
    int getStartEnergy();
    int getMoveEnergy();
    int getPlantEnergy();
    double getJungleRatio();
    int getNumberOfAnimals();

}
