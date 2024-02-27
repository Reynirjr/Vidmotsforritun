package hi.vidmot;

import hi.vinnsla.Lag;
import hi.vinnsla.Lagalisti;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Stjórnandi fyrir lagalista viðmótið.
 * Þessi stjórnandi sér um að meðhöndla notendaviðmót fyrir spilun lagalista,
 * þar með talið að sýna lög í listaview, meðhöndla spilun, pásun, og skiptingu á milli laga.
 */
public class ListiController {

    @FXML
    private Label seasonLabel;// Textalabel fyrir lagalistann.
    @FXML
    private ImageView seasonImage; // Mynd fyrir lagalistann
    @FXML
    private ListView<Lag> songListView;//sýnir lögin
    @FXML
    private ImageView currentSongImage; // Mynd fyrir núverandi lag.
    @FXML
    private ProgressBar songProgressBar;
    @FXML
    private Button playPauseButton;
    @FXML
    private VBox root;


    private Boolean aHeima = false;
    private MediaPlayer player;
    private Lagalisti minnLagalisti = new Lagalisti();

    /***
     * Frumstillir viðmótið
     */
    public void initialize() {
        try {
            InputStream is = getClass().getResourceAsStream("/hi/vidmot/lagalisti.txt");
            ;
            if (is == null) {
                throw new FileNotFoundException("Cannot find resource file");
            }
            minnLagalisti.lesaLog(is);
            setjaUppLagalisti("lagalisti1");
        } catch (Exception e) {
            e.printStackTrace();
        }
        songListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                onValidLag(newSelection);
            }
        });
    }

    /**
     * Meðhöndlar "Heim" aðgerðina.
     * Þessi aðferð sér um að færa notanda aftur á forsíðu.
     */
    @FXML
    private void onHeim(ActionEvent event) throws IOException {
        hreinsaOgStodvaspilara();
        aHeima = true;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/hi/vidmot/heima-view.fxml"));
        Parent homeView = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(homeView));
        stage.show();
    }

    /**
     * Hreinsar og stöðvar playerinn
     */
    private void hreinsaOgStodvaspilara() {
        if (player != null) {
            player.stop();
            player.dispose();
            player = null;
        }
    }

    /**
     * play og pásu takkar
     */
    @FXML
    private void onPlayPause(ActionEvent event) {
        if (player != null) {
            if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                player.pause();
            } else {
                player.play();
            }
            updatePlayPauseButton();
        }
    }

    /**
     * uppfærir play og pásu iconið
     */
    private void updatePlayPauseButton() {
        if (player != null && player.getStatus() == MediaPlayer.Status.PLAYING) {
            playPauseButton.setText("▶");
        } else {
            playPauseButton.setText("||");
        }
    }

    /**
     * fer yfir á næsta lag "skip"
     */
    @FXML
    private void onNext(ActionEvent event) {
        int currentIndex = songListView.getSelectionModel().getSelectedIndex();
        int nextIndex = currentIndex + 1;
        if (nextIndex >= songListView.getItems().size()) {
            nextIndex = 0;
        }
        songListView.getSelectionModel().select(nextIndex);
        Lag nextLag = songListView.getSelectionModel().getSelectedItem();
        spilaLag(nextLag);
    }

    @FXML
    private void onValidLag(Lag valiðLag) {
        if (player != null) {
            player.stop();
            player.dispose();
        }
        spilaLag(valiðLag);
        updatePlayPauseButton();
    }

    /**
     * sér um að hreinsa og stöðva núverandi spilara,
     * búa til nýjan spilara fyrir skrána í Lag hlutnum,
     * og stjórna spilun.
     *
     * @param lag
     */
    private void spilaLag(Lag lag) {
        hreinsaOgStodvaspilara();
        String musicFile = lag.getSkraNafn();
        URL resource = getClass().getResource(musicFile);
        if (resource == null) {
            throw new IllegalArgumentException("Skrá finnst ekki: " + musicFile);
        }
        Media sound = new Media(resource.toString());
        player = new MediaPlayer(sound);
        player.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
            if (player != null && player.getTotalDuration().toSeconds() > 0) {
                double progress = newValue.toSeconds() / player.getTotalDuration().toSeconds();
                songProgressBar.setProgress(progress);
            }
        });
        player.setOnEndOfMedia(this::spilaNastaLag);
        player.play();
        updatePlayPauseButton();
        String myndSkra = lag.getMyndSkra();
        if (myndSkra != null) {
            URL myndUrl = getClass().getResource(myndSkra);
            if (myndUrl != null) {
                Image image = new Image(myndUrl.toString());
                currentSongImage.setImage(image);
            } else {
                currentSongImage.setImage(null);
            }
        }
    }

    /**
     * Spilar næsta lag á listanum.
     * Þessi aðferð sér um að velja og spila næsta lag ef núverandi lag klárast.
     */
    private void spilaNastaLag() {
        if (!aHeima) {
            int currentIndex = songListView.getSelectionModel().getSelectedIndex();
            int nextIndex = (currentIndex + 1) % songListView.getItems().size();
            songListView.getSelectionModel().select(nextIndex);
            Lag nextLag = songListView.getSelectionModel().getSelectedItem();
            spilaLag(nextLag);
            updatePlayPauseButton();
        } else {
            hreinsaOgStodvaspilara();
            aHeima = false;
        }
    }

    /**
     * Setur upp lagalistann í notendaviðmótinu.
     * Þessi aðferð sér um að birta réttan lagalista í viðmótinu eftir vali.
     *
     * @param lagalistiId Auðkenni fyrir þann lagalista sem á að birta.
     */
    public void setjaUppLagalisti(String lagalistiId) {
        songListView.getItems().clear();
        ObservableList<Lag> valinnListi = FXCollections.observableArrayList();
        String arstid = "";
        String slodin = "";
        switch (lagalistiId) {
            case "lagalisti1":
                valinnListi = minnLagalisti.getListi1();
                arstid = "Vor";
                slodin = "/hi/vidmot/media/vor.jpg";
                root.getStyleClass().add("vor-bakgrunnur");
                break;
            case "lagalisti2":
                valinnListi = minnLagalisti.getListi2();
                arstid = "Sumar";
                slodin = "/hi/vidmot/media/sumar.jpg";
                root.getStyleClass().add("sumar-bakgrunnur");
                break;
            case "lagalisti3":
                valinnListi = minnLagalisti.getListi3();
                arstid = "Haust";
                slodin = "/hi/vidmot/media/haust.jpg";
                root.getStyleClass().add("haust-bakgrunnur");
                break;
            case "lagalisti4":
                valinnListi = minnLagalisti.getListi4();
                arstid = "Vetur";
                slodin = "/hi/vidmot/media/vetur.jpg";
                root.getStyleClass().add("vetur-bakgrunnur");
                break;
            default:
                root.setId("");
                break;

        }
        seasonLabel.setText(arstid);
        Image image = new Image(getClass().getResourceAsStream(slodin));
        seasonImage.setImage(image);

        songListView.getItems().addAll(valinnListi);
    }


}
