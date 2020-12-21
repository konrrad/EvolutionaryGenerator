package pl.edu.agh.model.map;

import pl.edu.agh.model.coordinates.Vector2;

import java.util.Set;

public class TerrainWithJungleInCenterAndSteppe implements Terrain {
    public final int WIDTH;
    public final int HEIGHT;
    public final Vector2 northEastCorner;
    public final Vector2 southWestCorner;
    public final Vector2 center;
    public final int JUNGLE_WIDTH;
    public final int JUNGLE_HEIGHT;
    private Jungle jungle;
    private Steppe steppe;
    private final int plantEnergy;


    public TerrainWithJungleInCenterAndSteppe(final int WIDTH, final int HEIGHT, double jungleToSteppeRatio, int plantEnergy) {
        this.plantEnergy=plantEnergy;
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.southWestCorner = new Vector2(0, 0);
        this.northEastCorner = new Vector2(WIDTH - 1, HEIGHT - 1);
        this.center = new Vector2((WIDTH - 1) / 2, (HEIGHT - 1) / 2);
        this.JUNGLE_HEIGHT = (int) (jungleToSteppeRatio * HEIGHT);
        this.JUNGLE_WIDTH = (int) (jungleToSteppeRatio * WIDTH);
        createZones();
    }

    private void createZones() {
        final Vector2 jungleNE = new Vector2(center.X + (JUNGLE_WIDTH - 1) / 2, center.Y + (JUNGLE_HEIGHT - 1) / 2);
        final Vector2 jungleSW = new Vector2(center.X - (JUNGLE_WIDTH - 1) / 2, center.Y - (JUNGLE_HEIGHT - 1) / 2);
        jungle = new Jungle(jungleNE, jungleSW);
        steppe = new Steppe(northEastCorner, southWestCorner, jungleNE, jungleSW);
    }

    public int getNumOfPlants() {
        return jungle.plantsPositions.size() + steppe.plantsPositions.size();
    }

    @Override
    public void plant(Set<Vector2> occupiedPositions) {
        jungle.plantRandomly(occupiedPositions);
        steppe.plantRandomly(occupiedPositions);
    }

    @Override
    public void deletePlant(final Vector2 position) {
        jungle.deletePlant(position);
        steppe.deletePlant(position);
    }

    @Override
    public boolean isInBorder(final Vector2 position) {
        return position.lessOrEqual(northEastCorner) && position.biggerOrEqual(southWestCorner);
    }

    @Override
    public boolean isGrown(final Vector2 position) {
        return jungle.isGrown(position) || steppe.isGrown(position);
    }

    @Override
    public int getEnergyForPosition(Vector2 position) {
        if(isGrown(position))
            return this.plantEnergy;
        return 0;
    }

    @Override
    public Vector2 validatePosition(Vector2 position) {
        return isInBorder(position)? position: new Vector2((position.X+WIDTH)%WIDTH,(position.Y+HEIGHT)%HEIGHT);
    }

    @Override
    public int getNumberOfPlants() {
        return jungle.getNumberOfPlants()+steppe.getNumberOfPlants();
    }


}
