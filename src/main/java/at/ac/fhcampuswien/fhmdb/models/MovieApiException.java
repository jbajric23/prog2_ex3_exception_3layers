package at.ac.fhcampuswien.fhmdb.models;

import javafx.scene.control.Alert;

public class MovieApiException extends Exception {
    public MovieApiException(String message) {
        super(message);
    }
}