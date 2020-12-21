package pl.edu.agh.statistics;

public interface StatisticsRepository {
    void insert(Epoch e);
    void dumpToFileAfterEpochs(int numOfEpoch);
}
