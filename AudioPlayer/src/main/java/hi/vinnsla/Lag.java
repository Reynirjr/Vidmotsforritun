package hi.vinnsla;

public class Lag {
    private String skraNafn;
    private String lagTitill;
    private int lengd;
    private String myndSkra;
    private int listi;

    public Lag(String skraNafn, String lagTitill, int lengd, String myndSkra, int listi) {
        this.skraNafn = skraNafn;
        this.lagTitill = lagTitill;
        this.lengd = lengd;
        this.myndSkra = myndSkra;
        this.listi = listi;
    }

    @Override
    public String toString() {
        return this.lagTitill;
    }

    // Getterar og setterar fyrir hvert svæði
    public String getSkraNafn() {
        return skraNafn;
    }

    public void setSkraNafn(String skraNafn) {
        this.skraNafn = skraNafn;
    }

    public String getLagTitill() {
        return lagTitill;
    }

    public void setLagTitill(String lagTitill) {
        this.lagTitill = lagTitill;
    }

    public int getLengd() {
        return lengd;
    }

    public void setLengd(int lengd) {
        this.lengd = lengd;
    }

    public String getMyndSkra() {
        return myndSkra;
    }

    public void setMyndSkra(String myndSkra) {
        this.myndSkra = myndSkra;
    }

    public int getListi() {
        return listi;
    }

    public void setListi(int listi) {
        this.listi = listi;
    }
}
