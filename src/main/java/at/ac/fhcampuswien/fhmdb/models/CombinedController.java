//package at.ac.fhcampuswien.fhmdb.models;
//
//import at.ac.fhcampuswien.fhmdb.data.WatchlistMovieEntity;
//import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
//import com.jfoenix.controls.JFXButton;
//import javafx.fxml.FXML;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Button;
//import javafx.stage.Stage;
//import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
//
//import java.io.IOException;
//import java.util.function.Consumer;
//
//public class CombinedController {
//    private static WatchlistRepository watchlistRepository;
//    private FhmdbApplication app = new FhmdbApplication();
//
//    @FXML
//    private Button watchlistButton,  homeButton; //loginButton,
//    @FXML
//    public JFXButton searchBtn, resetFilterBtn, sortBtn;
//
//    @FXML
//    public JFXButton watchButton;
//
//    public CombinedController(WatchlistRepository watchlistRepository) {
//        this.watchlistRepository = watchlistRepository;
//    }
//
//    public static void addToWatchlist(String apiId) {
//        System.out.println("addToWatchlist called with apiId: " + apiId);
//        WatchlistMovieEntity movie = new WatchlistMovieEntity();
//        movie.setApiId(apiId);
//        watchlistRepository.addMovieToWatchlist(movie);
//    }
//
//    public void removeFromWatchlist(String apiId) {
//        System.out.println("removeFromWatchlist called with apiId: " + apiId);
//        WatchlistMovieEntity movie = new WatchlistMovieEntity();
//        movie.setApiId(apiId);
//        watchlistRepository.removeMovieFromWatchlist(movie);
//    }
//
//    public void performActionOnMovie(String apiId, Consumer<WatchlistMovieEntity> action) {
//        WatchlistMovieEntity movie = new WatchlistMovieEntity();
//        movie.setApiId(apiId);
//        action.accept(movie);
//    }
//
////    public void openWatchlist() {
////        Stage stage = (Stage) watchlistButton.getScene().getWindow();
////        try {
////            app.startWatchlistScene(stage);
////            System.out.println("Watchlist opened");
////        } catch (DatabaseException | MovieApiException e ) {
////            showErrorDialog(e.getMessage());
////        }
////    }
//
////    public void openLogin() {
////        // Code to open the login dialog
////    }
//
////    public void goHome() {
////        Stage stage = (Stage) homeButton.getScene().getWindow();
////        try {
////            app.start(stage);
////        } catch (DatabaseException | MovieApiException e) {
////            showErrorDialog(e.getMessage());
////        }
////    }
////
////    private void showErrorDialog(String message) {
////        Alert alert = new Alert(Alert.AlertType.ERROR);
////        alert.setTitle("Fehler");
////        alert.setHeaderText(null);
////        alert.setContentText(message);
////        alert.showAndWait();
////    }
//}
