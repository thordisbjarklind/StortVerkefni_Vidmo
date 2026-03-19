package is.vinnsla;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Leikmadur {
    private final SimpleStringProperty nafn = new SimpleStringProperty();
    private final IntegerProperty reiturProperty =
            new SimpleIntegerProperty(0);



    public Leikmadur(String nafn) {
        this.nafn.setValue(nafn);
    }

    /**
     * Færir peð leikmanns um i sæti en þó aldrei fram yfir max
     * @param reitur sæti sem á að færa peðið fram um
     * @param max hæsta sæti
     */
    public void faera(int reitur, int max) {
        this.reiturProperty.set(Math.min(max, this.reiturProperty.get()+reitur));
    }

    public String getNafn() {
        return nafn.get();
    }

    public SimpleStringProperty nafnProperty() {
        return nafn;
    }

    public int getReitur() { return reiturProperty.getValue(); }

    public IntegerProperty reiturProperty() {
        return reiturProperty;
    }

    @Override
    public String toString() {
        return getNafn();
    }
}
