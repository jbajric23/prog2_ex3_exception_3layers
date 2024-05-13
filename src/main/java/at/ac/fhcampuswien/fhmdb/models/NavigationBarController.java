//package at.ac.fhcampuswien.fhmdb.models;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.stage.Stage;
//import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
//
//import java.io.IOException;
//
//public class NavigationBarController {
//
//    @FXML
//    private Button watchlistButton;
//
//    @FXML
//    private Button loginButton;
//
//    @FXML
//    private Button homeButton;
//
//    private FhmdbApplication app = new FhmdbApplication();
//
//    @FXML
//    public void openWatchlist() {
//        Stage stage = (Stage) watchlistButton.getScene().getWindow();
//        try {
//            app.startWatchlistScene(stage);
//            System.out.println("Watchlist opened");
//        } catch (DatabaseException | MovieApiException e ) {
//            showErrorDialog(e.getMessage());
//        }
//    }
//
//    @FXML
//    public void openLogin() {
//        // Code to open the login dialog
//    }
//
//    /*
//    @FXML
//    public void goHome() {
//        // Code to navigate to the home view
//        Stage stage = (Stage) homeButton.getScene().getWindow();
//        try {
//            app.start(stage);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//    */
//    @FXML
//    public void goHome() {
//        Stage stage = (Stage) homeButton.getScene().getWindow();
//        try {
//            app.start(stage);
//        } catch (DatabaseException | MovieApiException e) {
//            showErrorDialog(e.getMessage());
//        }
//    }
//
//    private void showErrorDialog(String message) {
//        Alert alert = new Alert(Alert.AlertType.ERROR);
//        alert.setTitle("Fehler");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//}
