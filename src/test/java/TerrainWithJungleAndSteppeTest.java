import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.edu.agh.model.coordinates.Vector2;
import pl.edu.agh.model.map.TerrainWithJungleInCenterAndSteppe;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TerrainWithJungleAndSteppeTest {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 100;
    public TerrainWithJungleInCenterAndSteppe terrain;

    @BeforeEach
    public void createTerrain() {
        terrain = new TerrainWithJungleInCenterAndSteppe(WIDTH, HEIGHT, 0.5,3);
    }


    @Test
    public void creationTest() {
        assertEquals(terrain.northEastCorner, new Vector2(WIDTH - 1, HEIGHT - 1));
        assertEquals(terrain.southWestCorner, new Vector2(0, 0));
    }

    @Test
    public void sholudPlant90Plants() {
        final int numOfPlants = 90;
        plantPlants(numOfPlants, new HashSet<>());
        assertEquals(numOfPlants, terrain.getNumOfPlants());
    }

    @Test
    public void shouldPlantLessPlantsBcOccupied() {
        final int numOfPlants = 9999;
        Set<Vector2> occupied = new HashSet<>(Arrays.asList(
                new Vector2(0, 0),
                new Vector2(10, 1),
                new Vector2(1, 10),
                new Vector2(23, 12),
                new Vector2(1, 13),
                new Vector2(14, 14),
                new Vector2(61, 16),
                new Vector2(10, 45)
        ));
        plantPlants(numOfPlants, new HashSet<>());
        assertTrue(terrain.getNumOfPlants() < numOfPlants);
    }

    @Test
    public void shouldNotPlantOnOccupied() {
        final int numOfPlants = 9999;
        Set<Vector2> occupied = new HashSet<>(Arrays.asList(
                new Vector2(0, 0),
                new Vector2(10, 1),
                new Vector2(1, 10),
                new Vector2(23, 12),
                new Vector2(1, 13),
                new Vector2(14, 14),
                new Vector2(61, 16),
                new Vector2(10, 45)
        ));
        plantPlants(numOfPlants, new HashSet<>());
        occupied.forEach(field -> assertFalse(terrain.isGrown(field)));
    }

    private void plantPlants(int numOfPlants, Set<Vector2> occupied) {
        for (int i = 0; i < numOfPlants / 2; i++) {
            terrain.plant(occupied);
        }
    }

    @Test
    public void isGrownTest() {
        final int numOfPlants = 40;
        plantPlants(numOfPlants, new HashSet<>());
        int numOfGrownFields = 0;
        for (int x = 0; x < terrain.WIDTH; x++) {
            for (int y = 0; y < terrain.HEIGHT; y++) {
                final Vector2 position = new Vector2(x, y);
                if (terrain.isGrown(position)) numOfGrownFields++;
            }
        }
        assertEquals(numOfPlants, numOfGrownFields);
    }

    @Test
    public void deletePlantTest() {
        final int numOfPlants = 80;
        plantPlants(numOfPlants, new HashSet<>());
        for (int x = 0; x < terrain.WIDTH; x++) {
            for (int y = 0; y < terrain.HEIGHT; y++) {
                final Vector2 position = new Vector2(x, y);
                terrain.deletePlant(position);
            }
        }
        assertEquals(0, terrain.getNumOfPlants());
    }

    @Test
    public void shouldNotBeInBorders() {
        Vector2 position1 = new Vector2(100, 1);
        assertFalse(terrain.isInBorder(position1));

        Vector2 position2 = new Vector2(-1, 0);
        assertFalse(terrain.isInBorder(position2));

        Vector2 position3 = new Vector2(101, 5);
        assertFalse(terrain.isInBorder(position3));
    }

    @Test
    public void shouldBeInBorders() {
        Vector2 position1 = new Vector2(0, 0);
        assertTrue(terrain.isInBorder(position1));

        Vector2 position2 = new Vector2(99, 99);
        assertTrue(terrain.isInBorder(position2));

        Vector2 position3 = new Vector2(49, 30);
        assertTrue(terrain.isInBorder(position3));
    }

    @Test
    public void validatePositionTest()
    {
        assertEquals(new Vector2(0,0),terrain.validatePosition(new Vector2(WIDTH,HEIGHT)));
        assertEquals(new Vector2(0,40),terrain.validatePosition(new Vector2(WIDTH,40)));
        assertEquals(new Vector2(10,0),terrain.validatePosition(new Vector2(10,HEIGHT)));
        assertEquals(new Vector2(10,HEIGHT-1),terrain.validatePosition(new Vector2(10,-1)));
        assertEquals(new Vector2(WIDTH-1,20),terrain.validatePosition(new Vector2(-1,20)));
    }
}
