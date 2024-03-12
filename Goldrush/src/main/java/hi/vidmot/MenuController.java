package hi.vidmot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioMenuItem;

import java.util.Optional;

public class MenuController {
    @FXML
    private GoldController goldController;

    private String erfidleikastig = "";

    public void setGoldController(GoldController aThis) {
        goldController = aThis;
    }

    public void initialize() {

    }

    /**
     * aðferð til að meðhöndla þegar erfiðleikastig er valið af menu
     *
     * @param actionEvent
     */
    public void onErfidleikastig(ActionEvent actionEvent) {
        RadioMenuItem selectedMenuItem = (RadioMenuItem) actionEvent.getSource();
        setErfidleikastig(selectedMenuItem.getText());
        System.out.println("Valið erfiðleikastig: " + erfidleikastig);
        
    }

    //setter fyrir erfiðleikastigin
    public void setErfidleikastig(String erfidleikastig) {
        this.erfidleikastig = erfidleikastig;
    }

    //getter fyrir erfiðleikastigin
    public String getErfidleikastig() {
        return erfidleikastig;
    }

    /**
     * kallar á aðferðina reset leikur til að hefja nýjan leik
     *
     * @param actionEvent
     */
    public void onNyrLeikur(ActionEvent actionEvent) {
        goldController.resetLeikur();
    }

    /***
     * Quit aðferð sem vekur alert til að hætta í Leiknum
     * @param actionEvent
     */
    public void onQuit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Hætta");
        alert.setHeaderText("Beiðni um að hætta");
        alert.setContentText("Ertu viss um að þú viljir hætta?");

        ButtonType buttonTypeYes = new ButtonType("Já");
        ButtonType buttonTypeNo = new ButtonType("Nei");
        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            System.exit(0);
        }

    }

    /**
     * kallar á information alert sem gefur upplýsingar um höfund forrits og ártal
     *
     * @param actionEvent
     */
    public void onUmFor(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Um forritið");
        alert.setHeaderText(null);
        alert.setContentText("Höfundur: Benjamín Reynir Jóhannsson\nÁrtal: Haust 2024\nForritið er einfalt JavaFX leikjaforrit.");

        alert.showAndWait();
    }

    /**
     * kallar á information alert sem gefur upplýsingar um hvernig á að spila leikinn
     *
     * @param actionEvent
     */
    public void onUmSpil(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hvernig skal spila");
        alert.setContentText("Hreyfðu örvatakkana til þess að færa Grafarann á Gullinn \n Markmiðið er að ná sem flestum stigum \n Gangi þér Vel!");

        alert.showAndWait();
    }
}
