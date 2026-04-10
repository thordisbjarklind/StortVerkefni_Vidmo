package is.vinnsla;

import javafx.beans.property.SimpleObjectProperty;
import java.util.ArrayList;

public class Ludo {

    public enum LeikStada {
        I_GANGI, LOKID
    }

    private Leikmadur[] leikmenn = new Leikmadur[4];
    private Teningur teningur = new Teningur();
    private int gerir = 0;
    private SimpleObjectProperty<LeikStada> stada = new SimpleObjectProperty<>(LeikStada.I_GANGI);
    private SimpleObjectProperty<Leikmadur> naestiLeikmadur = new SimpleObjectProperty<>();
    private boolean bidurEftirPedVali = false;

    private ArrayList<Reitur>[] leidir = new ArrayList[4];

    public Ludo(Leikmadur[] leikmenn) {
        this.leikmenn = leikmenn;
        smidaLeidir();
        gerir = finnaVirkanLeikmann(0);
        naestiLeikmadur.set(leikmenn[gerir]);
    }

    private void smidaLeidir() {
        int[][] gulur = {
                {1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{4,0},{5,0},
                {5,1},{5,2},{5,3},{6,3},{7,3},{8,3},{8,4},{8,5},
                {7,5},{6,5},{5,5},{5,6},{5,7},{5,8},{4,8},{3,8},
                {3,7},{3,6},{3,5},{2,5},{1,5},{0,5},{0,4},
                {1,4},{2,4},{3,4},
                {4,4}
        };

        int[][] raudur = {
                {3,7},{3,6},{3,5},{2,5},{1,5},{0,5},{0,4},{0,3},
                {1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{4,0},{5,0},
                {5,1},{5,2},{5,3},{6,3},{7,3},{8,3},{8,4},{8,5},
                {7,5},{6,5},{5,5},{5,6},{5,7},{5,8},{4,8},
                {4,7},{4,6},{4,5},
                {4,4}
        };

        int[][] graenn = {
                {7,5},{6,5},{5,5},{5,6},{5,7},{5,8},{4,8},{3,8},
                {3,7},{3,6},{3,5},{2,5},{1,5},{0,5},{0,4},{0,3},
                {1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{4,0},{5,0},
                {5,1},{5,2},{5,3},{6,3},{7,3},{8,3},{8,4},
                {7,4},{6,4},{5,4},
                {4,4}
        };

        int[][] blar = {
                {5,1},{5,2},{5,3},{6,3},{7,3},{8,3},{8,4},{8,5},
                {7,5},{6,5},{5,5},{5,6},{5,7},{5,8},{4,8},{3,8},
                {3,7},{3,6},{3,5},{2,5},{1,5},{0,5},{0,4},{0,3},
                {1,3},{2,3},{3,3},{3,2},{3,1},{3,0},{4,0},
                {4,1},{4,2},{4,3},
                {4,4}
        };

        // smá ruglandi en þetta er einfaldasta leiðin til að gera þetta held ég. að nota 3D lista fyrir for lykkjuna.
        int[][][] allarLeidir = {gulur, raudur, graenn, blar};
        Reitur.Litur[] litir = {
                Reitur.Litur.GULUR,
                Reitur.Litur.RAUDUR,
                Reitur.Litur.GRAENN,
                Reitur.Litur.BLAR
        };

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

    /**
     * Kallað þegar smellt er á tening
     * Kastar tening og biður eftir að það sé smellt á peð
     */
    public void kastaTeningi() {
        teningur.kasta();
        bidurEftirPedVali = true;
    }

    /**
     * Kallað þegar smellt er á peð.
     * Færir peð og athugar á árekstrum eða hvort peð sé komið í mark, skilar þá true.
     */
    public boolean faeraPed(int pedNumer) {
        int kast = teningur.getTala();
        Leikmadur nuverandi = leikmenn[gerir];

        // færir peð, skilar true ef það fer í mark
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

        // Ef kastið er 6, gera aftur
        if (kast == 6) {
            bidurEftirPedVali = false;
        } else {
            // annars, næsti spilari
            gerir = finnaVirkanLeikmann((gerir + 1) % 4);
            naestiLeikmadur.set(leikmenn[gerir]);
            bidurEftirPedVali = false;
        }

        return false;
    }

    /**
     * Athugar hvort annað peð er á sama reit. Ef svo, senda heim.
     */
    private void athugaKnocking(int leikmannIndex, int pedStada) {
        Reitur targetReitur = leidir[leikmannIndex].get(pedStada);

        for (int i = 0; i < 4; i++) {
            if (i == leikmannIndex) continue;
            if (!leikmenn[i].isVirkur()) continue;

            int p1Stada = leikmenn[i].getPed1Stada();
            if (leikmenn[i].isPed1Leyst() && p1Stada <= 30) {
                Reitur opponentReitur = leidir[i].get(p1Stada);
                if (opponentReitur.getRow() == targetReitur.getRow() &&
                        opponentReitur.getColumn() == targetReitur.getColumn()) {
                    leikmenn[i].sendaHeim(1);
                    System.out.println(leikmenn[i] + " ped 1 sent home!");
                }
            }

            int p2Stada = leikmenn[i].getPed2Stada();
            if (leikmenn[i].isPed2Leyst() && p2Stada <= 30) {
                Reitur opponentReitur = leidir[i].get(p2Stada);
                if (opponentReitur.getRow() == targetReitur.getRow() &&
                        opponentReitur.getColumn() == targetReitur.getColumn()) {
                    leikmenn[i].sendaHeim(2);
                    System.out.println(leikmenn[i] + " ped 2 sent home!");
                }
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

    public boolean isPedClickable(int pedNumer) {
        if (!bidurEftirPedVali) return false;
        return leikmenn[gerir].isPedClickable(pedNumer, teningur.getTala());
    }

    public boolean isBidurEftirPedVali() { return bidurEftirPedVali; }
    public Reitur getReiturFyrirPed(int leikmannIndex, int pedStada) {
        return leidir[leikmannIndex].get(pedStada);
    }
    public Teningur getTeningur() { return teningur; }
    public Leikmadur getLeikmadur(int id) { return leikmenn[id]; }
    public SimpleObjectProperty<LeikStada> stadaProperty() { return stada; }
    public SimpleObjectProperty<Leikmadur> naestiLeikmadurProperty() { return naestiLeikmadur; }
    public ArrayList<Reitur> getLeid(int i) { return leidir[i]; }

    public void nyrLeikur() {
        for (Leikmadur l : leikmenn) {
            if (l.isVirkur()) {
                l.ped1StadaProperty().set(0);
                l.ped2StadaProperty().set(0);
                l.ped1LeystProperty().set(false);
                l.ped2LeystProperty().set(false);
            }
        }
        gerir = finnaVirkanLeikmann(0);
        naestiLeikmadur.set(leikmenn[gerir]);
        stada.set(LeikStada.I_GANGI);
        bidurEftirPedVali = false;
    }
}