package org.example.javafxsudoku;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.example.javafxsudoku.model.Board;
import org.example.javafxsudoku.model.Cell;
import org.example.javafxsudoku.model.Difficulties;
import org.example.javafxsudoku.model.SudokuGenerator;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private ComboBox<Difficulties> difficultiesComboBox;
    @FXML
    private Button buttonNewBoard;
    @FXML
    private Button buttonRestart;
    @FXML
    private Button buttonHint;
    @FXML
    private GridPane sudokuGrid;

    private final TextField[][] cells = new TextField[9][9];
    private final Board board = new Board();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDifficulties();
        buildGrid();
        buttonNewBoard.setOnAction(event -> startNewGame());
    }

    public void loadDifficulties() {
        difficultiesComboBox.getItems().addAll(Difficulties.values());
    }

    private void buildGrid() {
        sudokuGrid.getChildren().clear();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cellField = new TextField();
                cellField.setPrefSize(50, 50);
                cellField.setAlignment(Pos.CENTER);
                cellField.setFont(Font.font(18));
                cellField.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");

                Cell cell = board.getCell(row, col);
                int value = cell.getValue();
                if (value != 0) {
                    cellField.setText(String.valueOf(value));
                    if (cell.isFixed()) {
                        cellField.setDisable(true);
                    }
                }
                cells[row][col] = cellField;
                sudokuGrid.add(cellField, col, row);
            }
        }
    }

    private void startNewGame() {
        sudokuGrid.getChildren().clear();
        Difficulties difficulty = difficultiesComboBox.getValue();
        if (difficulty == null) difficulty = Difficulties.EASY;
        SudokuGenerator generator = new SudokuGenerator(board);
        generator.generateBoard(difficulty);
        buildGrid();
    }
}
