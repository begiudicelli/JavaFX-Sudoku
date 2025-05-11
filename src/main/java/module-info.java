module org.example.javafxsudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.javafxsudoku to javafx.fxml;

    exports org.example.javafxsudoku.controller;
    opens org.example.javafxsudoku.controller to javafx.fxml;
    exports org.example.javafxsudoku.view;
    opens org.example.javafxsudoku.view to javafx.fxml;

}