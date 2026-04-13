package is.vinnsla;

import javafx.beans.property.SimpleObjectProperty;
import java.util.ArrayList;

public class Ludo {

    public enum LeikStada {
        I_GANGI, LOKID
    }

    private final Leikmadur[] leikmenn;
    private final Teningur teningur = new Teningur();
    private final ArrayList<Reitur>[] leidir = new ArrayList[4];
    private final SimpleObjectProperty<LeikStada> stada = new SimpleObjectProperty<>(LeikStada.I_GANGI);
    private final SimpleObjectProperty<Leikmadur> naestiLeikmadur = new SimpleObjectProperty<>();
    private int gerir = 0;
    private boolean bidurEftirPedVali = false;

    public Ludo(Leikmadur[] leikmenn) {
        this.leikmenn = leikmenn;
        smidaLeidir();
        gerir = finnaVirkanLeikmann(0);
        naestiLeikmadur.set(leikmenn[gerir]);
    }

    /**
     * Kastar tening og biður eftir peðvali.
     */
    public void kastaTeningi() {
        teningur.kasta();
        bidurEftirPedVali = true;
    }

    /**
     * Færir peð og athugar árekstra. Skilar true ef leikmaður vinnur.
     */
    public boolean faeraPed(int pedNumer) {
        int kast = teningur.getTala();
        Leikmadur nuverandi = leikmenn[gerir];

        boolean skorad = nuverandi.faera(pedNumer, kast);

        if (!skorad) {
            int pedStada = (pedNumer == 1) ? nuverandi.getPed1Stada() : nuverandi.getPed2Stada();
            if (pedStada <= 30) {
                athugaKnocking(gerir, pedStada);
            }
        }

        if (nuverandi.hefurSigrad()) {
            stada.set(LeikStada.LOKID);
            bidurEftirPedVali = false;
            return true;
        }

        if (kast == 6) {
            bidurEftirPedVali = false;
        } else {
            skippaLeikmann();
        }

        return false;
    }

    /**
     * Færir umferð á næsta virkan leikmann.
     */
    public void skippaLeikmann() {
        gerir = finnaVirkanLeikmann((gerir + 1) % 4);
        naestiLeikmadur.set(leikmenn[gerir]);
        bidurEftirPedVali = false;
    }

    public boolean isPedClickable(int pedNumer) {
        if (!bidurEftirPedVali) return false;
        return leikmenn[gerir].isPedClickable(pedNumer, teningur.getTala());
    }

    // Hjálparaðferðir

    /**
     * Athugar hvort peð lendi á andstæðingi og sendir heim ef svo.
     */
    private void athugaKnocking(int leikmannIndex, int pedStada) {
        Reitur target = leidir[leikmannIndex].get(pedStada);

        for (int i = 0; i < 4; i++) {
            if (i == leikmannIndex || !leikmenn[i].isVirkur()) continue;
            athugaPedKnocking(leikmenn[i], i, 1, target);
            athugaPedKnocking(leikmenn[i], i, 2, target);
        }
    }

    /**
     * Athugar eitt peð hjá andstæðingi.
     */
    private void athugaPedKnocking(Leikmadur lm, int lmIndex, int pedNumer, Reitur target) {
        boolean leyst = (pedNumer == 1) ? lm.isPed1Leyst() : lm.isPed2Leyst();
        int stada = (pedNumer == 1) ? lm.getPed1Stada() : lm.getPed2Stada();

        if (leyst && stada <= 30) {
            Reitur r = leidir[lmIndex].get(stada);
            if (r.getRow() == target.getRow() && r.getColumn() == target.getColumn()) {
                lm.sendaHeim(pedNumer);
            }
        }
    }

    private int finnaVirkanLeikmann(int fromIndex) {
        for (int i = 0; i < 4; i++) {
            int idx = (fromIndex + i) % 4;
            if (leikmenn[idx].isVirkur()) return idx;
        }
        return 0;
    }

    private void smidaLeidir() {
        int[][] gulur = {
                {1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{4,0},{5,0},
                {5,1},{5,2},{5,3},{6,3},{7,3},{8,3},{8,4},{8,5},
                {7,5},{6,5},{5,5},{5,6},{5,7},{5,8},{4,8},{3,8},
                {3,7},{3,6},{3,5},{2,5},{1,5},{0,5},{0,4},
                {1,4},{2,4},{3,4},{4,4}
        };
        int[][] raudur = {
                {5,1},{5,2},{5,3},{6,3},{7,3},{8,3},{8,4},{8,5},
                {7,5},{6,5},{5,5},{5,6},{5,7},{5,8},{4,8},{3,8},
                {3,7},{3,6},{3,5},{2,5},{1,5},{0,5},{0,4},{0,3},
                {1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{4,0},
                {4,1},{4,2},{4,3},{4,4}
        };
        int[][] graenn = {
                {7,5},{6,5},{5,5},{5,6},{5,7},{5,8},{4,8},{3,8},
                {3,7},{3,6},{3,5},{2,5},{1,5},{0,5},{0,4},{0,3},
                {1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{4,0},{5,0},
                {5,1},{5,2},{5,3},{6,3},{7,3},{8,3},{8,4},
                {7,4},{6,4},{5,4},{4,4}
        };
        int[][] blar = {
                {3,7},{3,6},{3,5},{2,5},{1,5},{0,5},{0,4},{0,3},
                {1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{4,0},{5,0},
                {5,1},{5,2},{5,3},{6,3},{7,3},{8,3},{8,4},{8,5},
                {7,5},{6,5},{5,5},{5,6},{5,7},{5,8},{4,8},
                {4,7},{4,6},{4,5},{4,4}
        };

        int[][][] allarLeidir = {gulur, raudur, graenn, blar};
        Reitur.Litur[] litir = {Reitur.Litur.GULUR, Reitur.Litur.RAUDUR, Reitur.Litur.GRAENN, Reitur.Litur.BLAR};

        for (int i = 0; i < 4; i++) {
            leidir[i] = new ArrayList<>();
            int[][] path = allarLeidir[i];
            for (int j = 0; j < path.length; j++) {
                Reitur.ReiturTypa typa;
                if (j == 0) typa = Reitur.ReiturTypa.START;
                else if (j >= 31 && j <= 33) typa = Reitur.ReiturTypa.HOME_STRETCH;
                else if (j == 34) typa = Reitur.ReiturTypa.GOAL;
                else typa = Reitur.ReiturTypa.NORMAL;

                Reitur.Litur litur = (j >= 31) ? litir[i] : null;
                leidir[i].add(new Reitur(path[j][1], path[j][0], typa, litur));
            }
        }
    }

    // Getter aðferðir
    public boolean isBidurEftirPedVali() { return bidurEftirPedVali; }
    public Reitur getReiturFyrirPed(int leikmannIndex, int pedStada) { return leidir[leikmannIndex].get(pedStada); }
    public Teningur getTeningur() { return teningur; }
    public Leikmadur getLeikmadur(int id) { return leikmenn[id]; }
    public SimpleObjectProperty<LeikStada> stadaProperty() { return stada; }
    public SimpleObjectProperty<Leikmadur> naestiLeikmadurProperty() { return naestiLeikmadur; }
    public ArrayList<Reitur> getLeid(int i) { return leidir[i]; }
}