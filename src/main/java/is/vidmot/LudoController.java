package is.vidmot;

import is.vinnsla.LeikStilling;
import is.vinnsla.Leikmadur;
import is.vinnsla.Ludo;
import is.vinnsla.Reitur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class LudoController {

    @FXML private GridPane fxBord;
    @FXML private Button fxKasta;
    @FXML private Button fxNyrLeikur;
    @FXML private Label fxSkilabod;
    @FXML private ImageView fxGulurHome;
    @FXML private ImageView fxRaudurHome;
    @FXML private ImageView fxBlarHome;
    @FXML private ImageView fxGraennHome;

    private Ludo ludo;
    private int[] litaVal;
    private final StackPane[][] bordReitir = new StackPane[9][9];
    private final StackPane[] ped1 = new StackPane[4];
    private final StackPane[] ped2 = new StackPane[4];

    private final String[] teningaMyndir = {"einn", "tveir", "thrir", "fjorir", "fimm", "sex"};

    private static final String[][] LITA_KLASAR = {
            {"realYellow", "pastelYellow", "realOrange", "pastelOrange"},
            {"realRed", "pastelRed", "realPink", "pastelPink"},
            {"realGreen", "seaGreen", "pastelGreen", "neonGreen"},
            {"realBlue", "pastelBlue", "realPurple", "lilac"}
    };

    private static final String[][] FILL_KLASAR = {
            {"fillYellow", "fillPastelYellow", "fillOrange", "fillPastelOrange"},
            {"fillRed", "fillPink", "fillPastelRed", "fillPastelPink"},
            {"fillGreen", "fillSeaGreen", "fillPastelGreen", "fillNeonGreen"},
            {"fillBlue", "fillPurple", "fillPastelBlue", "fillLilac"}
    };

    private static final String[] DYR_MYNDIR = {"rooster.png", "cat.png", "horse.png", "dog.png"};
    private static final int[][] HOME_PED1 = {{0,0}, {0,6}, {6,6}, {6,0}};
    private static final int[][] HOME_PED2 = {{1,1}, {1,7}, {7,7}, {7,1}};
    private static final double HOME_OFFSET = 32.5;

    @FXML
    public void initialize() {
    }

    public void setStilling(LeikStilling stilling) {
        ludo = new Ludo(stilling.getLeikmenn());
        litaVal = stilling.getLitaVal();
        stillaHomeImages();
        tengjaTening();
        byggjaBord();
        uppfaeraSkilabod();

        ludo.stadaProperty().addListener((obs, o, n) -> {
            if (n == Ludo.LeikStada.LOKID) {
                fxSkilabod.setText(ludo.naestiLeikmadurProperty().get().toString() + " vinnur!");
                fxKasta.setDisable(true);
            }
        });
    }

    // ---- Teningur ----

    private void tengjaTening() {
        uppfaeraTeningamynd(ludo.getTeningur().getTala());
        ludo.getTeningur().talaProperty().addListener((obs, o, n) ->
                uppfaeraTeningamynd(n.intValue()));
    }

    private void uppfaeraTeningamynd(int tala) {
        fxKasta.getStyleClass().removeAll(teningaMyndir);
        fxKasta.getStyleClass().add(teningaMyndir[tala - 1]);
    }

    // ---- Home myndir ----

    private void stillaHomeImages() {
        ImageView[] homes = {fxGulurHome, fxRaudurHome, fxGraennHome, fxBlarHome};
        for (int i = 0; i < 4; i++) {
            homes[i].setMouseTransparent(true);
            homes[i].setImage(hladaMynd(LITA_KLASAR[i][litaVal[i]] + ".png"));
        }
    }

    // ---- Borð ----

    private void byggjaBord() {
        ImageView[] homes = {fxGulurHome, fxRaudurHome, fxBlarHome, fxGraennHome};
        fxBord.getChildren().removeAll(homes);

        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                StackPane reitur = new StackPane();
                reitur.getStyleClass().add("reitur");
                bordReitir[row][col] = reitur;
                GridPane.setRowIndex(reitur, row);
                GridPane.setColumnIndex(reitur, col);
                fxBord.getChildren().add(reitur);
            }
        }

        fxBord.getChildren().addAll(homes);
        litaBordid();
        setjaPed();
    }

    private void litaBordid() {
        for (int i = 0; i < 4; i++) {
            for (Reitur r : ludo.getLeid(i)) {
                if (r.getTypa() == Reitur.ReiturTypa.NORMAL) continue;

                StackPane sp = bordReitir[r.getRow()][r.getColumn()];

                if (r.getTypa() == Reitur.ReiturTypa.START ||
                        r.getTypa() == Reitur.ReiturTypa.HOME_STRETCH) {
                    sp.getStyleClass().add(LITA_KLASAR[i][litaVal[i]]);
                } else if (r.getTypa() == Reitur.ReiturTypa.GOAL) {
                    sp.getStyleClass().add("goal");
                }
            }
        }
    }

    // ---- Peð ----

    private void setjaPed() {
        for (int i = 0; i < 4; i++) {
            if (!ludo.getLeikmadur(i).isVirkur()) continue;
            ped1[i] = buaPed(i, 1);
            ped2[i] = buaPed(i, 2);
            setjaPedVidmot(ped1[i], HOME_PED1[i][0], HOME_PED1[i][1], true);
            setjaPedVidmot(ped2[i], HOME_PED2[i][0], HOME_PED2[i][1], true);
        }
    }

    private StackPane buaPed(int leikmannIndex, int pedNumer) {
        Circle circle = new Circle(30);
        circle.getStyleClass().add(FILL_KLASAR[leikmannIndex][litaVal[leikmannIndex]]);

        ImageView dyr = new ImageView(hladaMynd(DYR_MYNDIR[leikmannIndex]));
        dyr.setFitWidth(50);
        dyr.setFitHeight(50);
        dyr.setPreserveRatio(true);

        StackPane sp = new StackPane(circle, dyr);
        sp.setOnMouseClicked(e -> onPedSmellt(leikmannIndex, pedNumer));

        Leikmadur lm = ludo.getLeikmadur(leikmannIndex);
        var stadaProp = (pedNumer == 1) ? lm.ped1StadaProperty() : lm.ped2StadaProperty();
        var leystProp = (pedNumer == 1) ? lm.ped1LeystProperty() : lm.ped2LeystProperty();

        stadaProp.addListener((obs, o, n) -> flytjaPed(leikmannIndex, pedNumer, sp));
        leystProp.addListener((obs, o, n) -> flytjaPed(leikmannIndex, pedNumer, sp));

        return sp;
    }

    private void flytjaPed(int leikmannIndex, int pedNumer, StackPane ped) {
        Leikmadur lm = ludo.getLeikmadur(leikmannIndex);
        boolean leyst = (pedNumer == 1) ? lm.isPed1Leyst() : lm.isPed2Leyst();
        int stada = (pedNumer == 1) ? lm.getPed1Stada() : lm.getPed2Stada();

        if (!leyst) {
            int[] home = (pedNumer == 1) ? HOME_PED1[leikmannIndex] : HOME_PED2[leikmannIndex];
            setjaPedVidmot(ped, home[0], home[1], true);
            return;
        }

        if (stada == 34) {
            fxBord.getChildren().remove(ped);
            return;
        }

        Reitur reitur = ludo.getReiturFyrirPed(leikmannIndex, stada);
        setjaPedVidmot(ped, reitur.getRow(), reitur.getColumn(), false);
    }

    private void setjaPedVidmot(StackPane ped, int row, int col, boolean heima) {
        fxBord.getChildren().remove(ped);
        GridPane.setRowIndex(ped, row);
        GridPane.setColumnIndex(ped, col);

        if (heima) {
            ped.setTranslateX(HOME_OFFSET);
            ped.setTranslateY(HOME_OFFSET);
        } else {
            ped.setTranslateX(0);
            ped.setTranslateY(0);
        }

        fxBord.getChildren().add(ped);
    }

    // ---- Aðgerðir ----

    private void onPedSmellt(int leikmannIndex, int pedNumer) {
        if (!ludo.isBidurEftirPedVali()) return;
        if (ludo.getLeikmadur(leikmannIndex) != ludo.naestiLeikmadurProperty().get()) return;
        if (!ludo.isPedClickable(pedNumer)) return;

        String nafn = ludo.getLeikmadur(leikmannIndex).toString();
        boolean sigur = ludo.faeraPed(pedNumer);

        if (sigur) {
            fxSkilabod.setText(nafn + " vinnur!");
            fxKasta.setDisable(true);
        } else {
            uppfaeraSkilabod();
        }
    }

    @FXML
    protected void onLeikaLeik() {
        if (ludo.isBidurEftirPedVali()) return;

        ludo.kastaTeningi();
        int kast = ludo.getTeningur().getTala();

        if (ludo.isPedClickable(1) || ludo.isPedClickable(2)) {
            fxSkilabod.setText(kast + " - veldu peð");
        } else {
            ludo.skippaLeikmann();
            uppfaeraSkilabod();
        }
    }

    @FXML
    protected void onNyrLeikur(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("start-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) fxNyrLeikur.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---- Hjálparaðferðir ----

    private void uppfaeraSkilabod() {
        fxSkilabod.setText(ludo.naestiLeikmadurProperty().get().toString() + " á að gera");
    }

    private Image hladaMynd(String nafn) {
        return new Image(getClass().getResourceAsStream("/is/vidmot/CSS/myndir/" + nafn));
    }
}