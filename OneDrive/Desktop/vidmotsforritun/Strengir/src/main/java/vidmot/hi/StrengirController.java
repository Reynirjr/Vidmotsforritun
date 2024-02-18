package vidmot.hi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class StrengirController {

    @FXML
    private TextArea textArea;// reitur þar sem notandinn skrifar texta

    @FXML
    private TextField searchText;//reitur til að skrifa texta til að leita af

    @FXML
    private Label resultLabel;//label til að sjá hvar orðið er sem notandi leitar af

    @FXML
    private Label wordCountLabel;//label sem sýnir fjölda orða í textanum

    private String geymdOrd = "";//geymir textann sem notandinn skrifar

    /**
     * Vistar textann frá textArea í geymdOrd breytunni.
     *
     * @param actionEvent Viðburðurinn sem verður til þegar takkinn er ýttur.
     */
    @FXML
    public void onVistaTexta(ActionEvent actionEvent) {
        geymdOrd = textArea.getText();
        searchText.setText("Texti vistaður.");
    }

    /**
     * Leitar að texta sem notandinn slær inn í searchText og birtir staðsetningu hans.
     *
     * @param actionEvent Viðburðurinn sem verður til þegar takkinn er ýttur.
     */
    @FXML
    public void onLeita(ActionEvent actionEvent) {
        String search = searchText.getText();
        if (geymdOrd.isEmpty()) {
            searchText.setText("Enginn texti til að leita í.");
        } else if (search.isEmpty()) {
            searchText.setText("Enginn leitartexti gefinn upp.");
        } else {
            int index = geymdOrd.indexOf(search);
            if (index != -1) {
                resultLabel.setText("" + (index + 1));
            } else {
                searchText.setText("Texti fannst ekki.");
            }
        }
    }

    /**
     * 'Telja Orð' takkann. Telur orð í textanum og birtir fjöldann í wordCountLabel.
     *
     * @param actionEvent Viðburðurinn sem verður til þegar takkinn er ýttur.
     */
    @FXML
    protected void onTeljaOrd(ActionEvent actionEvent) {
        if (!geymdOrd.isEmpty()) {
            String[] words = geymdOrd.trim().split("\\s+");
            wordCountLabel.setText("" + words.length);
        } else {
            wordCountLabel.setText("0");
        }
    }
}
