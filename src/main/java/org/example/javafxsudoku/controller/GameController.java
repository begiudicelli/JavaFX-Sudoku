package org.example.javafxsudoku.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Alert;

import org.example.javafxsudoku.model.Board;
import org.example.javafxsudoku.model.Cell;
import org.example.javafxsudoku.model.Difficulties;
import org.example.javafxsudoku.model.SudokuGenerator;


import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class GameController implements Initializable {

    @FXML
    private ComboBox<Difficulties> difficultiesComboBox;
    @FXML
    private Button buttonNewBoard;
    @FXML
    private Button buttonCheckAnswer;
    @FXML
    private GridPane sudokuGrid;

    @FXML
    private Button buttonCompleteAnswer;

    private final TextField[][] cells = new TextField[9][9];
    private Board board = new Board();
    private final Board solutionBoard = new Board();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDifficulties();
        buildFirstGrid();
        buttonNewBoard.setOnAction(event -> startNewGame());
        buttonCheckAnswer.setOnAction(actionEvent -> checkAnswer());
        buttonCompleteAnswer.setOnAction(actionEvent -> fillWithCorrect());
    }

    public void loadDifficulties() {
        difficultiesComboBox.getItems().addAll(Difficulties.values());
    }

    private void fillWithCorrect() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                int correctValue = solutionBoard.getCell(row, col).getValue();
                cells[row][col].setText(String.valueOf(correctValue));
            }
        }
    }


    private void buildFirstGrid(){
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cellField = new TextField();
                cellField.setPrefSize(50, 50);
                cellField.setAlignment(Pos.CENTER);
                cellField.setFont(Font.font(18));
                cellField.setStyle("-fx-border-color: black; -fx-border-width: 0.5;");

                UnaryOperator<TextFormatter.Change> filter = change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-9]?")) {
                        return change;
                    }
                    return null;
                };
                TextFormatter<String> textFormatter = new TextFormatter<>(filter);
                cellField.setTextFormatter(textFormatter);

                cellField.setDisable(true);

                cells[row][col] = cellField;
                sudokuGrid.add(cellField, col, row);
            }
        }
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

                UnaryOperator<TextFormatter.Change> filter = change -> {
                    String newText = change.getControlNewText();
                    if (newText.matches("[1-9]?")) {
                        return change;
                    }
                    return null;
                };
                TextFormatter<String> textFormatter = new TextFormatter<>(filter);
                cellField.setTextFormatter(textFormatter);

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
        board.clear();
        Difficulties difficulty = difficultiesComboBox.getValue();
        if (difficulty == null) difficulty = Difficulties.EASY;
        SudokuGenerator generator = new SudokuGenerator(solutionBoard);
        generator.generateBoard();
        board = solutionBoard.deepCopy();
        board = generator.removeCellsBasedOnDifficulty(difficulty, board);
        buildGrid();
    }


    private void checkAnswer() {
        boolean hasError = false;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String userInput = cells[i][j].getText();
                if (userInput.isEmpty()) {
                    hasError = true;
                    break;
                }
                int userValue = Integer.parseInt(userInput);
                int correctValue = solutionBoard.getCell(i, j).getValue();
                if (userValue != correctValue) {
                    hasError = true;
                    break;
                }
            }
            if (hasError) break;
        }
        Alert alert = new Alert(hasError ? Alert.AlertType.ERROR : Alert.AlertType.INFORMATION);
        alert.setTitle(hasError ? "Erro na Solução" : "Sucesso");
        alert.setHeaderText(null);
        alert.setContentText(hasError
                ? "A solução inserida contém erros. Por favor, verifique e tente novamente."
                : "Parabéns! Você completou o Sudoku corretamente.");
        alert.showAndWait();
    }
}


