package pl.edu.agh.map;

import pl.edu.agh.animal.Vector2;

import java.util.ArrayList;
import java.util.List;

public class TerrainWithJungleInCenterAndSteppe implements Terrain {
    public final int WIDTH;
    public final int HEIGHT;
    public final Vector2 northEastCorner;
    public final Vector2 southWestCorner;
    public final Vector2 center;
    private List<Zone> zones;
    public final int JUNGLE_WIDTH;
    public final int JUNGLE_HEIGHT;


    public TerrainWithJungleInCenterAndSteppe(final int WIDTH,final int HEIGHT,double jungleToSteppeRatio) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.southWestCorner=new Vector2(0,0);
        this.northEastCorner=new Vector2(WIDTH-1,HEIGHT-1);
        this.center=new Vector2((WIDTH-1)/2,(HEIGHT-1)/2);
        this.JUNGLE_HEIGHT= (int) (jungleToSteppeRatio*HEIGHT);
        this.JUNGLE_WIDTH= (int) (jungleToSteppeRatio*WIDTH);
        createZones();
    }

    private void createZones()
    {
        zones=new ArrayList<>();
        final Vector2 jungleNE=new Vector2(center.X+(WIDTH-1)/2,center.Y+(HEIGHT-1)/2);
        final Vector2 jungleSW=new Vector2(center.X-(WIDTH-1)/2,center.Y-(HEIGHT-1)/2);
        zones.add(new Jungle(jungleNE,jungleSW));
        zones.add(new Steppe(northEastCorner,southWestCorner,jungleNE,jungleSW));
    }

    public int getNumOfPlants()
    {
        return zones.stream().map(zone -> zone.plantsPositions.size()).reduce(0, Integer::sum);
    }

    @Override
    public void plant() {
        zones.forEach(Zone::plantRandomly);
    }

    @Override
    public void deletePlant(final Vector2 position) {
        zones.forEach(zone -> zone.deletePlant(position));
    }

    @Override
    public boolean isInBorder(final Vector2 position) {
        return position.lessOrEqual(northEastCorner)&&position.biggerOrEqual(southWestCorner);
    }

    @Override
    public boolean isGrown(final Vector2 position) {
        return zones.stream().anyMatch(zone -> zone.isGrown(position));
    }


}
