package hi.vinnsla;

import javafx.beans.property.SimpleBooleanProperty;

public class Stykki {
    private String styleClass;
    private int number;
    private final SimpleBooleanProperty rettGisk = new SimpleBooleanProperty(false);

    public Stykki(int number, String styleClass) {
        this.styleClass = styleClass;
        this.number = number;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean erRettGisk() {
        return rettGisk.get();
    }

    public SimpleBooleanProperty rettGiskProperty() {
        return rettGisk;
    }

    public void setRettGisk(boolean rettGisk) {
        this.rettGisk.set(rettGisk);
    }
}
