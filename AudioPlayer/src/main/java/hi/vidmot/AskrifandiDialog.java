package hi.vidmot;

import hi.vinnsla.Askrifandi;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;

import java.io.IOException;

public class AskrifandiDialog extends Dialog<Askrifandi> {
    /**
     * Smiður sem býr til nýjan AskrifandiDialog.
     * Upphafsstillir dialogglugga með viðmóti fyrir innskráningu áskrifanda.
     */
    public AskrifandiDialog() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("askrifandi-view.fxml"));
            DialogPane dialogPane = loader.load();
            this.setTitle("Innskráning");

            ButtonType iLagiButtonType = new ButtonType("Í lagi", ButtonBar.ButtonData.OK_DONE);
            ButtonType haettaButtonType = new ButtonType("Hætta", ButtonBar.ButtonData.CANCEL_CLOSE);
            // Bætir hnöppum við dialoggluggann
            dialogPane.getButtonTypes().setAll(iLagiButtonType, haettaButtonType);
            // Stillir dialogPane sem aðalpane fyrir dialoggluggann
            this.setDialogPane(dialogPane);
            // Skilgreinir hvernig á að meðhöndla niðurstöðu úr dialogglugganum
            this.setResultConverter(dialogButton -> {
                if (dialogButton == iLagiButtonType) {
                    AskrifandiDialogController controller = loader.getController();
                    return new Askrifandi(controller.getNafn());
                }
                return null;
            });

        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new RuntimeException(ioe);
        }
    }
}
