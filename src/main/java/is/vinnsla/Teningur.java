package is.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import java.util.Random;

public class Teningur {
    private static final int MAX = 6;
    private final IntegerProperty tala = new SimpleIntegerProperty(MAX);
    private final Random random = new Random();

    public IntegerProperty talaProperty() {
        return tala;
    }

    public void kasta() {
        tala.set(random.nextInt(1, MAX + 1));
    }

    public int getTala() {
        return tala.get();
    }

    @Override
    public String toString() {
        return "Teningur{tala=" + tala.get() + "}";
    }
}