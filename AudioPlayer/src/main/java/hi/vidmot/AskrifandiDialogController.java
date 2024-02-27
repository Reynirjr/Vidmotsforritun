package hi.vidmot;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AskrifandiDialogController {
    @FXML
    private TextField nafnTextField;

    public String getNafn() {
        return nafnTextField.getText();
    }

    @FXML
    private void handleOkAction() {
        Stage stage = (Stage) nafnTextField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleCancelAction() {
        Stage stage = (Stage) nafnTextField.getScene().getWindow();
        stage.close();
    }
}
