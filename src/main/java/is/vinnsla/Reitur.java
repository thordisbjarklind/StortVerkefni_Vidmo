package is.vinnsla;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Reitur {
    private SimpleIntegerProperty row = new SimpleIntegerProperty();
    private SimpleIntegerProperty column = new SimpleIntegerProperty();
    private SimpleObjectProperty reitur = new SimpleObjectProperty();
    private ReiturTypa typa;

    public enum ReiturTypa {
        START,
        NORMAL,
        FINISH
    }

    public Reitur(int row, int column, ReiturTypa typa) {
        this.row.set(row);
        this.column.set(column);
        this.typa = typa;
    }

    public int getRow() { return row.get(); }
    public int getColumn() { return column.get(); }

}
