module org.example.javafxsudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens org.example.javafxsudoku to javafx.fxml;

    exports org.example.javafxsudoku.controller;
    opens org.example.javafxsudoku.controller to javafx.fxml;
    exports org.example.javafxsudoku.view;
    opens org.example.javafxsudoku.view to javafx.fxml;


}