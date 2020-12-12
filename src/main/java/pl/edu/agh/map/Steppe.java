package pl.edu.agh.map;

import pl.edu.agh.coordinates.Vector2;

import java.util.Set;

public class Steppe extends Zone{


    private Vector2 forbiddenSW;
    private Vector2 forbiddenNE;

    public Steppe(Vector2 northEastCorner, Vector2 southWestCorner) {
        super(northEastCorner, southWestCorner);
    }

    public Steppe(Vector2 northEastCorner, Vector2 southWestCorner,Vector2 forbiddenNE,Vector2 forbiddenSW)
    {
        super(northEastCorner,southWestCorner);
        this.forbiddenNE=forbiddenNE;
        this.forbiddenSW=forbiddenSW;
    }

    @Override
    public void plantRandomly(Set<Vector2> occupiedPositions) {
        for(int attemptNumber=0;attemptNumber<NUM_OF_PLACING_ATTEMPTS;attemptNumber++)
        {
            final Vector2 candidate=getCandidateForPlanting();
            if(!occupiedPositions.contains(candidate)&&!isForbidden(candidate)&&plantsPositions.add(candidate)) return;
        }
    }

    private boolean isForbidden(Vector2 position)
    {
        return !(position.lessOrEqual(forbiddenNE)&&position.biggerOrEqual(forbiddenSW));
    }
}
