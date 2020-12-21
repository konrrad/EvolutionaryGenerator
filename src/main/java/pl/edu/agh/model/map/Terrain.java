package pl.edu.agh.model.map;

import pl.edu.agh.model.coordinates.Vector2;

import java.util.Set;

public interface Terrain {
    void plant(Set<Vector2> occupiedPositions);
    void deletePlant(Vector2 position);
    boolean isInBorder(final Vector2 position);
    boolean isGrown(final Vector2 position);
    int getEnergyForPosition(final Vector2 position);
    Vector2 validatePosition(Vector2 position);
    int getNumberOfPlants();}
