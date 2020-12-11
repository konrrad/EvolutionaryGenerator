package pl.edu.agh.map;

import pl.edu.agh.animal.Vector2;

public interface Terrain {
    void plant();
    void deletePlant(Vector2 position);
    boolean isInBorder(final Vector2 position);
    boolean isGrown(final Vector2 position);
}
