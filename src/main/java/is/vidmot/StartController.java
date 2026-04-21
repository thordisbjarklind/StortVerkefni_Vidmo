package is.vidmot;

import is.vinnsla.Leikmadur;
import is.vinnsla.LeikStilling;
import is.vinnsla.Reitur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

/******************************************************************************
 *  Nafn    : Logi Halldórsson og Þórdís Bjarklind Gunnarsdóttir
 *  T-póstur: tbg18@hi.is, loh19@hi.is
 *  Lýsing  : Controller fyrir upphafsskjá Ludo leiksins.
 *  Sér um val leikmanna, liti og útlit áður en leikur hefst.
 *  Býr til LeikStilling og skiptir yfir í aðalleik (LudoController).
 *
 *****************************************************************************/
public class StartController {

    @FXML private Circle fxGulurCircle;
    @FXML private Circle fxRaudurCircle;
    @FXML private Circle fxBlarCircle;
    @FXML private Circle fxGraennCircle;

    @FXML private Button fxGulurTakki;
    @FXML private Button fxRaudurTakki;
    @FXML private Button fxBlarTakki;
    @FXML private Button fxGraennTakki;

    @FXML private ChoiceBox<String> fxGulurLitur;
    @FXML private ChoiceBox<String> fxRaudurLitur;
    @FXML private ChoiceBox<String> fxBlarLitur;
    @FXML private ChoiceBox<String> fxGraennLitur;

    @FXML private ImageView fxGulurDyr;
    @FXML private ImageView fxRaudurDyr;
    @FXML private ImageView fxBlarDyr;
    @FXML private ImageView fxGraennDyr;

    @FXML private Button fxHefjaLeik;

    private final boolean[] virkir = {true, true, true, true};

    private static final String[][] FILL_KLASAR = {
            {"fillYellow", "fillPastelYellow", "fillOrange", "fillPastelOrange"},
            {"fillRed", "fillPink", "fillPastelRed", "fillPastelPink"},
            {"fillGreen", "fillSeaGreen", "fillPastelGreen", "fillNeonGreen"},
            {"fillBlue", "fillPurple", "fillPastelBlue", "fillLilac"}
    };

    /**
     * Frumstillir notendaviðmótið.
     * Setur upp valmöguleika, sjálfgefin gildi, myndir og tengir listeners.
     * Kallað á það sjálfkrafa þegar FXML er hlaðið.
     */
    @FXML
    public void initialize() {
        fxGulurLitur.getItems().addAll("Gulur", "Pastell gulur", "Appelsínugulur", "Pastell appelsínugulur");
        fxRaudurLitur.getItems().addAll("Rauður", "Bleikur", "Pastell rauður", "Pastell bleikur");
        fxBlarLitur.getItems().addAll("Blár", "Fjólublár", "Pastell blár", "Lilac");
        fxGraennLitur.getItems().addAll("Grænn", "Sægrænn", "Pastell grænn", "Neon grænn");

        fxGulurLitur.getSelectionModel().selectFirst();
        fxRaudurLitur.getSelectionModel().selectFirst();
        fxBlarLitur.getSelectionModel().selectFirst();
        fxGraennLitur.getSelectionModel().selectFirst();

        fxGulurDyr.setImage(hladaMynd("rooster.png"));
        fxRaudurDyr.setImage(hladaMynd("cat.png"));
        fxBlarDyr.setImage(hladaMynd("dog.png"));
        fxGraennDyr.setImage(hladaMynd("horse.png"));

        // Tengja litaval við hringi
        fxGulurLitur.getSelectionModel().selectedIndexProperty().addListener(
                (obs, o, n) -> uppfaeraCircle(fxGulurCircle, 0, n.intValue()));
        fxRaudurLitur.getSelectionModel().selectedIndexProperty().addListener(
                (obs, o, n) -> uppfaeraCircle(fxRaudurCircle, 1, n.intValue()));
        fxBlarLitur.getSelectionModel().selectedIndexProperty().addListener(
                (obs, o, n) -> uppfaeraCircle(fxBlarCircle, 3, n.intValue()));
        fxGraennLitur.getSelectionModel().selectedIndexProperty().addListener(
                (obs, o, n) -> uppfaeraCircle(fxGraennCircle, 2, n.intValue()));

        uppfaeraCircle(fxGulurCircle, 0, 0);
        uppfaeraCircle(fxRaudurCircle, 1, 0);
        uppfaeraCircle(fxBlarCircle, 3, 0);
        uppfaeraCircle(fxGraennCircle, 2, 0);
    }

