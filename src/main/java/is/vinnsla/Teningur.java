package is.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.IntegerProperty;
import java.util.Random;

/**
 * @author: Logi Halldórsson, Háskóli Íslands, loh19@hi.is
 * @author: Þórdís Bjarklind Gunnarsdóttir, Háskóli Íslands, tbg18@hi.is
 *
 * Lýsir teningi í Ludo leiknum.
 * Sér um að kasta og geyma núverandi gildi tenings með JavaFX property.
 */
public class Teningur {
    private static final int MAX = 6;
    private final IntegerProperty tala = new SimpleIntegerProperty(MAX);
    private final Random random = new Random();

    public IntegerProperty talaProperty() {
        return tala;
    }

    /**
     * Kastar teningi og setur nýtt gildi á milli 1 og 6.
     */
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