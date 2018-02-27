package Mancala.Output.Table;

import java.util.ArrayList;
import java.util.List;


public class Table {

    private static int defaultColumnPadding = 4;
    private static String defaultBlankCellChar = "-";

    private List<Row> rows = new ArrayList();
    private List<Column> columns = new ArrayList();
    private int columnPadding = Table.defaultColumnPadding;
    private String blankCellChar = Table.defaultBlankCellChar;

    private Table(int columnPadding, String blankCellChar) {
        this.setColumnPadding(columnPadding);
        this.setBlankCellChar(blankCellChar);
    }

    private Table(String[] headers, int columnPadding, String blankCellChar) {
        this(columnPadding, blankCellChar);
        for (String header : headers) {
            this.columns.add(new Column(header));
        }
    }

    public Table(String[] header, String[][] rows) {
        this(header, Table.defaultColumnPadding, Table.defaultBlankCellChar);
        this.addRows(rows);
    }

    private void setColumnPadding(int columnPadding) {
        this.columnPadding = columnPadding;
    }

    private void setBlankCellChar(String blankCellChar) {
        this.blankCellChar = blankCellChar;
    }

    private void addRow(String[] row) {
        this.addRow(row, this.blankCellChar);
    }

    private void addRow(String[] row, String blankCellChar) {
        Row Row = new Row(row, blankCellChar);
        this.rows.add(Row);
        this.updateColumns(Row);
    }

    private void addRows(String[][] rows) {
        for (String[] row : rows) {
            this.addRow(row);
        }
    }

    private void updateColumns(Row row) {
        if (!row.isRepeatingItem()) {
            int[] rowCellsWidths = row.getCellWidths();
            for (int i = 0; i < rowCellsWidths.length; i++) {

                if (this.columns.size() <= i) {
                    this.columns.add(new Column());
                }

                Column currentColumn = this.columns.get(i);

                if (currentColumn.width < rowCellsWidths[i]) {
                    currentColumn.width = rowCellsWidths[i];
                }
            }
        }
    }

    private String[] toArrayString() {
        List<String> returnArray = new ArrayList();

        String columnPadding = String.format("%" + this.columnPadding + "s", "");


        if (this.columns.size() > 0) {
            boolean showHeader = false;
            String headerBar = "";
            String headerLin = "";


            for (int i = 0; i < this.columns.size(); i++) {

                if (this.columns.size() <= i) {
                    this.columns.add(new Column());
                }

                Column currentColumn = this.columns.get(i);

                headerBar += String.format("%-" + currentColumn.width + "." + currentColumn.width + "s", currentColumn.title) + ((i + 1) >= this.columns.size() ? "" : columnPadding);
                headerLin += String.format(String.format("%%0%dd", currentColumn.width), 0).replace("0", String.valueOf("-")) + ((i + 1) >= this.columns.size() ? "" : columnPadding);

                if (currentColumn.hasTitle()) {
                    showHeader = true;
                }
            }

            if (showHeader) {
                returnArray.add(headerLin);
                returnArray.add(headerBar);
                returnArray.add(headerLin);
            }
        }

        for (Row row : this.rows) {
            String rowString = "";
            String[] cells = row.get();


            for (int j = 0; j < this.columns.size(); j++) {
                Column currentColumn = this.columns.get(j);

                if (row.isRepeatingItem()) {
                    rowString += String.format(String.format("%%0%dd", currentColumn.width), 0).replace("0", cells[0]) + ((j + 1) >= this.columns.size() ? "" : columnPadding);
                } else {
                    // check to see if there is a value, if not, use blank value
                    String content = (cells.length < (j + 1) ? row.getBlankCellChar() : cells[j]);

                    // format cell to a string
                    String format = "%";
                    format += "-" + currentColumn.width + "." + currentColumn.width + "s";


                    rowString += String.format(format, content) + ((j + 1) >= this.columns.size() ? "" : columnPadding);
                }
            }
            returnArray.add(rowString);
        }
        return returnArray.toArray(new String[returnArray.size()]);
    }


    public String toString() {
        return this.toString("%s");
    }

    public String toString(String formatString) {
        String str = "";
        String[] rows = this.toArrayString();

        for (String row : rows) {
            str += String.format(formatString, row) + "\n";
        }

        return str;
    }
}