    @FXML protected void onGulurTakki() { togglePlayer(0, fxGulurTakki, fxGulurLitur); }
    @FXML protected void onRaudurTakki() { togglePlayer(1, fxRaudurTakki, fxRaudurLitur); }
    @FXML protected void onBlarTakki() { togglePlayer(2, fxBlarTakki, fxBlarLitur); }
    @FXML protected void onGraennTakki() { togglePlayer(3, fxGraennTakki, fxGraennLitur); }

    /**
     * Skipar leikmanni inn eða úr leik og uppfærir UI í kjölfarið.
     *
     * @param index index leikmanss
     * @param takki hnappur sem táknar leikmann
     * @param litur choicebox fyrir lit leikmanns
     */
    private void togglePlayer(int index, Button takki, ChoiceBox<String> litur) {
        virkir[index] = !virkir[index];
        takki.setText(virkir[index] ? "Í leik" : "Úr leik");
        litur.setDisable(!virkir[index]);
        uppfaeraHnappa();
    }

    /**
     * Gerir virkan leikmann ósmellananlegan ef aðeins 2 eru virkir,
     * svo ekki sé hægt að fara niður í færri en 2.
     */
    private void uppfaeraHnappa() {
        int fjoldi = 0;
        for (boolean v : virkir) if (v) fjoldi++;

        Button[] takkar = {fxGulurTakki, fxRaudurTakki, fxBlarTakki, fxGraennTakki};
        for (int i = 0; i < 4; i++) {
            takkar[i].setDisable(virkir[i] && fjoldi <= 2);
        }
    }

    /**
     * Býr til leikstillingu út frá vali notanda.
     * Skiptir yfir í aðalleik.
     * Sendir leikmenn, liti og stillingar til LudoController.
     */
    @FXML
    protected void onHefjaLeik() {
        Leikmadur[] leikmenn = {
                new Leikmadur(Reitur.Litur.GULUR, Leikmadur.Dyr.KJUKLING, virkir[0]),
                new Leikmadur(Reitur.Litur.RAUDUR, Leikmadur.Dyr.KISA, virkir[1]),
                new Leikmadur(Reitur.Litur.GRAENN, Leikmadur.Dyr.HESTUR, virkir[2]),
                new Leikmadur(Reitur.Litur.BLAR, Leikmadur.Dyr.HUNDUR, virkir[3])
        };

        int[] litaVal = {
                fxGulurLitur.getSelectionModel().getSelectedIndex(),
                fxRaudurLitur.getSelectionModel().getSelectedIndex(),
                fxGraennLitur.getSelectionModel().getSelectedIndex(),
                fxBlarLitur.getSelectionModel().getSelectedIndex()
        };

        LeikStilling stilling = new LeikStilling(leikmenn, litaVal);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
            Scene scene = new Scene(loader.load());
            LudoController controller = loader.getController();
            controller.setStilling(stilling);

            Stage stage = (Stage) fxHefjaLeik.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Fjarlægir fyrri lit og setur nýjan.
     * Uppfærir lit á hring í UI út frá vali leikmanns.
     *
     * @param circle hringur sem á að uppfæra
     * @param playerIndex index leikmanns
     * @param valIndex valinn litur fyrir leikmann
     */
    private void uppfaeraCircle(Circle circle, int playerIndex, int valIndex) {
        circle.getStyleClass().removeAll(FILL_KLASAR[playerIndex]);
        circle.getStyleClass().add(FILL_KLASAR[playerIndex][valIndex]);
    }

    /**
     * Hleður mynd úr resources möppu.
     *
     * @param nafn nafn á myndaskrá
     * @return Image hlutur sem var hlaðin
     */
    private Image hladaMynd(String nafn) {
        return new Image(getClass().getResourceAsStream("/is/vidmot/CSS/myndir/" + nafn));
    }
}