package pl.edu.agh.animal.genome;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static pl.edu.agh.animal.genome.Genome.NUM_OF_DISTINCT_GENES;
import static pl.edu.agh.animal.genome.Genome.NUM_OF_GENES;

public class GenomeCreator {
    public static final Random RANDOMIZER = new Random();
    public static final List<Integer> POSSIBLE_GENES = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7));

    public List<Integer> joinGenomes(List<Integer> motherGenome, List<Integer> fatherGenome) {
        final int firstDividerInclusiveToFirstRange = getRandomNumber(1, NUM_OF_GENES - 3);
        final int secondDividerInclusiveToSecondRange = getRandomNumber(firstDividerInclusiveToFirstRange + 1, NUM_OF_GENES - 2);

        final List<List<Integer>> firstParentParts = divideToThreeParts(motherGenome, firstDividerInclusiveToFirstRange, secondDividerInclusiveToSecondRange);
        final List<List<Integer>> secondParentParts = divideToThreeParts(fatherGenome, firstDividerInclusiveToFirstRange, secondDividerInclusiveToSecondRange);
        final List<Integer> candidateList = new ArrayList<>();

        candidateList.addAll(firstParentParts.get(0));
        candidateList.addAll(secondParentParts.get(1));
        candidateList.addAll(firstParentParts.get(2));

        Collections.sort(candidateList);
        completeGenes(candidateList);

        return candidateList;
    }

    public List<Integer> createRandomGenome() {
        final List<Integer> numerosity = getNumerosity();
        return IntStream.range(0, NUM_OF_DISTINCT_GENES)
                .mapToObj(i -> new ArrayList<>(Collections.nCopies(numerosity.get(i), POSSIBLE_GENES.get(i))))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private void completeGenes(List<Integer> candidateList) {
        if (!allGenesPresent(candidateList)) {
            int[] geneToQuantity = new int[NUM_OF_DISTINCT_GENES];
            Arrays.fill(geneToQuantity, 0);
            candidateList.forEach(gene -> geneToQuantity[gene]++);
            List<Integer> missingGenes = getMissingGenes(candidateList);
            missingGenes.forEach(missing -> {
                final int dominating = getDominatingGene(geneToQuantity);
                candidateList.set(getGenesIdx(candidateList, dominating), missing);
                geneToQuantity[dominating]--;
                geneToQuantity[missing]++;

            });
            Collections.sort(candidateList);
        }
    }

    private int getGenesIdx(List<Integer> list, Integer gene) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(gene)) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    private List<Integer> getMissingGenes(List<Integer> genes) {
        final List<Integer> missing = new ArrayList<>();
        POSSIBLE_GENES.forEach(g -> {
            if (!genes.contains(g)) missing.add(g);
        });
        return missing;
    }

    private int getDominatingGene(int[] geneToQuantity) {
        int candidate = 0;
        int candidateOccurences = 0;
        for (int i = 0; i < geneToQuantity.length; i++) {
            if (geneToQuantity[i] > candidateOccurences) {
                candidate = i;
                candidateOccurences = geneToQuantity[i];
            }
        }
        return candidate;
    }

    private boolean allGenesPresent(List<Integer> genes) {
        return genes.stream().distinct().count() == NUM_OF_DISTINCT_GENES;
    }

    private List<List<Integer>> divideToThreeParts(List<Integer> listToDivide, final int firstDividerInclusiveToFirstRange, final int secondDividerInclusiveToSecondRange) {
        List<List<Integer>> result = new ArrayList<>();
        result.add(listToDivide.subList(0, firstDividerInclusiveToFirstRange + 1));
        result.add(listToDivide.subList(firstDividerInclusiveToFirstRange + 1, secondDividerInclusiveToSecondRange + 2));
        result.add(listToDivide.subList(secondDividerInclusiveToSecondRange + 2, listToDivide.size()));
        return result;
    }


    private int getRandomNumber(int fromInclusive, int toInclusive) {
        return fromInclusive + RANDOMIZER.nextInt(toInclusive - fromInclusive);
    }


    private List<Integer> getNumerosity() {
        List<Integer> numerosity = new ArrayList<>(Collections.nCopies(NUM_OF_DISTINCT_GENES, 1));
        int remaining = NUM_OF_GENES - NUM_OF_DISTINCT_GENES;
        while (remaining > 0) {
            final int idx = RANDOMIZER.nextInt(NUM_OF_DISTINCT_GENES);
            numerosity.set(idx, numerosity.get(idx) + 1);
            remaining--;
        }
        return numerosity;
    }
}
