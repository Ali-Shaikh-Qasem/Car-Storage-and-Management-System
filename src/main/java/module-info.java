module com.example.database_project {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.desktop;

    opens com.example.database_project to javafx.fxml;
    exports com.example.database_project;
}