package Mancala.Output.Table;

public class Column {

    private boolean hasTitle = false;

    public String title = "";
    public int width = 0;

    public Column() {
    }

    public Column(String title) {
        this.setTitle(title);
    }

    boolean hasTitle() {
        return hasTitle;
    }

    private Column setTitle(String newTitle) {
        this.title = newTitle;
        if (this.width < this.title.length()) {
            this.width = this.title.length();
        }
        this.hasTitle = true;
        return this;
    }

}
