package hi.vinnsla;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Lagalisti {
    private ObservableList<Lag> listi1 = FXCollections.observableArrayList();
    private ObservableList<Lag> listi2 = FXCollections.observableArrayList();
    private ObservableList<Lag> listi3 = FXCollections.observableArrayList();
    private ObservableList<Lag> listi4 = FXCollections.observableArrayList();

    /**
     * Les lög úr gefnum {@link InputStream} og bætir þeim við viðeigandi lista.
     * Gildi fyrir hvert lag eru aðskilin með "|".
     *
     * @param inputStream Streymi sem inniheldur lög til að lesa.
     * @throws IOException Ef lesning úr streyminu mistekst.
     */
    public void lesaLog(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String lin;
            while ((lin = reader.readLine()) != null) {
                String[] gogn = lin.split("\\|");
                if (gogn.length == 5) {
                    String skranafn = gogn[0];
                    String lagTitill = gogn[1];
                    int lengd = Integer.parseInt(gogn[2]);
                    String myndSkra = gogn[3];
                    int listiNr = Integer.parseInt(gogn[4]);

                    Lag lag = new Lag(skranafn, lagTitill, lengd, myndSkra, listiNr);
                    baetaLagiILista(lag, listiNr);
                }
            }
        }
    }

    /**
     * Bætir tilteknu lagi í viðeigandi lista eftir listanúmeri.
     *
     * @param lag     Lagið sem á að bæta við listann.
     * @param listiNr Númer listans sem lagið á að fara í.
     */
    public void baetaLagiILista(Lag lag, int listiNr) {
        switch (listiNr) {
            case 1:
                listi1.add(lag);
                break;
            case 2:
                listi2.add(lag);
                break;
            case 3:
                listi3.add(lag);
                break;
            case 4:
                listi4.add(lag);
                break;
            default:
                break;

        }
    }

    public ObservableList<Lag> getListi1() {
        return listi1;
    }

    public ObservableList<Lag> getListi2() {
        return listi2;
    }

    public ObservableList<Lag> getListi3() {
        return listi3;
    }

    public ObservableList<Lag> getListi4() {
        return listi4;
    }

}
