package Mancala.Output.Table;


public class Row {

    private String[] row;
    private int columnCount = 0;
    private int[] cellWidths;
    private boolean isRepeatingItem = false;
    private String blankCellChar = "-";


    /**
     * should the value repeat and fill the whole cell
     *
     * @return
     */
    public boolean isRepeatingItem() {
        return this.isRepeatingItem;
    }

    public Row(String[] row, String blankCellChar) {
        this(row);
        this.blankCellChar = blankCellChar;
    }

    public Row(String[] row) {
        this.row = row;
        this.columnCount = this.row.length;
        this.cellWidths = new int[this.columnCount];
        for (int i = 0; i < this.cellWidths.length; i++) {
            this.cellWidths[i] = this.row[i].length();
        }
    }

    public int[] getCellWidths() {
        return this.cellWidths;
    }

    public String getBlankCellChar() {
        return this.blankCellChar;
    }

    public String[] get() {
        return this.row;
    }
    //END Row
}
