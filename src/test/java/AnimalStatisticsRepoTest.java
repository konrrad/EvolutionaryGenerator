import org.junit.jupiter.api.Test;
import pl.edu.agh.statistics.AnimalStatisticsRepository;
import pl.edu.agh.statistics.Epoch;
import pl.edu.agh.statistics.StatisticsRepository;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnimalStatisticsRepoTest {
    @Test
    public void save()
    {
        StatisticsRepository statisticsRepository=new AnimalStatisticsRepository();
        statisticsRepository.insert(new Epoch(1,2,3,4.5,5.5,2.2));
        statisticsRepository.dumpToFileAfterEpochs(1);
        assertTrue(new File("stats.json").exists());
    }
}
