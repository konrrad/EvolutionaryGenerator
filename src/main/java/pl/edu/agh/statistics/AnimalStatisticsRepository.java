package pl.edu.agh.statistics;

import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AnimalStatisticsRepository implements StatisticsRepository {
    List<Epoch> epochList = new ArrayList<>();

    public AnimalStatisticsRepository() {
    }

    @Override
    public void insert(Epoch e) {
        epochList.add(e);
    }

    @Override
    public void dumpToFileAfterEpochs(int numOfEpoch) {
        dumpToFile(epochList.subList(0, numOfEpoch));
    }

    private void dumpToFile(List<Epoch> epochList) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("LivingAnimals", calculateAvgLivingAnimals(epochList));
        jsonObject.put("NumOfPlants", calculateAvgNumOfPlants(epochList));
        jsonObject.put("DominatingGenome", calculateDominatingGenome(epochList));
        jsonObject.put("AVGEnergy", calculateAvgEnergy(epochList));
        jsonObject.put("MeanLivingTimeForDead", calculateMeanLivingTime(epochList));
        jsonObject.put("MeanChildren", calculateMeanChildren(epochList));

        try (FileWriter fw = new FileWriter("stats.json")) {
            fw.write(jsonObject.toJSONString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Double calculateAvgLivingAnimals(List<Epoch> epochList) {
        return epochList.stream().mapToDouble(Epoch::getNumberOfLivingAnimals)
                .average().orElse(0);
    }

    private Double calculateAvgNumOfPlants(List<Epoch> epochList) {
        return epochList.stream().mapToDouble(Epoch::getNumberOfPlants)
                .average().orElse(0);
    }

    private Double calculateDominatingGenome(List<Epoch> epochList) {
        return epochList.stream().mapToDouble(Epoch::getDominatingGenome)
                .average().orElse(0);
    }

    private Double calculateAvgEnergy(List<Epoch> epochList) {
        return epochList.stream().mapToDouble(Epoch::getMeanEnergyForLiving)
                .average().orElse(0);
    }

    private Double calculateMeanLivingTime(List<Epoch> epochList) {
        return epochList.stream().mapToDouble(Epoch::getMeanLivingTimeForDead)
                .average().orElse(0);
    }

    private Double calculateMeanChildren(List<Epoch> epochList) {
        return epochList.stream().mapToDouble(Epoch::getMeanChildren)
                .average().orElse(0);
    }


}
