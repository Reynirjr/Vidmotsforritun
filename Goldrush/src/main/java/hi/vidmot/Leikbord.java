package hi.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Leikbord extends AnchorPane {


    //Loader til þess að lesa leikbord-view.fxml
    public Leikbord() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/hi/vidmot/fxml/leikbord-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();

        } catch (IOException ioe) {
            throw new RuntimeException("Runtime exception");
        }

    }


}
