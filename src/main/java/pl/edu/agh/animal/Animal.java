package pl.edu.agh.animal;

import lombok.Getter;

public class Animal {
    private Vector2 position;
    private Orientation orientation;
    @Getter
    private int energy;
    public final Genome genome=new Genome();


    public boolean isAlive()
    {
        return energy>0;
    }

    public void eat(int energy)
    {
        this.energy+=energy;
    }
}
