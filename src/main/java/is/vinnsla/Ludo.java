package is.vinnsla;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Gerir ekki neitt í þessu forriti. Síðar hafa vinnsluklasar það hlutverk að framkvæma
 *  bakendavinnslu óháð notendaviðmóti *
 *
 *****************************************************************************/
public class Ludo {
    private Leikmadur[] leikmenn = new Leikmadur[2];
    private ArrayList<Reitur> leidin = new ArrayList<>();
    private Teningur teningur = new Teningur();
    private int gerir = 0;
    private SimpleObjectProperty<Leikmadur> naestiLeikmadur = new SimpleObjectProperty<>();

    public Teningur getTeningur() {
        return teningur;
    }

    public enum leikStada {
        I_GANGI, LOKID
    }

    /**
     * kastar tening, færir leikmann, setur næsta leikmann
     *
     * @return skilar true ef leik er lokið
     */
    public boolean leikaLeik() {
        // kasta tening
        teningur.kasta();
        int kast = teningur.getTala();
        // færa leikmann samkvæmt tening, leikmaður komst í mark er leik lokið og skilað true
        Leikmadur nuverandi = leikmenn[gerir];
        nuverandi.faera(kast, 10);

        // kanna stöðu á opponent
        int o = (gerir == 0) ? 1 : 0;
        Leikmadur opponent = leikmenn[o];

        // Kanna hvort þeir séu á sama reit
        if (nuverandi.getReitur() == opponent.getReitur() &&
                nuverandi.getReitur() > 0 &&
                nuverandi.getReitur() < 10) {

            // senda heim!!
            opponent.reiturProperty().set(0);
            System.out.println(opponent.getNafn() + " var sendur heim!");
        }

        //kanna sigrara
        if (nuverandi.getReitur() >= 10) {
            //enda leik
            setStada(leikStada.LOKID);
            return true;
        }

        // næsti leikmaður gerir
        gerir = (gerir == 0) ? 1 : 0;
        naestiLeikmadur.set(leikmenn[gerir]);

        return false;
    }

    public ArrayList<Reitur> getLeidin() {
        return leidin;
    }

    public Ludo(int m) {
        leikmenn[0] = new Leikmadur("blar");
        leikmenn[1] = new Leikmadur("gulur");

        int size = 6; // 6x6 borð

        for (int i = size - 1; i >= 0; i--) {
            Reitur.ReiturTypa typa = (i == size - 1) ? Reitur.ReiturTypa.START : Reitur.ReiturTypa.NORMAL;
            leidin.add(new Reitur(i, 0, typa)); // Row i, Column 0
        }

        for (int j = 1; j < size; j++) {
            Reitur.ReiturTypa typa = (j == size - 1) ? Reitur.ReiturTypa.FINISH : Reitur.ReiturTypa.NORMAL;
            leidin.add(new Reitur(0, j, typa)); // Row 0, Column j
        }

        naestiLeikmadur.set(leikmenn[gerir]);
    }

    public Leikmadur getLeikmadur(int id) {
        return leikmenn[id];
    }

    public void skipta() {
        naestiLeikmadur.set(leikmenn[gerir == 0 ? 1 : 0]);
    }

    private final SimpleObjectProperty<leikStada> stadaProperty =
            new SimpleObjectProperty<>(leikStada.I_GANGI);

    public SimpleObjectProperty<leikStada> stadaProperty() {
        return stadaProperty;
    }

    public void setStada(leikStada nyrStada) {
        this.stadaProperty.set(nyrStada);
    }

    public void nyrLeikur() {
        leikmenn[0].reiturProperty().set(0);
        leikmenn[1].reiturProperty().set(0);
        gerir = 0;
        setStada(leikStada.I_GANGI);
    }

    public SimpleObjectProperty<Leikmadur> naestiLeikmadurProperty() {
        return naestiLeikmadur;
    }
}
