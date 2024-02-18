package hi.vidmot;

import hi.vinnsla.Kubbur;
import hi.vinnsla.Stykki;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;

public class KubburController {
    private Set<Integer> rettGiskadTolur = new HashSet<>();
    private Integer valinTala = null;
    private int stig = 10;

    private boolean leikLokid = false;

    @FXML
    private TextField inputField;
    @FXML
    private GridPane fxBord;
    @FXML
    private Kubbur kubbur;
    @FXML
    private Label stigLabel;
    @FXML
    private Label leikStatusLabel;
    @FXML
    private Label leikStoduLabel;

    public void initialize() {
        kubbur = new Kubbur();
        stigLabel.textProperty().bind(kubbur.stigProperty().asString().concat(" Stig"));
        leikStatusLabel.setText("Sláðu inn tölu á bilinu 1-9");
        geraHnappaOvirka();
        inputField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                inputField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            onNyTala(null);
        });
    }

    @FXML
    private void onSetjaStykki(ActionEvent event) {
        if (!leikLokid && event.getSource() instanceof Button) {
            Button reitur = (Button) event.getSource();
            Integer rowIndex = GridPane.getRowIndex(reitur);
            Integer columnIndex = GridPane.getColumnIndex(reitur);
            Stykki stykki = kubbur.getBordStykki(rowIndex, columnIndex);
            setjaMynd(stykki, reitur);

            if (stykki.getNumber() == valinTala) {
                stykki.setRettGisk(true);
                kubbur.merkjaToluSemGiskada(valinTala);
                rettGiskadTolur.add(valinTala);
                reitur.setDisable(true);
                athugaLeikStatus();
                geraAlltOvirkt();
                leikStatusLabel.setText("Veldu næstu tölu á bilinu 1-9");
                if (kubbur.athugaLeikBúinn()) {
                    leikLokid = true;
                    leikStatusLabel.setText("Þú vannst!");
                    leikStoduLabel.setText("Leik lokið");
                } else {
                    leikStatusLabel.setText("Rétt! Veldu nýja tölu á bilinu 1-9");
                }
            } else {
                kubbur.setStig(kubbur.getStig() - 1);
                if (kubbur.getStig() == 0) {
                    leikLokid = true;
                    leikStatusLabel.setText("Þú tapaðir");
                    leikStoduLabel.setText("Leik lokið");
                    geraAlltOvirkt();
                }
            }

        }
    }

    @FXML
    public void onNyTala(javafx.scene.input.KeyEvent keyEvent) {
        try {
            int nyTala = Integer.parseInt(inputField.getText());
            if (nyTala >= 1 && nyTala <= 9) {
                if (!kubbur.erTalaGiskud(nyTala)) {
                    valinTala = nyTala;
                    endurraesaBordOgHaldaRettGisk();
                    geraAlltVirkt();
                    leikStatusLabel.setText("Veldu reit");
                } else {
                    leikStatusLabel.setText("þú hefur þegar giskað á þessa tölu! Veldu nýja");
                }
            } else {
                inputField.clear();
                leikStatusLabel.setText("Ógild tala");
            }
        } catch (NumberFormatException nfe) {
            inputField.clear();
            leikStatusLabel.setText("Sláðu inn tölu á bilinu 1-9");
        }
    }

    private void setjaMynd(Stykki stykki, Button b) {
        b.getStyleClass().clear();
        b.getStyleClass().add("button");
        switch (stykki.getNumber()) {
            case 1:
                b.getStyleClass().add("einn");
                break;
            case 2:
                b.getStyleClass().add("tveir");
                break;
            case 3:
                b.getStyleClass().add("trio");
                break;
            case 4:
                b.getStyleClass().add("fjorir");
                break;
            case 5:
                b.getStyleClass().add("fimm");
                break;
            case 6:
                b.getStyleClass().add("sex");
                break;
            case 7:
                b.getStyleClass().add("sjo");
                break;
            case 8:
                b.getStyleClass().add("atta");
                break;
            case 9:
                b.getStyleClass().add("niu");
                break;
            default:
                break;
        }
    }


    private void endurraesaBordOgHaldaRettGisk() {
        fxBord.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                Integer rowIndex = GridPane.getRowIndex(button);
                Integer columnIndex = GridPane.getColumnIndex(button);
                Stykki stykki = kubbur.getBordStykki(rowIndex, columnIndex);
                if (stykki.erRettGisk()) {
                    button.setDisable(true);
                } else {
                    button.setDisable(false);
                    button.getStyleClass().clear();
                    button.getStyleClass().add("button");
                }
            }
        });
        geraHnappaVirka();
    }

    private void geraAlltOvirkt() {
        fxBord.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setDisable(rettGiskadTolur.contains(valinTala));
            }
        });
    }

    private void geraAlltVirkt() {
        fxBord.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button button = (Button) node;
                Integer rowIndex = GridPane.getRowIndex(button);
                Integer columnIndex = GridPane.getColumnIndex(button);
                Stykki stykki = kubbur.getBordStykki(rowIndex == null ? 0 : rowIndex, columnIndex == null ? 0 : columnIndex);
                if (!stykki.erRettGisk()) {
                    button.setDisable(false);
                }
            }
        });
    }


    private void geraHnappaOvirka() {
        fxBord.getChildren().forEach(node -> {
            if (node instanceof Button) {
                node.setDisable(true);
            }
        });
    }

    private void geraHnappaVirka() {
        fxBord.getChildren().forEach(node -> {
            if (node instanceof Button) {
                node.setDisable(false);
            }
        });
    }

    private void athugaLeikStatus() {
        boolean allirReitirRettir = true;

        for (int i = 0; i < Kubbur.RADIR; i++) {
            for (int j = 0; j < Kubbur.DALKAR; j++) {
                Stykki stykki = kubbur.getBordStykki(i, j);
                if (!stykki.erRettGisk()) {
                    allirReitirRettir = false;
                    break;
                }
            }
            if (!allirReitirRettir) break;
        }

        if (allirReitirRettir) {
            leikLokid = true;
            geraAlltOvirkt();
        }
    }

    @FXML
    private void byrjaNyjanLeik() {
        kubbur = new Kubbur();
        stigLabel.textProperty().unbind();
        stigLabel.textProperty().bind(kubbur.stigProperty().asString().concat(" Stig"));
        kubbur.setStig(10);

        leikLokid = false;
        leikStatusLabel.setText("Veldu tölu frá bilinu 1-9");

        fxBord.getChildren().forEach(node -> {
            if (node instanceof Button) {
                Button hnappur = (Button) node;
                hnappur.setDisable(false);
                hnappur.setText("");
                hnappur.getStyleClass().clear();
                hnappur.getStyleClass().add("button");
            }
        });
    }
}
