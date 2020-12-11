package pl.edu.agh.animal;

public enum Orientation {
    NORTH, NORTHEAST, EAST, SOUTHEAST, SOUTH, SOUTHWEST, WEST, NORTHWEST;

    public Orientation next() {
        switch (this) {
            case SOUTHWEST -> {
                return WEST;
            }
            case SOUTHEAST -> {
                return SOUTH;
            }
            case NORTHWEST -> {
                return NORTH;
            }
            case NORTHEAST -> {
                return EAST;
            }
            case SOUTH -> {
                return SOUTHWEST;
            }
            case NORTH -> {
                return NORTHEAST;
            }
            case WEST -> {
                return NORTHWEST;
            }
            case EAST -> {
                return SOUTHEAST;
            }
        }
        throw new IllegalStateException();
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
}
