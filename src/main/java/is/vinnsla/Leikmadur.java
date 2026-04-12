package is.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class Leikmadur {

    public enum Dyr {
        KJUKLING("Kjúklingur"),
        KISA("Kisa"),
        HESTUR("Hestur"),
        HUNDUR("Hundur");

        private final String nafn;
        Dyr(String nafn) { this.nafn = nafn; }
        @Override public String toString() { return nafn; }
    }

    private final Reitur.Litur litur;
    private final Dyr dyr;
    private boolean virkur;

    private final IntegerProperty ped1Stada = new SimpleIntegerProperty(0);
    private final IntegerProperty ped2Stada = new SimpleIntegerProperty(0);
    private final BooleanProperty ped1Leyst = new SimpleBooleanProperty(false);
    private final BooleanProperty ped2Leyst = new SimpleBooleanProperty(false);

    public Leikmadur(Reitur.Litur litur, Dyr dyr, boolean virkur) {
        this.litur = litur;
        this.dyr = dyr;
        this.virkur = virkur;
    }

    /**
     * Færir peð eftir gildi á tening.
     * Ef peð er ekki á leiðinni og teningurinn er 6, setja peð á reit 0.
     * Skilar true ef peðið er komið í mark (reitur 34).
     */
    public boolean faera(int pedNumer, int kast) {
        IntegerProperty stada = getStada(pedNumer);
        BooleanProperty leyst = getLeyst(pedNumer);

        if (!leyst.get()) {
            if (kast == 6) {
                leyst.set(true);
                stada.set(0);
            }
            return false;
        }

        int nyrStadur = stada.get() + kast;
        if (nyrStadur >= 34) {
            stada.set(34);
            return true;
        }
        stada.set(nyrStadur);
        return false;
    }

    /**
     * Peð er smellanlegt ef það er ekki á leið og kast er 6,
     * eða ef það er á leið og hefur ekki klárað.
     */
    public boolean isPedClickable(int pedNumer, int kast) {
        if (!getLeyst(pedNumer).get()) return kast == 6;
        return getStada(pedNumer).get() < 34;
    }

    public boolean hefurSigrad() {
        return ped1Stada.get() == 34 && ped2Stada.get() == 34;
    }

    public void sendaHeim(int pedNumer) {
        getStada(pedNumer).set(0);
        getLeyst(pedNumer).set(false);
    }

    // Hjálparaðferðir til að fá rétt property eftir peðnúmeri
    private IntegerProperty getStada(int pedNumer) {
        return (pedNumer == 1) ? ped1Stada : ped2Stada;
    }

    private BooleanProperty getLeyst(int pedNumer) {
        return (pedNumer == 1) ? ped1Leyst : ped2Leyst;
    }

    // Getters og setters
    public Reitur.Litur getLitur() { return litur; }
    public Dyr getDyr() { return dyr; }
    public boolean isVirkur() { return virkur; }
    public void setVirkur(boolean virkur) { this.virkur = virkur; }

    public IntegerProperty ped1StadaProperty() { return ped1Stada; }
    public IntegerProperty ped2StadaProperty() { return ped2Stada; }
    public BooleanProperty ped1LeystProperty() { return ped1Leyst; }
    public BooleanProperty ped2LeystProperty() { return ped2Leyst; }

    public int getPed1Stada() { return ped1Stada.get(); }
    public int getPed2Stada() { return ped2Stada.get(); }
    public boolean isPed1Leyst() { return ped1Leyst.get(); }
    public boolean isPed2Leyst() { return ped2Leyst.get(); }

    @Override
    public String toString() {
        return dyr.toString();
    }
}