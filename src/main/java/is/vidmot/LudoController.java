package is.vidmot;

import is.vinnsla.LeikStilling;
import is.vinnsla.Leikmadur;
import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Controller eða stýring fyrir notendaviðmótið
 *
 *
 *****************************************************************************/
public class LudoController {

    @FXML
    private GridPane fxBord;



    private Ludo ludo;

    private final Map<Reitur, StackPane> vidmotLeid = new HashMap<>();

    @FXML
    public void initialize() {
        ludo = new Ludo(new Leikmadur[]{
                new Leikmadur(Reitur.Litur.GULUR, Leikmadur.Dyr.KISA, true),
                new Leikmadur(Reitur.Litur.RAUDUR, Leikmadur.Dyr.HUNDUR, true),
                new Leikmadur(Reitur.Litur.GRAENN, Leikmadur.Dyr.KJUKLING, true),
                new Leikmadur(Reitur.Litur.BLAR, Leikmadur.Dyr.HESTUR, true)
        });
        System.out.println("Leikur byrjaður!!");
    }



    @FXML
    private Label welcomeText;

    @FXML
    private Button fxKasta;

    @FXML
    private Button fxNyrLeikur;

    @FXML
    private Label fxSkilabod;

    @FXML
    protected void onLeikaLeik() {
        // Á eftir að útfæra
    }

    @FXML
    protected void onNyrLeikur() {
        // Á eftir að útfæra
    }

    public void setStilling(LeikStilling stilling) {
        ludo = new Ludo(stilling.getLeikmenn());
    }


}

