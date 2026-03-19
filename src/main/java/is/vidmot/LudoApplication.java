package is.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/******************************************************************************
 *  Nafn    : Ebba Þóra Hvannberg
 *  T-póstur: ebba@hi.is
 *  Lýsing  : Einfalt aðalforrit fyrir Halló heimur appið í JavaFX
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
        // Smíða loader fyrir notendaviðmótið sem er geymt í skránni hello-view.fxml
        // Gætið þess að .fxml skráin sé undir resources/is/vidmot
        FXMLLoader fxmlLoader = new FXMLLoader(LudoApplication.class.getResource("hello-view.fxml"));
        // Smíða senuna með notendaviðmótinu sem er núna lesið inn af resources
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        // Setja titilinn á gluggann
        stage.setTitle("Halló heimur");
        // Tengja senuna við glugggann
        stage.setScene(scene);
        // Birta gluggann
        stage.show();
    }


    public static void main(String[] args) {
        // Ræsa forritið
        launch();
    }
}