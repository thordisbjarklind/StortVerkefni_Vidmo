package is.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/******************************************************************************
 *  Nafn    : Logi Halldórsson og Þórdís Bjarklind Gunnarsdóttir
 *  T-póstur: tbg18@hi.is, loh19@hi.is
 *  Lýsing  : Aðalforritið sem ræsir Lúdó spilið.
 *
 *
 *****************************************************************************/
public class LudoApplication extends javafx.application.Application {
    /**
     * Ræsir appið
     *
     * @param stage glugginn
     * @throws Exception undnantekning sem verður ef villla
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Smíða loader fyrir notendaviðmótið sem er geymt í skránni Ludo-view.fxml
        // Gætið þess að .fxml skráin sé undir resources/is/vidmot
        FXMLLoader fxmlLoader = new FXMLLoader(LudoApplication.class.getResource("start-view.fxml"));
        // Smíða senuna með notendaviðmótinu sem er núna lesið inn af resources
        Scene scene = new Scene(fxmlLoader.load());
        // Setja titilinn á gluggann
        stage.setTitle("LUDO");
        // Tengja senuna við glugggann
        stage.setScene(scene);
        // Birta gluggann
        stage.show();
    }

    /**
     * Aðalforritið sem ræsir appið
     *
     * @param args ónotað
     */
    public static void main(String[] args) {
        launch();
    }
}