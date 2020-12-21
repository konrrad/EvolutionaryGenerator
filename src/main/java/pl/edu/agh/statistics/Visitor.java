package pl.edu.agh.statistics;

import pl.edu.agh.model.animal.Animal;

import java.util.Collection;

public interface Visitor {
    void visit(Collection<Animal> animalCollection);
}
