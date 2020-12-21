package pl.edu.agh.model.world;

import pl.edu.agh.statistics.Visitor;

public interface Visitable{
    void accept(Visitor v);
}
