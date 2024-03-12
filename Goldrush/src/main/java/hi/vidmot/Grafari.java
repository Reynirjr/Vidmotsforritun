package hi.vidmot;

import javafx.fxml.FXMLLoader;
import javafx.scene.shape.Rectangle;

import java.io.IOException;

public class Grafari extends Rectangle {

    private static final String FXML_SKRA = "/hi/vidmot/fxml/grafari-view.fxml";
    private static final double Hreyfing_lengd = 10.0;

    public Grafari() {
        lesa(FXML_SKRA);


    }

    /**
     * Les inn útlit úr fxml skrá
     *
     * @param fxmlSkra nafn á fxml skrá
     */
    protected void lesa(String fxmlSkra) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlSkra));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    //Hreyfingar og festingar innan leikborðs

    public void moveUp(double boardHeight) {
        if (this.getY() > 0) {
            this.setY(this.getY() - Hreyfing_lengd);
            this.setRotate(0);
        }
    }

    public void moveDown(double boardHeight) {
        if (this.getY() + this.getHeight() < boardHeight) {
            this.setY(this.getY() + Hreyfing_lengd);
            this.setRotate(0);
        }
    }

    public void moveLeft(double boardWidth) {
        if (this.getX() > 0) {
            this.setX(this.getX() - Hreyfing_lengd);
            this.setRotate(270);
        }
    }

    public void moveRight(double boardWidth) {
        if (this.getX() + this.getWidth() < boardWidth) {
            this.setX(this.getX() + Hreyfing_lengd);
            this.setRotate(90); // Snúa
        }
    }
}
