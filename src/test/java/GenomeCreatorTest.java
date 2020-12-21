import org.junit.jupiter.api.RepeatedTest;
import pl.edu.agh.model.animal.genome.GenomeCreator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenomeCreatorTest {

    @RepeatedTest(10)
    public void getNumerosityTest() {
        try {
            GenomeCreator genomeCreator = new GenomeCreator();
            Method method = GenomeCreator.class.getDeclaredMethod("getNumerosity");
            method.setAccessible(true);
            List<Integer> result = ((List<Integer>) method.invoke(genomeCreator));
            System.out.println(result);
            assertEquals(32, result.stream().reduce(0, Integer::sum));

        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @RepeatedTest(10)
    public void shouldHave32Ints()
    {
        GenomeCreator genomeCreator=new GenomeCreator();
        List<Integer> genomeInts=genomeCreator.createRandomGenome();
        assertEquals(32,genomeInts.size());
    }

    @RepeatedTest(10)
    public void shouldHave8DistinctInts()
    {
        GenomeCreator genomeCreator=new GenomeCreator();
        List<Integer> genomeInts=genomeCreator.createRandomGenome();
        assertEquals(8,genomeInts.stream().distinct().count());
    }

    @RepeatedTest(10)
    public void shouldReturnCompleteGenome()
    {
        GenomeCreator genomeCreator=new GenomeCreator();
        List<Integer> genomeInts=genomeCreator.joinGenomes(genomeCreator.createRandomGenome(),genomeCreator.createRandomGenome());

        assertEquals(8,genomeInts.stream().distinct().count());
    }

    @RepeatedTest(10)
    public void shouldReturnSize32Genome()
    {
        GenomeCreator genomeCreator=new GenomeCreator();
        List<Integer> genomeInts=genomeCreator.joinGenomes(genomeCreator.createRandomGenome(),genomeCreator.createRandomGenome());

        assertEquals(32,genomeInts.size());
    }
}
