package org.example.javafxsudoku.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import org.example.javafxsudoku.model.Board;
import org.example.javafxsudoku.model.Cell;
import org.example.javafxsudoku.model.Difficulties;
import org.example.javafxsudoku.model.SudokuGenerator;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class GameController implements Initializable {
    private int selectedRow = -1;
    private int selectedCol = -1;

    private int numHint = 3;
    private int numCheckCells = 3;

    @FXML
    private GridPane sudokuGrid;
    @FXML
    private ComboBox<Difficulties> difficultiesComboBox;
    @FXML
    private Button buttonNewBoard;
    @FXML
    private Button buttonCheckAnswer;
    @FXML
    private Button buttonCompleteAnswer;
    @FXML
    private Button buttonCheckCell;
    @FXML
    private Label labelHintCount;
    @FXML
    private Label labelCheckCellsCount;


    private final TextField[][] cells = new TextField[9][9];
    private Board board = new Board();
    private final Board solutionBoard = new Board();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadDifficulties();
        buildFirstGrid();
        buttonNewBoard.setOnAction(event -> startNewGame());
        buttonCheckAnswer.setOnAction(actionEvent -> checkAnswer());
        buttonCompleteAnswer.setOnAction(actionEvent -> showHint());
        buttonCheckCell.setOnAction(actionEvent -> checkSelectedCell());
    }

    private void loadDifficulties() {
        difficultiesComboBox.getItems().addAll(Difficulties.values());
        difficultiesComboBox.setValue(Difficulties.EASY);
    }

    private void startNewGame() {
        numHint = 3;
        numCheckCells = 3;
        labelHintCount.setText("Hints: " + numHint);
        labelCheckCellsCount.setText("Check cells: " + numCheckCells);

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

    private void showHint() {
        if (numHint <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hints");
            alert.setHeaderText(null);
            alert.setContentText("You have used all the hints!");
            alert.showAndWait();
            return;
        }
        List<Cell> emptyCells = new ArrayList<>();
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                Cell cell = board.getCell(row, col);
                if (!cell.isFixed() && cell.getValue() == 0) {
                    emptyCells.add(cell);
                }
            }
        }
        if (emptyCells.isEmpty()) return;

        Cell cell = emptyCells.get((int) (Math.random() * emptyCells.size()));
        int row = cell.getRow();
        int col = cell.getCol();

        int correctValue = solutionBoard.getCell(row, col).getValue();
        cell.setValue(correctValue);
        cell.setFixed(true);

        TextField textField = cells[row][col];
        textField.setText(String.valueOf(correctValue));
        textField.setStyle("-fx-text-fill: blue; -fx-border-color: black; -fx-border-width: 0.5;");
        textField.setDisable(true);

        numHint--;
        labelHintCount.setText("Hints: " + numHint);
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
        alert.setTitle(hasError ? "Error in the solution" : "Sucess");
        alert.setHeaderText(null);
        alert.setContentText(hasError
                ? "Wrong solution.Try again."
                : "Congratulations!");
        alert.showAndWait();
    }

    private void checkSelectedCell() {
        if (numCheckCells <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Check Cells");
            alert.setHeaderText(null);
            alert.setContentText("You have used all the check cells!!");
            alert.showAndWait();
            return;
        }
        if (selectedRow == -1 && selectedCol == -1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText(null);
            alert.setContentText("Please select one cell.");
            alert.showAndWait();
        } else {
            Cell cell = board.getCell(selectedRow, selectedCol);
            if (cell.getValue() == solutionBoard.getCell(selectedRow, selectedCol).getValue()) {
                cell.setFixed(true);
                TextField textField = cells[selectedRow][selectedCol];
                textField.setStyle("-fx-text-fill: green; -fx-border-color: black; -fx-border-width: 0.8;");
                textField.setDisable(true);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setHeaderText(null);
                alert.setContentText("Wrong value in the cell select!");
                alert.showAndWait();
            }
            numCheckCells--;
            labelCheckCellsCount.setText("Check cells: " + numCheckCells);
        }
    }

    private TextField buildGridConfig() {
        TextField cellField = new TextField();
        cellField.setPrefSize(50, 50);
        cellField.setAlignment(Pos.CENTER);
        cellField.setFont(Font.font(18));

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[1-9]?")) {
                return change;
            }
            return null;
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        cellField.setTextFormatter(textFormatter);
        return cellField;
    }

    private void buildFirstGrid() {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                TextField cellField = buildGridConfig();
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
                TextField cellField = buildGridConfig();
                Cell cell = board.getCell(row, col);
                int value = cell.getValue();
                if (value != 0) {
                    cellField.setText(String.valueOf(value));
                    if (cell.isFixed()) {
                        cellField.setDisable(true);
                        cellField.getStyleClass().add("fixed-cell");
                    }
                } else {
                    int finalRow = row;
                    int finalCol = col;
                    cellField.setOnMouseClicked(event -> {
                        if (selectedRow != -1 && selectedCol != -1) {
                            cells[selectedRow][selectedCol].getStyleClass().remove("cell-selected");
                        }
                        selectedRow = finalRow;
                        selectedCol = finalCol;
                        cellField.getStyleClass().add("cell-selected");
                    });
                    cellField.textProperty().addListener((obs, oldText, newText) -> {
                        if (newText.matches("[1-9]")) {
                            board.getCell(finalRow, finalCol).setValue(Integer.parseInt(newText));
                        } else {
                            board.getCell(finalRow, finalCol).setValue(0);
                        }
                    });
                }
                cells[row][col] = cellField;
                sudokuGrid.add(cellField, col, row);
            }
        }
    }

}
