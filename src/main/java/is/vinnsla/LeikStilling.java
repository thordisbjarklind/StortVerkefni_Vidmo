package is.vinnsla;

public class LeikStilling {
    private final Leikmadur[] leikmenn;

    public LeikStilling(Leikmadur[] leikmenn) {
        this.leikmenn = leikmenn;
    }

    public Leikmadur[] getLeikmenn() {
        return leikmenn;
    }
}