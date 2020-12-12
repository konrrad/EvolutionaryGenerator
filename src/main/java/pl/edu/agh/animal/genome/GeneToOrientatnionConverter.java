package pl.edu.agh.animal.genome;

import pl.edu.agh.coordinates.Orientation;

public class GeneToOrientatnionConverter {
    public static Orientation geneToOrientation(int gene)
    {
        return Orientation.values()[gene];
    }
}
