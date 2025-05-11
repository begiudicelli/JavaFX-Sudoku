package org.example.javafxsudoku.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SudokuGenerator {
    private static final int SIZE = 9;
    private final Board board;

    public SudokuGenerator(Board board) {
        this.board = board;
    }
    public void generateBoard() {
        fillBoardRecursively(0, 0);
    }

    private boolean fillBoardRecursively(int row, int col) {
        if (row == SIZE) return true;
        if (col == SIZE) return fillBoardRecursively(row + 1, 0);

        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) {
            values.add(i);
        }
        Collections.shuffle(values);
        for (int value : values) {
            if (isValidMove(row, col, value)) {
                board.setValue(row, col, value);
                if (fillBoardRecursively(row, col + 1)) {
                    return true;
                }
                board.setValue(row, col, 0);
            }
        }
        return false;
    }

    private boolean isValidMove(int row, int col, int value) {
        return isValidInRow(row, value) && isValidInCol(col, value) && isValidInBlock(row, col, value);
    }

    private boolean isValidInRow(int row, int value) {
        for (int i = 0; i < SIZE; i++) {
            if (board.getValue(row, i) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInCol(int col, int value) {
        for (int i = 0; i < SIZE; i++) {
            if (board.getValue(i, col) == value) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidInBlock(int row, int col, int value) {
        int blockRowStart = (row / 3) * 3;
        int blockColStart = (col / 3) * 3;

        for (int i = blockRowStart; i < blockRowStart + 3; i++) {
            for (int j = blockColStart; j < blockColStart + 3; j++) {
                if (board.getValue(i, j) == value) {
                    return false;
                }
            }
        }
        return true;
    }

    public Board removeCellsBasedOnDifficulty(Difficulties difficulty, Board board) {
        int cellsToRemove = getCellsToRemove(difficulty);
        while (cellsToRemove > 0) {
            int row = (int) (Math.random() * SIZE);
            int col = (int) (Math.random() * SIZE);

            if (board.getValue(row, col) != 0) {
                board.setValue(row, col, 0);
                cellsToRemove--;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board.getCell(i,j).getValue() != 0){
                    board.getCell(i,j).setFixed(true);
                }
            }
        }
        return board;
    }

    private int getCellsToRemove(Difficulties difficulty) {
        return switch (difficulty) {
            case EASY -> 30;
            case MEDIUM -> 45;
            case HARD -> 55;
        };
    }

    public Board getBoard() {
        return board;
    }
}
