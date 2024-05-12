package at.ac.fhcampuswien.fhmdb.models;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import at.ac.fhcampuswien.fhmdb.FhmdbApplication;

import java.io.IOException;

public class NavigationBarController {

    @FXML
    private Button watchlistButton;

    @FXML
    private Button loginButton;

    @FXML
    private Button homeButton;

    private FhmdbApplication app = new FhmdbApplication();

    @FXML
    public void openWatchlist() {
        // Code to navigate to the watchlist view
        Stage stage = (Stage) watchlistButton.getScene().getWindow();
        try {
            app.startWatchlistScene(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void openLogin() {
        // Code to open the login dialog
    }

    @FXML
    public void goHome() {
        // Code to navigate to the home view
        Stage stage = (Stage) homeButton.getScene().getWindow();
        try {
            app.start(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}