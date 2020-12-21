import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import pl.edu.agh.model.animal.Animal;
import pl.edu.agh.model.coordinates.Orientation;
import pl.edu.agh.model.coordinates.Vector2;
import pl.edu.agh.model.map.Terrain;
import pl.edu.agh.model.world.World;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class WorldTest {
    public static final List<Orientation> possibleOrientations = Arrays.asList(Orientation.values());

    @Test
    public void shouldFindOneStrongest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        World world = new World(mock(Multimap.class), terrain, anyInt());
        Collection<Animal> animals = Arrays.asList(
                new Animal(any(Orientation.class), 10, anyInt()),
                new Animal(any(Orientation.class), 9, anyInt()),
                new Animal(any(Orientation.class), 8, anyInt()),
                new Animal(any(Orientation.class), 7, anyInt()),
                new Animal(any(Orientation.class), 5, anyInt()));
        Method method = World.class.getDeclaredMethod("findStrongest", Collection.class);
        method.setAccessible(true);
        List<Animal> result = ((List<Animal>) method.invoke(world, animals));
        assertEquals(result.size(), 1);
        assertEquals(result.get(0).getEnergy(), 10);
    }

    @Test
    public void shouldFindTwoStrongest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        World world = new World(mock(Multimap.class), terrain, anyInt());
        Collection<Animal> animals = Arrays.asList(
                new Animal(any(Orientation.class), 10, anyInt()),
                new Animal(any(Orientation.class), 10, anyInt()),
                new Animal(any(Orientation.class), 9, anyInt()),
                new Animal(any(Orientation.class), 8, anyInt()),
                new Animal(any(Orientation.class), 7, anyInt()),
                new Animal(any(Orientation.class), 5, anyInt()));
        Method method = World.class.getDeclaredMethod("findStrongest", Collection.class);
        method.setAccessible(true);
        List<Animal> result = ((List<Animal>) method.invoke(world, animals));
        assertEquals(result.size(), 2);
        result.forEach(a -> assertEquals(a.getEnergy(), 10));
    }

    @Test
    public void findTwoStrongestTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        World world = new World(mock(Multimap.class), terrain, anyInt());
        Animal first = new Animal(any(Orientation.class), 10, anyInt());
        Animal second = new Animal(any(Orientation.class), 9, anyInt());
        Collection<Animal> animals = Arrays.asList(
                first, second,
                new Animal(any(Orientation.class), 8, anyInt()),
                new Animal(any(Orientation.class), 7, anyInt()),
                new Animal(any(Orientation.class), 5, anyInt()));
        Method method = World.class.getDeclaredMethod("findTwoStrongest", Collection.class);
        method.setAccessible(true);
        Collection<Animal> result = ((Collection<Animal>) method.invoke(world, animals));
        assertEquals(result.size(), 2);
        System.out.println(result);
        assertTrue(result.contains(first) && result.contains(second));
    }

    @Test
    public void shouldFeedOnlyOne() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        when(terrain.getEnergyForPosition(any(Vector2.class))).thenReturn(3);

        Multimap<Vector2, Animal> map = ArrayListMultimap.create();
        Animal first = new Animal(getRandomOrientation(), 10, 4);
        Collection<Animal> animals = Arrays.asList(
                first,
                new Animal(getRandomOrientation(), 9, 4),
                new Animal(getRandomOrientation(), 8, 4),
                new Animal(getRandomOrientation(), 7, 4),
                new Animal(getRandomOrientation(), 5, 4));
        map.putAll(new Vector2(0,0),animals);
        World world = new World(map, terrain, anyInt());
        Method method = World.class.getDeclaredMethod("feedAnimals");
        method.setAccessible(true);
        method.invoke(world);
        assertEquals(13,first.getEnergy());
    }


    @Test
    public void shouldFeedTwo() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        when(terrain.getEnergyForPosition(any(Vector2.class))).thenReturn(4);

        Multimap<Vector2, Animal> map = ArrayListMultimap.create();
        Animal first = new Animal(getRandomOrientation(), 10, 4);
        Animal second = new Animal(getRandomOrientation(), 10, 4);

        Collection<Animal> animals = Arrays.asList(
                first,
                second,
                new Animal(getRandomOrientation(), 8, 4),
                new Animal(getRandomOrientation(), 7, 4),
                new Animal(getRandomOrientation(), 5, 4));
        map.putAll(new Vector2(0,0),animals);
        World world = new World(map, terrain, anyInt());
        Method method = World.class.getDeclaredMethod("feedAnimals");
        method.setAccessible(true);
        method.invoke(world);
        assertEquals(12,first.getEnergy());
        assertEquals(12,second.getEnergy());
    }

    @Test
    public void shouldDeleteTwoDead() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        when(terrain.getEnergyForPosition(any(Vector2.class))).thenReturn(4);

        Multimap<Vector2, Animal> map = ArrayListMultimap.create();
        Animal first = new Animal(getRandomOrientation(), 0, 4);
        Animal second = new Animal(getRandomOrientation(), 0, 4);

        Collection<Animal> animals = Arrays.asList(
                first,
                second,
                new Animal(getRandomOrientation(), 8, 4),
                new Animal(getRandomOrientation(), 7, 4),
                new Animal(getRandomOrientation(), 5, 4));
        map.putAll(any(Vector2.class),animals);
        World world = new World(map, terrain, anyInt());
        Method method = World.class.getDeclaredMethod("removeDead");
        method.setAccessible(true);
        method.invoke(world);
        assertFalse(map.containsValue(first));
        assertFalse(map.containsValue(second));
    }

    @Test
    public void shouldFindEmptyPositionOfEmptyAround() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        when(terrain.validatePosition(any(Vector2.class))).then(AdditionalAnswers.returnsFirstArg());
        when(terrain.isGrown(any(Vector2.class))).thenReturn(false);
        Multimap<Vector2, Animal> map = ArrayListMultimap.create();
        map.put(new Vector2(5,5),new Animal(getRandomOrientation(), 0, 4));
        map.put(new Vector2(5,5),new Animal(getRandomOrientation(), 0, 4));

        World world = new World(map, terrain, anyInt());
        Method method = World.class.getDeclaredMethod("findEmptyPositionAround", Vector2.class);
        method.setAccessible(true);
        Vector2 res=(Vector2)method.invoke(world,new Vector2(5,5));
        System.out.println(res);
        assertNotEquals(new Vector2(5,5),res);
    }

    @Test
    public void shouldFindEmptyPositionOneFreeAround() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        when(terrain.validatePosition(any(Vector2.class))).then(AdditionalAnswers.returnsFirstArg());
        when(terrain.isGrown(any(Vector2.class))).thenReturn(false);
        Multimap<Vector2, Animal> map = ArrayListMultimap.create();
        map.put(new Vector2(5,5),new Animal(getRandomOrientation(), 0, 4));

        map.put(new Vector2(6,5),new Animal(getRandomOrientation(), 0, 4));
        map.put(new Vector2(4,5),new Animal(getRandomOrientation(), 0, 4));
        map.put(new Vector2(5,6),new Animal(getRandomOrientation(), 0, 4));
        map.put(new Vector2(5,4),new Animal(getRandomOrientation(), 0, 4));

        map.put(new Vector2(4,4),new Animal(getRandomOrientation(), 0, 4));
        map.put(new Vector2(6,4),new Animal(getRandomOrientation(), 0, 4));
        map.put(new Vector2(6,6),new Animal(getRandomOrientation(), 0, 4));


        World world = new World(map, terrain, anyInt());
        Method method = World.class.getDeclaredMethod("findEmptyPositionAround", Vector2.class);
        method.setAccessible(true);
        Vector2 res=(Vector2)method.invoke(world,new Vector2(5,5));
        System.out.println(res);
        assertEquals(new Vector2(4,6),res);
    }

    @Test
    public void shouldCopulate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        when(terrain.validatePosition(any(Vector2.class))).then(AdditionalAnswers.returnsFirstArg());
        when(terrain.isGrown(any(Vector2.class))).thenReturn(false);
        Multimap<Vector2, Animal> map = ArrayListMultimap.create();
        map.put(new Vector2(5,5),new Animal(getRandomOrientation(), 6, 4));
        map.put(new Vector2(5,5),new Animal(getRandomOrientation(), 6, 4));

        World world = new World(map, terrain, anyInt());
        Method method = World.class.getDeclaredMethod("copulateAnimals");
        method.setAccessible(true);
        method.invoke(world);
        assertEquals(3,map.size());
    }


    @Test
    public void shouldNotCopulate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Terrain terrain = mock(Terrain.class);
        when(terrain.validatePosition(any(Vector2.class))).then(AdditionalAnswers.returnsFirstArg());
        when(terrain.isGrown(any(Vector2.class))).thenReturn(false);
        Multimap<Vector2, Animal> map = ArrayListMultimap.create();
        map.put(new Vector2(5,5),new Animal(getRandomOrientation(), 6, 4));
        map.put(new Vector2(5,5),new Animal(getRandomOrientation(), 3, 4));

        World world = new World(map, terrain, anyInt());
        Method method = World.class.getDeclaredMethod("copulateAnimals");
        method.setAccessible(true);
        method.invoke(world);
        assertEquals(2,map.size());
    }



    public Orientation getRandomOrientation() {
        Collections.shuffle(possibleOrientations);
        return possibleOrientations.get(0);
    }
}
