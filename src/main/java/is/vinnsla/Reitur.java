package is.vinnsla;

public class Reitur {

    public enum ReiturTypa {
        NORMAL,
        START,
        HOME_STRETCH,
        GOAL,
        HOME_BASE
    }

    public enum Litur {
        GULUR,  // top-left
        RAUDUR, // top-right
        BLAR,   // bottom-left
        GRAENN  // bottom-right
    }

    private final int row;
    private final int column;
    private final ReiturTypa typa;
    private final Litur litur;

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
    public Litur getListur() { return litur; }

    public boolean isColorSpecific() {
        return litur != null;
    }

    public boolean isAccessibleTo(Litur leiklitur) {
        if (typa == ReiturTypa.HOME_STRETCH || typa == ReiturTypa.START) {
            return this.litur == leiklitur;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Reitur{row=" + row + ", col=" + column + ", type=" + typa + ", color=" + litur + "}";
    }
}