package is.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

public class Leikmadur {

    public enum Dyr {
        KISA, HUNDUR, KJUKLING, HESTUR
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
     * Færir peð eftir gildi á tening
     * Ef peð er ekki á leiðinni og teningurinn er 6, setja peð á reit 0
     * Skilar true ef peðið er búið að vinna (reitur 34)
     */
    public boolean faera(int pedNumer, int kast) {
        IntegerProperty stada = (pedNumer == 1) ? ped1Stada : ped2Stada;
        BooleanProperty leyst = (pedNumer == 1) ? ped1Leyst : ped2Leyst;

        // Sleppa peði á reit 0
        if (!leyst.get()) {
            if (kast == 6) {
                leyst.set(true);
                stada.set(0); // reitur 0
                return false;
            }
            return false;
        }

        // Færa áfram á leið
        int nyrStadur = stada.get() + kast;
        if (nyrStadur >= 34) {
            stada.set(34);
            return true;
        }
        stada.set(nyrStadur);
        return false;
    }

    /**
     * Peð er smellanlegt ef það það er ekki á leið og teningurinn er 6,
     * eða það er á leið og hefur ekki klárað.
     */
    public boolean isPedClickable(int pedNumer, int kast) {
        if (pedNumer == 1) {
            if (!ped1Leyst.get()) return kast == 6;
            return ped1Stada.get() < 34;
        } else {
            if (!ped2Leyst.get()) return kast == 6;
            return ped2Stada.get() < 34;
        }
    }

    public boolean hefurSigrad() {
        return ped1Stada.get() == 34 && ped2Stada.get() == 34;
    }

    public Reitur.Litur getListur() { return litur; }
    public Dyr getDyr() { return dyr; }
    public boolean isVirkur() { return virkur; }
    public void setVirkur(boolean virkur) { this.virkur = virkur; }

    public IntegerProperty ped1StadaProperty() { return ped1Stada; }
    public IntegerProperty ped2StadaProperty() { return ped2Stada; }
    public BooleanProperty ped1LeystProperty() { return ped1Leyst; }
    public BooleanProperty ped2LeystProperty() { return ped2Leyst; }

    public int getPed1Stada() { return ped1Stada.get(); }
    public int getPed2Stada() { return ped2Stada.get(); }

    @Override
    public String toString() {
        return dyr + " (" + litur + ")";
    }

    public void sendaHeim(int pedNumer) {
        if (pedNumer == 1) {
            ped1Stada.set(0);
            ped1Leyst.set(false);
        } else {
            ped2Stada.set(0);
            ped2Leyst.set(false);
        }
    }

    public boolean isPed1Leyst() {
        return ped1Leyst.get();
    }

    public boolean isPed2Leyst() {
        return ped2Leyst.get();
    }
}