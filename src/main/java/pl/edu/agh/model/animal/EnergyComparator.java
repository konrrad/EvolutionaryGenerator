package pl.edu.agh.model.animal;

import java.util.Comparator;

public class EnergyComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal o1, Animal o2) {
        if (o1.getEnergy() < o2.getEnergy())
            return -1;
        if (o2.getEnergy() > o2.getEnergy())
            return 1;
        return 0;
    }
}
