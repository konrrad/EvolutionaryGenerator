package pl.edu.agh.model.coordinates;

import java.util.Random;

public enum Orientation {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;
    public static final Random random= new Random();


    public Orientation next() {
        return Orientation.values()[(this.ordinal()+1)%values().length];
    }

    public Vector2 toUnitVector() {
        switch (this) {
            case SOUTHWEST -> {
                return new Vector2(-1, -1);

            }
            case SOUTHEAST -> {
                return new Vector2(1, -1);

            }
            case NORTHWEST -> {
                return new Vector2(-1, 1);

            }
            case NORTHEAST -> {
                return new Vector2(1, 1);
            }
            case SOUTH -> {
                return new Vector2(0, -1);
            }
            case NORTH -> {
                return new Vector2(0, 1);
            }
            case WEST -> {
                return new Vector2(-1, 0);
            }
            case EAST -> {
                return new Vector2(1, 0);
            }
        }
        throw new IllegalStateException();
    }

    public static Orientation getRandomOrientation()
    {
        return values()[random.nextInt(values().length)];
    }
}
