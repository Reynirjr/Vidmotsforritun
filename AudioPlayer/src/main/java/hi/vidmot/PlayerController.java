package hi.vidmot;


import hi.vinnsla.Askrifandi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerController {

    @FXML
    private Button askrifandiButton;

    /**
     * Sýnir dialogglugga fyrir innskráningu áskrifanda.
     * Setur nafn áskrifanda á hnapp ef innskráning er staðfest.
     */
    @FXML
    protected void onAskrifandiButtonClick() {
        Dialog<Askrifandi> dialog = new AskrifandiDialog();
        dialog.setTitle("Áskrifandi Innskráning");

        Askrifandi result = dialog.showAndWait().orElse(null);

        if (result != null) {
            askrifandiButton.setText(result.getNafn());
        }
    }

    /**
     * Meðhöndlar val á lagalista.
     * Skiptir yfir í viðmót fyrir valinn lagalista.
     *
     * @param event Viðburður sem varð til við að velja lista.
     */
    @FXML
    private void onVeljaLista(ActionEvent event) {
        Button btn = (Button) event.getSource();
        switchToLagalistiView(btn.getId(), event);
    }

    /**
     * Skiptir yfir í viðmót fyrir valinn lagalista.
     * Uppfærir sviðið til að sýna viðmót fyrir valinn lagalista.
     *
     * @param lagalistiId Auðkenni valins lagalista.
     * @param event       Viðburður sem orsakaði skiptin.
     */
    private void switchToLagalistiView(String lagalistiId, ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/hi/vidmot/listi-view.fxml"));
            Parent root = loader.load();

            ListiController listiController = loader.getController();
            listiController.setjaUppLagalisti(lagalistiId);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
