package pl.edu.agh.model.animal;

import lombok.Getter;
import pl.edu.agh.model.animal.genome.GeneToOrientatnionConverter;
import pl.edu.agh.model.animal.genome.Genome;
import pl.edu.agh.model.coordinates.Orientation;
import pl.edu.agh.model.coordinates.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Animal {

    @Getter
    private static int MINIMUM_REPRODUCTION_ENERGY;
    @Getter
    private Orientation orientation;
    @Getter
    private int energy;
    @Getter
    private int livingTime=0;
    public final Genome genome;
    private final List<Birth> birthList=new ArrayList<>();
    @Getter
    private int deathTime;

    public Animal(Orientation orientation, int energy,int minimum_copulation_energy) {
        MINIMUM_REPRODUCTION_ENERGY=minimum_copulation_energy;
        this.orientation = orientation;
        this.energy = energy;
        this.genome=new Genome();
    }

    public Animal(Orientation orientation,int energy,Genome genome)
    {
        this.orientation=orientation;
        this.energy=energy;
        this.genome=genome;
    }

    public boolean isAlive()
    {
        return energy>0;
    }

    public void die(int epoch)
    {
        this.deathTime=epoch;
    }

    public void eat(int energy)
    {
        this.energy+=energy;
    }

    public int getNumberOfChildren()
    {
        return this.birthList.size();
    }


    public Animal copulate(Animal other)
    {
        final Animal newborn=
                new Animal(this.orientation, (int) ((float)this.energy*0.25+ (float)other.energy*0.25),new Genome(this.genome,other.genome));
        this.energy= (int) ((float)this.energy*0.75);
        other.energy= (int) ((float)this.energy*0.75);
        birthList.add(new Birth(newborn,livingTime));
        return newborn;
    }

    public void increaseLivingTime()
    {
        this.livingTime++;
    }

    public Vector2 getPreferredDirectionVector()
    {
        return GeneToOrientatnionConverter.geneToOrientation(genome.getRandomGene()).toUnitVector();
    }

    public void takeEnergy(int moveEnergy) {
        this.energy-=moveEnergy;
    }

    public boolean canCopulate()
    {
        return this.energy>=MINIMUM_REPRODUCTION_ENERGY;
    }

    public int getNumberOfDescendants()
    {
        if(birthList.size()==0) return 0;
        return this.birthList.size()+birthList.stream().mapToInt(birth->birth.animal.getNumberOfDescendants()).sum();
    }


}
