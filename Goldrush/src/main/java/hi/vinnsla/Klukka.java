package hi.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;

public class Klukka {
    private SimpleIntegerProperty timi;

    //
    public Klukka(int upphafstiminn) {
        this.timi = new SimpleIntegerProperty(upphafstiminn);
    }

    public void tic() {
        if (timi.get() > 0) {
            timi.set(timi.get() - 1);
        }
    }


    public SimpleIntegerProperty timiProperty() {
        return timi;
    }


}
