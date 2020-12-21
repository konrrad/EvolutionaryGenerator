package pl.edu.agh.model.animal.genome;

import pl.edu.agh.model.coordinates.Orientation;

public class GeneToOrientatnionConverter {
    public static Orientation geneToOrientation(int gene)
    {
        return Orientation.values()[gene];
    }
}
