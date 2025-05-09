package org.example.javafxsudoku.model;

import java.util.HashSet;
import java.util.Set;

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
                if (!cells[i][j].isFixed()) {
                    cells[i][j].setValue(0);
                }
            }
        }
    }

    public boolean isFull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (cells[i][j].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean isValid() {
        // Check rows
        for (int i = 0; i < 9; i++) {
            Set<Integer> seenInRow = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                int value = cells[i][j].getValue();
                if (value != 0) {
                    if (!seenInRow.add(value)) {
                        return false;
                    }
                }
            }
        }

        // Check columns
        for (int i = 0; i < 9; i++) {
            Set<Integer> seenInCol = new HashSet<>();
            for (int j = 0; j < 9; j++) {
                int value = cells[j][i].getValue();
                if (value != 0) {
                    if (!seenInCol.add(value)) {
                        return false;
                    }
                }
            }
        }

        // Check 3x3 blocks
        for (int blockRow = 0; blockRow < 3; blockRow++) {
            for (int blockCol = 0; blockCol < 3; blockCol++) {
                Set<Integer> seenInBlock = new HashSet<>();
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int row = blockRow * 3 + i;
                        int col = blockCol * 3 + j;
                        int value = cells[row][col].getValue();
                        if (value != 0) {
                            if (!seenInBlock.add(value)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0 && i != 0) {
                sb.append("------+-------+------\n");
            }
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0 && j != 0) {
                    sb.append("| ");
                }
                int value = cells[i][j].getValue();
                sb.append(value == 0 ? "_ " : value + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}