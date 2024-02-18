package hi.vinnsla;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.*;

public class Kubbur {
    private Stykki[][] bord = new Stykki[3][3];
    public static final int RADIR = 3;
    public static final int DALKAR = 3;
    private Set<Integer> rettGiskadTolur = new HashSet<>();

    private IntegerProperty stig = new SimpleIntegerProperty(this, "stig", 10);
    private SimpleIntegerProperty rettGiskadReiti = new SimpleIntegerProperty(0);

    public Kubbur() {
        stig.set(10);
        frumstillaKubb();
    }

    private void frumstillaKubb() {
        List<Stykki> stykkjaListi = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            stykkjaListi.add(new Stykki(i, "stykki" + i));
        }
        Collections.shuffle(stykkjaListi);

        int index = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                bord[i][j] = stykkjaListi.get(index++);
            }
        }
    }

    public boolean athugaLeikBúinn() {
        for (int i = 0; i < bord.length; i++) {
            for (int j = 0; j < bord[i].length; j++) {
                Stykki stykki = bord[i][j];
                if (!stykki.erRettGisk()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Stykki getBordStykki(int i, int j) {
        return bord[i][j];
    }

    public IntegerProperty stigProperty() {
        return stig;
    }

    public final int getStig() {
        return stig.get();
    }

    public final void setStig(int value) {
        stig.set(value);
    }

    public SimpleIntegerProperty getRettGiskadReitiProperty() {
        return rettGiskadReiti;
    }

    public boolean erTalaGiskud(int tala) {
        return rettGiskadTolur.contains(tala);
    }

    public void merkjaToluSemGiskada(int tala) {
        rettGiskadTolur.add(tala);
    }
}
