//package at.ac.fhcampuswien.fhmdb.models;
//
//import at.ac.fhcampuswien.fhmdb.data.WatchlistMovieEntity;
//import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXComboBox;
//import com.jfoenix.controls.JFXListView;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import org.controlsfx.control.CheckComboBox;
//
//import java.util.Comparator;
//import java.util.function.Consumer;
//
//import static java.util.Collections.sort;
//
//public class WatchlistController {
//    private final WatchlistRepository watchlistRepository;
//    @FXML
//    public JFXButton searchBtn, resetFilterBtn, sortBtn;
//
//    @FXML
//    public TextField searchField;
//
//    @FXML
//    public JFXListView movieListView, watchListView;
//
//
//    @FXML
//    public JFXComboBox genreComboBox;
//
//    @FXML
//    CheckComboBox releaseYearBox;
//    @FXML
//    private Label releaseYearLabel;
//
//    @FXML
//    ComboBox ratingFromBox;
//
//    public void initialize() {
//        sortBtn.setOnAction(e -> sort());
//    }
//
//    private void sort() {
//        ObservableList<Movie> movieList = watchListView.getItems();
//        FXCollections.sort(movieList, Comparator.comparing(Movie::getTitle)); // sort by title
//        watchListView.setItems(movieList);
//    }
//
//    public WatchlistController(WatchlistRepository watchlistRepository) {
//
//        this.watchlistRepository = watchlistRepository;
//    }
//
//    public void addToWatchlist(String apiId) {
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
//}
