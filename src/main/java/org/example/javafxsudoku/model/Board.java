package org.example.javafxsudoku.model;

public class Board {
    private final Cell[][] cells;

    public Board() {
        this.cells = new Cell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell(0, false);
            }
        }
    }

    public Board(Cell[][] cells) {
        this.cells = cells;
    }

    public void clear() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].setValue(0);
            }
        }
    }

    public Board deepCopy() {
        Board copy = new Board();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Cell originalCell = this.getCell(i, j);
                Cell copiedCell = new Cell(originalCell.getValue(), originalCell.isFixed());
                copy.setCell(i, j, copiedCell);
            }
        }
        return copy;
    }

    public void setCell(int row, int col, Cell cell) {
        cells[row][col] = cell;
    }

    public Cell getCell(int row, int col) {
        return this.cells[row][col];
    }

    public void setValue(int row, int col, int value) {
        this.cells[row][col].setValue(value);
    }

    public int getValue(int row, int col) {
        return this.cells[row][col].getValue();
    }

    public boolean isFixed(int row, int col) {
        return this.cells[row][col].isFixed();
    }
}