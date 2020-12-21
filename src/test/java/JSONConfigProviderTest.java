import org.junit.jupiter.api.Test;
import pl.edu.agh.model.config.JSONConfigProvider;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSONConfigProviderTest {

    @Test
    public void readingTest()
    {
        JSONConfigProvider jsonConfigProvider=new JSONConfigProvider(new File(getClass().getResource("parameters.json").getFile()));
        assertEquals(100,jsonConfigProvider.getHeight());
        assertEquals(100,jsonConfigProvider.getWidth());
        assertEquals(0.75,jsonConfigProvider.getJungleRatio());
        assertEquals(100,jsonConfigProvider.getMoveEnergy());
        assertEquals(100,jsonConfigProvider.getNumberOfAnimals());
        assertEquals(100,jsonConfigProvider.getPlantEnergy());
        assertEquals(100,jsonConfigProvider.getStartEnergy());


    }
}
