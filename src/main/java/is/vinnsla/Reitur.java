package is.vinnsla;

/**
 * @author: Logi Halldórsson, Háskóli Íslands, loh19@hi.is
 * @author: Þórdís Bjarklind Gunnarsdóttir, Háskóli Íslands, tbg18@hi.is
 *
 * Lýsir einum reit á Ludo leikborðinu.
 * Heldur utan um staðsetningu (row, column), tegund reits og lit (ef við á).
 */
public class Reitur {

    public enum ReiturTypa {
        NORMAL, START, HOME_STRETCH, GOAL, HOME_BASE
    }

    public enum Litur {
        GULUR, RAUDUR, BLAR, GRAENN
    }

    private final int row;
    private final int column;
    private final ReiturTypa typa;
    private final Litur litur;

    /**
     * Býr til nýjan reit með upplýsingum.
     *
     * @param row röð reits á borði
     * @param column dálkur reits á borði
     * @param typa tegund reits (NORMAL, START, HOME_STRECH, GOAL, HOME_BASE)
     * @param litur litur reits
     */
    public Reitur(int row, int column, ReiturTypa typa, Litur litur) {
        this.row = row;
        this.column = column;
        this.typa = typa;
        this.litur = litur;
    }

    public Reitur(int row, int column, ReiturTypa typa) {
        this(row, column, typa, null);
    }

    public int getRow() { return row; }
    public int getColumn() { return column; }
    public ReiturTypa getTypa() { return typa; }
    public Litur getLitur() { return litur; }

    @Override
    public String toString() {
        return "Reitur{row=" + row + ", col=" + column + ", type=" + typa + ", color=" + litur + "}";
    }
}