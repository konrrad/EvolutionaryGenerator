package pl.edu.agh.model.config;

public interface ConfigProvider {
    int getWidth();
    int getHeight();
    int getStartEnergy();
    int getMoveEnergy();
    int getPlantEnergy();
    double getJungleRatio();
    int getNumberOfAnimals();

}
