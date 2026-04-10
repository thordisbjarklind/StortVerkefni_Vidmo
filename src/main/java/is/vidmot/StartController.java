package is.vidmot;

import is.vinnsla.Leikmadur;
import is.vinnsla.LeikStilling;
import is.vinnsla.Reitur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class StartController {

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

    // Virkir notendur
    private boolean[] virkir = {true, true, true, true};

    @FXML
    public void initialize() {
        // litavalkostir
        fxGulurLitur.getItems().addAll("Gulur", "Pastel gulur", "Appelsínugulur", "Pastel appelsínugulur");
        fxRaudurLitur.getItems().addAll("Rauður", "Bleikur", "Pastel rauður", "Pastel bleikur");
        fxBlarLitur.getItems().addAll("Blár", "Fjólublár", "Pastel Blár", "Lilac");
        fxGraennLitur.getItems().addAll("Graenn", "Sjó grænn", "Pastel grænn", "Neon grænn");

        // default litir
        fxGulurLitur.getSelectionModel().selectFirst();
        fxRaudurLitur.getSelectionModel().selectFirst();
        fxBlarLitur.getSelectionModel().selectFirst();
        fxGraennLitur.getSelectionModel().selectFirst();

        // Tengja myndir
        fxGulurDyr.setImage(new Image(getClass().getResourceAsStream("/is/vidmot/CSS/myndir/rooster.png")));
        fxRaudurDyr.setImage(new Image(getClass().getResourceAsStream("/is/vidmot/CSS/myndir/cat.png")));
        fxBlarDyr.setImage(new Image(getClass().getResourceAsStream("/is/vidmot/CSS/myndir/dog.png")));
        fxGraennDyr.setImage(new Image(getClass().getResourceAsStream("/is/vidmot/CSS/myndir/horse.png")));
    }

    @FXML
    protected void onGulurTakki() {
        virkir[0] = !virkir[0];
        uppfaeraPlayer(0, fxGulurTakki, fxGulurLitur);
    }

    @FXML
    protected void onRaudurTakki() {
        virkir[1] = !virkir[1];
        uppfaeraPlayer(1, fxRaudurTakki, fxRaudurLitur);
    }

    @FXML
    protected void onBlarTakki() {
        virkir[2] = !virkir[2];
        uppfaeraPlayer(2, fxBlarTakki, fxBlarLitur);
    }

    @FXML
    protected void onGraennTakki() {
        virkir[3] = !virkir[3];
        uppfaeraPlayer(3, fxGraennTakki, fxGraennLitur);
    }

    private void uppfaeraPlayer(int index, Button takki, ChoiceBox<String> litur) {
        if (virkir[index]) {
            takki.setText("Í leik");
            litur.setDisable(false);
        } else {
            takki.setText("Úr leik");
            litur.setDisable(true);
        }
        uppfaeraHnappa();
    }

    private void uppfaeraHnappa() {
        int fjoldiVirkra = 0;
        for (boolean v : virkir) if (v) fjoldiVirkra++;

        // Svo það sé ekki hægt að hafa færri en 2 í leik
        fxGulurTakki.setDisable(!virkir[0] || fjoldiVirkra <= 2 ?
                virkir[0] ? fjoldiVirkra <= 2 : false : false);
        fxRaudurTakki.setDisable(!virkir[1] || fjoldiVirkra <= 2 ?
                virkir[1] ? fjoldiVirkra <= 2 : false : false);
        fxBlarTakki.setDisable(!virkir[2] || fjoldiVirkra <= 2 ?
                virkir[2] ? fjoldiVirkra <= 2 : false : false);
        fxGraennTakki.setDisable(!virkir[3] || fjoldiVirkra <= 2 ?
                virkir[3] ? fjoldiVirkra <= 2 : false : false);
    }

    @FXML
    protected void onHefjaLeik() {
        Leikmadur[] leikmenn = new Leikmadur[4];

        leikmenn[0] = new Leikmadur(Reitur.Litur.GULUR, Leikmadur.Dyr.KISA, virkir[0]);
        leikmenn[1] = new Leikmadur(Reitur.Litur.RAUDUR, Leikmadur.Dyr.HUNDUR, virkir[1]);
        leikmenn[2] = new Leikmadur(Reitur.Litur.BLAR, Leikmadur.Dyr.KJUKLING, virkir[2]);
        leikmenn[3] = new Leikmadur(Reitur.Litur.GRAENN, Leikmadur.Dyr.HESTUR, virkir[3]);

        LeikStilling stilling = new LeikStilling(leikmenn);

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
}