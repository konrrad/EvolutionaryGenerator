package pl.edu.agh.config;

public interface ConfigProvider {
    int getWidth();
    int getHeigth();
    int getStartEnergy();
    int getMoveEnergy();
    int getPlantEnergy();
    double getJungleRatio();
    int getNumberOfAnimals();

}
