package hi.vinnsla;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Leikur {

    private boolean leikLokid = false;
    private boolean leikPasa = false;

    private final ObservableList<Integer> stigaTafla = FXCollections.observableArrayList(); //Stig
    private int stigin = 0; // núverandi stig í einum leik

    //aðferð til að bæta við stigum
    public void addStig(int points) {
        stigin += points;
        stigaTafla.add(stigin);
    }


    // Getter fyrir stigin
    public int getStigin() {
        return stigin;
    }

    //setter fyrir leiklokig gildið
    public void setLeiklokid(boolean leiklokid) {
        this.leikLokid = leiklokid;
    }

    //einskonar getter til að fá stöðu leiks
    public boolean erLeiklokid() {
        return leikLokid;
    }

    //setter fyrir leikpásu gildið
    public void setLeikPasa(boolean leikpasa) {
        this.leikPasa = leikpasa;
    }

    //einskonar getter til að fá stöðu leiks
    public boolean erLeikPasa() {
        return leikPasa;
    }
}
