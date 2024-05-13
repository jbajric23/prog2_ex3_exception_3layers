module at.ac.fhcampuswien.fhmdb {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.jfoenix;
    requires org.controlsfx.controls;
    requires com.google.gson;
    requires okhttp3;
    requires ormlite.jdbc;
    requires java.sql;

    opens at.ac.fhcampuswien.fhmdb to javafx.fxml;
    exports at.ac.fhcampuswien.fhmdb;
    exports at.ac.fhcampuswien.fhmdb.models;
    opens at.ac.fhcampuswien.fhmdb.models to javafx.fxml;
    opens at.ac.fhcampuswien.fhmdb.data;
    exports at.ac.fhcampuswien.fhmdb.data;
}