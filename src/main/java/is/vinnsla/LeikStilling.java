package is.vinnsla;

public class LeikStilling {
    private final Leikmadur[] leikmenn;
    private final int[] litaVal;

    public LeikStilling(Leikmadur[] leikmenn, int[] litaVal) {
        this.leikmenn = leikmenn;
        this.litaVal = litaVal;
    }

    public Leikmadur[] getLeikmenn() {
        return leikmenn;
    }

    public int[] getLitaVal() {
        return litaVal;
    }
}