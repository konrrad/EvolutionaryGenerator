package pl.edu.agh.map;

import pl.edu.agh.coordinates.Vector2;

import java.util.Set;

public interface Terrain {
    void plant(Set<Vector2> occupiedPositions);
    void deletePlant(Vector2 position);
    boolean isInBorder(final Vector2 position);
    boolean isGrown(final Vector2 position);
}
