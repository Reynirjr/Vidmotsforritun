package hi.vidmot;

import hi.vinnsla.Klukka;
import hi.vinnsla.Leikur;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

public class GoldController {

    private ObservableList<Gull> gullListi = FXCollections.observableArrayList();

    @FXML
    private Leikbord leikbord;

    @FXML
    private Label stigLabel;


    @FXML
    private MenuController menuStyringController;

    @FXML
    private Label timiLabel;


    @FXML
    private Button pauseButton;

    private Grafari grafari;
    private int leiktími;
    private Leikur leikur = new Leikur();
    private Klukka klukka;
    private Timeline timeline;

    private ConcurrentHashMap<KeyCode, Runnable> hreyfingar = new ConcurrentHashMap<>();// fann þetta á netinu á víst að vera meira smooth hashmap.
    private final HashSet<KeyCode> aktivirTakkar = new HashSet<>();

    /**
     * tekur inn erfiðleikastig og setur á þau gildi
     *
     * @param erfidleikastig
     */
    public void updateErfidleikastig(String erfidleikastig) {
        switch (erfidleikastig) {
            case "Auðvelt":
                leiktími = 30;
                break;
            case "Miðlungs":
                leiktími = 20;
                break;
            case "Erfitt":
                leiktími = 10;
                break;
            default:
                leiktími = 20; // Sjálfgefið gildi ef ekkert skildi passa
        }
        System.out.println("Leiktími er núna: " + leiktími + " sekúndur");
        raesaKlukku();

    }

    //aðferð til uppfæra stigin
    private void updateScore() {
        leikur.addStig(1);
        stigLabel.setText("Stig: " + leikur.getStigin());
    }

    /**
     * initalize aðferð sem frumstillir borðinu og leiknum
     */
    @FXML
    private void initialize() {
        grafari = new Grafari();
        skilgreinaHreyfingar();
        menuStyringController.setGoldController(this);
        leikbord.setFocusTraversable(true);
        leikbord.requestFocus();
        pauseButton.setFocusTraversable(false);//til að notandi fari ekki óvart á pásutakkan með örvatökkunum

        updateErfidleikastig("Miðlungs");
        klukka = new Klukka(leiktími);
        timiLabel.textProperty().bind(Bindings.concat("Tími eftir: ").concat(klukka.timiProperty().asString()));

        leikbord.setOnKeyPressed(keyEvent -> {
            aktivirTakkar.add(keyEvent.getCode());
            hreyfingar.getOrDefault(keyEvent.getCode(), () -> {
            }).run();
        });
        leikbord.setOnKeyReleased(event -> aktivirTakkar.remove(event.getCode()));

        Platform.runLater(() -> {
            leikbord.getChildren().add(grafari);
            hefjaLeik();
            // Staðsetja grafarann í miðju leikborðsins
            upphafsStillaGrafara();
            Thread hreyfingarThread = new Thread(() -> {
                while (true) {
                    aktivirTakkar.forEach(code -> Platform.runLater(hreyfingar.getOrDefault(code, () -> {
                    })));
                    try {
                        Thread.sleep(100); // Hraði hreyfingar
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            hreyfingarThread.setDaemon(true);
            hreyfingarThread.start();
        });

    }

    /**
     * til að upphafsstilla grafarann í miðjunna
     */
    private void upphafsStillaGrafara() {
        double centerX = (leikbord.getWidth() - grafari.getWidth()) / 2;
        double centerY = (leikbord.getHeight() - grafari.getHeight()) / 2;
        grafari.setX(centerX);
        grafari.setY(centerY);
    }

    //ræsa klukku aðferð notar bindings til að halda utan um tíman
    public void raesaKlukku() {
        if (timeline != null) {
            timeline.stop();
        }
        klukka = new Klukka(leiktími);
        timiLabel.textProperty().unbind();
        timiLabel.textProperty().bind(Bindings.concat("Tími eftir: ").concat(klukka.timiProperty().asString()));

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), e -> {
            klukka.tic();
            if (klukka.timiProperty().get() <= 0) { // skoðum hvort tími sé búinn.
                gameOver();
            }
        });
        timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    /**
     * aðferð sem gerir grafarann kleyft að hreyfa sig
     */
    private void skilgreinaHreyfingar() {
        hreyfingar.put(KeyCode.UP, () -> grafari.moveUp(leikbord.getHeight()));
        hreyfingar.put(KeyCode.DOWN, () -> grafari.moveDown(leikbord.getHeight()));
        hreyfingar.put(KeyCode.LEFT, () -> grafari.moveLeft(leikbord.getWidth()));
        hreyfingar.put(KeyCode.RIGHT, () -> grafari.moveRight(leikbord.getWidth()));
    }

    //pásu takki og play takki.
    @FXML
    private void onPasa() {
        if (pauseButton.getText().equals("Pása Leik")) {
            leikur.setLeikPasa(true);
            timeline.pause();
            pauseButton.setText("Halda áfram");
            hreyfingar.clear();
        } else if (pauseButton.getText().equals("Halda áfram")) {
            leikur.setLeikPasa(false);
            timeline.play();
            pauseButton.setText("Pása Leik");
            meiraGull();
            skilgreinaHreyfingar();

        }


    }

    //aðferð til að kalla fram gull
    public void hefjaLeik() {
        KeyFrame kf = new KeyFrame(Duration.seconds(2), e -> meiraGull());
        Timeline gullTimeline = new Timeline(kf);
        gullTimeline.setCycleCount(Timeline.INDEFINITE);
        gullTimeline.play();
        Timeline collisionTimeline = new Timeline(new KeyFrame(Duration.millis(100), e -> erGrefurGull()));
        collisionTimeline.setCycleCount(Timeline.INDEFINITE);
        collisionTimeline.play();
    }

    //aðferð til að stöðva leik
    private void gameOver() {
        leikur.setLeiklokid(true);
        aktivirTakkar.clear();
        hreyfingar.clear();

        leikbord.getChildren().removeIf(node -> node instanceof Gull);
        gullListi.clear();

    }

    //aðferð sem býr til gull
    public void meiraGull() {
        if (!leikur.erLeiklokid() && !leikur.erLeikPasa()) {
            double x = Math.random() * (leikbord.getWidth() - 20) + 10;
            double y = Math.random() * (leikbord.getHeight() - 20) + 10;
            Gull g = new Gull(10);
            g.setCenterX(x);
            g.setCenterY(y);

            leikbord.getChildren().add(g);
            gullListi.add(g);
        }
    }

    //aðferð sem skoðar hvort grafarinn snertir gull ef svo er þá hverfur gullið og stigin hækka
    public void erGrefurGull() {
        ArrayList<Gull> toRemove = new ArrayList<>();
        for (Gull gull : gullListi) {
            if (grafari.getBoundsInParent().intersects(gull.getBoundsInParent())) {
                toRemove.add(gull);
                updateScore();
            }
        }

        gullListi.removeAll(toRemove);
        leikbord.getChildren().removeAll(toRemove);
    }

    //aðferð til að hefja nýjan leik
    public void resetLeikur() {
        leikur.setLeiklokid(false);
        leikur = new Leikur();
        stigLabel.setText("Stig: 0");
        updateErfidleikastig(menuStyringController.getErfidleikastig());
        leikbord.getChildren().removeIf(node -> node instanceof Gull);
        gullListi.clear();
        upphafsStillaGrafara();
        hefjaLeik();
        skilgreinaHreyfingar();
    }
}


