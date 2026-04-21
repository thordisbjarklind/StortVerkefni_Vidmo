package is.vinnsla;

/**
 * @author: Logi Halldórsson, Háskóli Íslands, loh19@hi.is
 * @author: Þórdís Bjarklind Gunnarsdóttir, Háskóli Íslands, tbg18@hi.is
 *
 * Heldur utan um upphafsstillingu Ludo leiksins.
 * Inniheldur leikmenn og valda liti þeirra.
 * Notað til að senda stillingar frá StartController yfir í LudoController.
 */
public class LeikStilling {
    private final Leikmadur[] leikmenn;
    private final int[] litaVal;

    /**
     * Býr til nýja leikstillingu fyrir Ludo leikinn.
     *
     * @param leikmenn fylki af leikmönnum sem taka þátt
     * @param litaVal fylki sem skilgreinir valda liti leikmanna
     */
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