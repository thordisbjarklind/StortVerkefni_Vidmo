package is.vidmot;

import is.vinnsla.LeikStilling;
import is.vinnsla.Leikmadur;
import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
import javafx.event.ActionEvent;
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

    @FXML
    private Label welcomeText;

    @FXML
    private Button fxKasta;

    @FXML
    private Button fxNyrLeikur;

    @FXML
    private Label fxSkilabod;

    private Ludo ludo;

    private final Map<Reitur, StackPane> vidmotLeid = new HashMap<>();

    private final String[] teningaMyndir = {"einn", "tveir", "thrir", "fjorir", "fimm", "sex"};

    @FXML
    public void initialize() {
        ludo = new Ludo(new Leikmadur[]{
                new Leikmadur(Reitur.Litur.GULUR, Leikmadur.Dyr.KISA, true),
                new Leikmadur(Reitur.Litur.RAUDUR, Leikmadur.Dyr.HUNDUR, true),
                new Leikmadur(Reitur.Litur.GRAENN, Leikmadur.Dyr.KJUKLING, true),
                new Leikmadur(Reitur.Litur.BLAR, Leikmadur.Dyr.HESTUR, true)
        });

        tengjaTening();

        System.out.println("Leikur byrjaður!!");
    }

    private void tengjaTening() {
        uppfaeraTeningamynd(ludo.getTeningur().getTala());

        ludo.getTeningur().talaProperty().addListener((obs, oldV, newV) -> {
            uppfaeraTeningamynd(newV.intValue());
        });
    }

    private void uppfaeraTeningamynd(int tala) {
        fxKasta.getStyleClass().removeAll("einn", "tveir", "thrir", "fjorir", "fimm", "sex");
        fxKasta.getStyleClass().add(teningaMyndir[tala - 1]);

        System.out.println("Teningur sýnir: " + tala);
        System.out.println(fxKasta.getStyleClass());
    }


    @FXML
    protected void onLeikaLeik() {
        System.out.println("Smellt á kasta");
        ludo.kastaTeningi();
    }

    @FXML
    protected void onNyrLeikur(ActionEvent event) {
        ludo.nyrLeikur();
        uppfaeraTeningamynd(ludo.getTeningur().getTala());
    }

    public void setStilling(LeikStilling stilling) {
        ludo = new Ludo(stilling.getLeikmenn());
        tengjaTening();
    }
}

