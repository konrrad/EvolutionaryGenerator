package pl.edu.agh.animal;

public class Birth {
    public final Animal animal;
    public final int EPOCH;

    public Birth(Animal animal, int EPOCH) {
        this.animal = animal;
        this.EPOCH = EPOCH;
    }
}
