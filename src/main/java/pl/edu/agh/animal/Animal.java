package pl.edu.agh.animal;

import lombok.Getter;
import lombok.Setter;
import pl.edu.agh.animal.genome.GeneToOrientatnionConverter;
import pl.edu.agh.animal.genome.Genome;
import pl.edu.agh.coordinates.Orientation;
import pl.edu.agh.coordinates.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Animal {

    @Getter
    private Orientation orientation;
    @Getter
    private int energy;
    @Getter
    private int livingTime=0;
    public final Genome genome;
    private final List<Birth> birthList=new ArrayList<>();

    public Animal(Vector2 position, Orientation orientation, int energy) {
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

    public void eat(int energy)
    {
        this.energy+=energy;
    }


    public Animal copulate(Animal other)
    {
        final Animal newborn=
                new Animal(this.orientation, (int) (this.energy*0.25+ other.energy*0.25),new Genome(this.genome,other.genome));
        this.energy*=0.75;
        other.energy*=0.75;
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
}
