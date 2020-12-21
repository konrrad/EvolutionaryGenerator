package pl.edu.agh.model.map;

import pl.edu.agh.model.coordinates.Vector2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public abstract class Zone {
    public static final Random RANDOMIZER=new Random();
    public static final int NUM_OF_PLACING_ATTEMPTS =100;
    public final Vector2 northEastCorner;
    public final Vector2 southWestCorner;
    protected Set<Vector2> plantsPositions;

    public Zone(Vector2 northEastCorner, Vector2 southWestCorner) {
        this.northEastCorner = northEastCorner;
        this.southWestCorner = southWestCorner;
        this.plantsPositions = new HashSet<>();
    }

    public boolean isInBorders(Vector2 position)
    {
        return position.lessOrEqual(northEastCorner)&&position.biggerOrEqual(southWestCorner);
    }

    public boolean isGrown(Vector2 position)
    {
        return plantsPositions.contains(position);
    }

    public void deletePlant(Vector2 position)
    {
        this.plantsPositions.remove(position);
    }

    public void plantRandomly(Set<Vector2> occupiedPositions)
    {
        for(int attemptNumber=0;attemptNumber<NUM_OF_PLACING_ATTEMPTS;attemptNumber++)
        {
            final Vector2 candidate=getCandidateForPlanting();
            if(!occupiedPositions.contains(candidate)&&plantsPositions.add(candidate)) return;

        }
    }

    protected Vector2 getCandidateForPlanting()
    {
        final int x=getRandomNumber(southWestCorner.X,northEastCorner.X);
        final int y=getRandomNumber(southWestCorner.Y,northEastCorner.Y);
        return new Vector2(x,y);
    }

    private int getRandomNumber(int fromInclusive, int toInclusive) {
        return fromInclusive + RANDOMIZER.nextInt(toInclusive - fromInclusive);
    }
}
