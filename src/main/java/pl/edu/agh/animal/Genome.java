package pl.edu.agh.animal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Genome {
    public static final int NUM_OF_GENES=32;
    public static final int NUM_OF_DISTINCT_GENES=8;
    private static final GenomeCreator GENOME_CREATOR=new GenomeCreator();
    private List<Integer> genes;
    private int dominatingGene=-1;


    public Genome()
    {
        this.genes=GENOME_CREATOR.createRandomGenome();
    }

    public Genome(Genome firstParentGenome, Genome secondParentGenome)
    {
        this.genes=GENOME_CREATOR.joinGenomes(firstParentGenome.genes,secondParentGenome.genes);
    }

    public int getDominatingGene()
    {
        if(dominatingGene==-1)
        {
            dominatingGene=this.genes.stream()
                    .collect(Collectors.groupingBy(el->el,Collectors.counting()))
                    .entrySet().stream().max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey).orElseThrow();
        }
        return dominatingGene;
    }
}
