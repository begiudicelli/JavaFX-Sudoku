package org.example.javafxsudoku.model;

public class Cell {
    private int value;
    private boolean fixed;
    private int row;
    private int col;

    public Cell() {}

    public Cell(int value, boolean fixed) {
        this.value = value;
        this.fixed = fixed;
    }

    public Cell(int value, boolean fixed, int row, int col) {
        this.value = value;
        this.fixed = fixed;
        this.row = row;
        this.col = col;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }
}
