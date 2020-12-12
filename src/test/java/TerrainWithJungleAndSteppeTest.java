import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.animal.Vector2;
import pl.edu.agh.map.Terrain;
import pl.edu.agh.map.TerrainWithJungleInCenterAndSteppe;

import static org.junit.jupiter.api.Assertions.*;

public class TerrainWithJungleAndSteppeTest {
    public TerrainWithJungleInCenterAndSteppe terrain;
    public static final int WIDTH=100;
    public static final int HEIGHT=100;

    @BeforeEach
    public void createTerrain()
    {
        terrain=new TerrainWithJungleInCenterAndSteppe(WIDTH,HEIGHT,0.5);
    }


    @Test
    public void creationTest()
    {
        assertEquals(terrain.northEastCorner,new Vector2(WIDTH-1,HEIGHT-1));
        assertEquals(terrain.southWestCorner,new Vector2(0,0));
    }

    @Test
    public void sholudPlant90Plants()
    {
        final int numOfPlants=90;
        plantPlants(numOfPlants);
        assertEquals(numOfPlants,terrain.getNumOfPlants());
    }

    private void plantPlants(int numOfPlants)
    {
        for (int i=0;i<numOfPlants/2;i++)
        {
            terrain.plant();
        }
    }

    @Test
    public void isGrownTest()
    {
        final int numOfPlants=80;
        plantPlants(numOfPlants);
        int numOfGrownFields=0;
        for (int x = 0; x < terrain.WIDTH; x++)
        {
            for (int y = 0; y < terrain.HEIGHT; y++) {
                final Vector2 position=new Vector2(x,y);
                if(terrain.isGrown(position)) numOfGrownFields++;
            }
        }
        assertEquals(numOfPlants,numOfGrownFields);
    }

    @Test
    public void deletePlantTest()
    {
        final int numOfPlants=80;
        plantPlants(numOfPlants);
        for (int x = 0; x < terrain.WIDTH; x++)
        {
            for (int y = 0; y < terrain.HEIGHT; y++) {
                final Vector2 position=new Vector2(x,y);
                terrain.deletePlant(position);
            }
        }
        assertEquals(0,terrain.getNumOfPlants());
    }

    @Test
    public void shouldNotBeInBorders()
    {
        Vector2 position1=new Vector2(100,1);
        assertFalse(terrain.isInBorder(position1));

        Vector2 position2=new Vector2(-1,0);
        assertFalse(terrain.isInBorder(position2));

        Vector2 position3=new Vector2(101,5);
        assertFalse(terrain.isInBorder(position3));
    }

    @Test
    public void shouldBeInBorders()
    {
        Vector2 position1=new Vector2(0,0);
        assertTrue(terrain.isInBorder(position1));

        Vector2 position2=new Vector2(99,99);
        assertTrue(terrain.isInBorder(position2));

        Vector2 position3=new Vector2(49,30);
        assertTrue(terrain.isInBorder(position3));
    }
}
