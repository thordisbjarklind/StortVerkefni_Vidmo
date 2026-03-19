package is.vidmot;

import is.vinnsla.Leikmadur;
import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

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
        ludo = new Ludo(11);

        System.out.println("Leikur byrjaður!!");

        for (Reitur r : ludo.getLeidin()) {
            //býr til reit
            StackPane s = new StackPane();
            s.getStyleClass().add("reitur");

            //bætir reit í gridpane
            fxBord.add(s, r.getColumn(), r.getRow());

            //bætir tengingu í mappið
            vidmotLeid.put(r, s);
        }

        for (int i = 0; i < 2; i++) {
            int id = i;
            ludo.getLeikmadur(id).reiturProperty().addListener((obs, oldVal, newVal) -> {

                // losa við peðið úr gamla reit
                Reitur gamallReitur = ludo.getLeidin().get(oldVal.intValue());
                StackPane gamaltPane = vidmotLeid.get(gamallReitur);
                // finnum tákn eftir id
                gamaltPane.getChildren().removeIf(node -> ("player-" + id).equals(node.getId()));

                //Setja leikmanninn á næsta reit
                setjaLeikmannABord(id);
            });
        }

        //uppstilla borðið við byrjun leiks
        setjaLeikmannABord(0);
        setjaLeikmannABord(1);

        fxKasta.textProperty().bind(ludo.getTeningur().talaProperty().asString());

        // fxKasta is DISABLED if the game is LOKID
        fxKasta.disableProperty().bind(
                ludo.stadaProperty().isEqualTo(Ludo.leikStada.LOKID)
        );

        // fxNyrLeikur is DISABLED if the game is I_GANGI
        fxNyrLeikur.disableProperty().bind(
                ludo.stadaProperty().isEqualTo(Ludo.leikStada.I_GANGI)
        );

        fxSkilabod.textProperty().bind(
                Bindings.when(ludo.stadaProperty().isEqualTo(Ludo.leikStada.I_GANGI))
                        .then(Bindings.concat(ludo.naestiLeikmadurProperty().asString(), " á að gera!"))
                        .otherwise(Bindings.concat(ludo.naestiLeikmadurProperty().asString(), " vann!!!"))
        );
    }

    private void setjaLeikmannABord(int id) {
        // 1. sækir leikmann
        Leikmadur leikmadur = ludo.getLeikmadur(id);

        // 2. sækir reitinn sem leikmaðurinn er á
        Reitur r = ludo.getLeidin().get(leikmadur.getReitur());

        // 3. finnur viðeigandi stackpane gildi úr hashmap
        StackPane tile = vidmotLeid.get(r);

        // 4. býr til peðið
        Circle ped = new Circle(20);
        ped.getStyleClass().addAll("ped", leikmadur.getNafn());
        ped.setId("player-" + id);

        tile.getChildren().add(ped);
    }

    @FXML
    private Label welcomeText; // Viðmótshlutur sem geymir texta með kveðju

    @FXML
    private Button fxKasta; // Takki

    @FXML
    private Button fxNyrLeikur; // Takki

    @FXML
    private Label fxSkilabod;

    @FXML
    protected void onLeikaLeik() {
        // rúlla tening og færa
        boolean leikLokid = ludo.leikaLeik();

        // athuga hvort einhver hafi unnið
        if (leikLokid) {
            System.out.println("Winner!");
        }
    }

    @FXML
    protected void onNyrLeikur() {
        ludo.nyrLeikur();
    }


}

