package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.data.*;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import org.controlsfx.control.CheckComboBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HomeController implements Initializable {

    @FXML
    private Button watchlistButton, loginButton, homeButton;
    @FXML
    public JFXButton searchBtn, resetFilterBtn, sortBtn;

    @FXML
    public JFXButton watchButton;

    // Initialize watchListMovies as an observable list, as we want to update the UI automatically when items are added/removed.
    public ObservableList<Movie> watchListMovies;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView, watchListView;


    @FXML
    public JFXComboBox genreComboBox;

    @FXML
    CheckComboBox releaseYearBox;
    @FXML
    private Label releaseYearLabel;

    @FXML
    ComboBox ratingFromBox;

    private final MovieRepository movieRepository;
    private final WatchlistRepository watchlistRepository;

    private FhmdbApplication app = new FhmdbApplication();

    // private final WatchlistController watchlistController;



    public HomeController() {
        this.movieRepository = new MovieRepository(new Database());
        this.watchlistRepository = new WatchlistRepository(new Database());
        // this.watchlistController = new WatchlistController(watchlistRepository);
        this.watchListMovies = FXCollections.observableArrayList();
    }

    public HomeController(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
        this.movieRepository = new MovieRepository(new Database());
        // this.watchlistController = new WatchlistController(watchlistRepository);
        this.watchListMovies = FXCollections.observableArrayList();
    }

//    public HomeController(MovieRepository movieRepository, WatchlistRepository watchlistRepository, WatchlistController watchlistController) {
//        this.movieRepository = movieRepository;
//        this.watchlistRepository = watchlistRepository;
//        this.watchlistController = new WatchlistController(watchlistRepository);
//        this.watchListMovies = FXCollections.observableArrayList();;
//        try {
//            MovieAPI apiMovies = new MovieAPI();
//            allMovies = apiMovies.callAPI(null);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }


    public List<Movie> allMovies;

    // Call initializeMovies method with a FileReader object as parameter for better testing possibilities
    {
        try {
            MovieAPI apiMovies = new MovieAPI();
            allMovies = apiMovies.callAPI(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // David
    public String getMostPopularActor(List<Movie> movies) {
        MovieAPI movieAPI = new MovieAPI();
        return movieAPI.getMostPopularActorFromAPI(movies);
    }

    public String getLongestMovieTitle(List<Movie> movies) {
        MovieAPI movieAPI = new MovieAPI();
        return movieAPI.getLongestMovieTitleFromAPI(movies);
    }

    public long countMoviesFrom(List<Movie> movies, String director) {
        MovieAPI movieAPI = new MovieAPI();
        return movies.stream()
                .filter(movie -> movieAPI.getDirectorFromAPI(movie).equals(director))
                .count();
    }

    public List<Movie> getMoviesBetweenYearsFromAPI(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }
    // David

    public List<Movie> filterMovies(String query, Genre genre) {
        return allMovies.stream()
                .filter(movie -> (query == null || movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                        movie.getDescription().toLowerCase().contains(query.toLowerCase())) &&
                        (genre == null || movie.getGenres().contains(genre)))
                .collect(Collectors.toList());
    }

    public List<Movie> filterMoviesWithAPI(String query, Genre genre, Integer releaseYear, Double rating) throws RuntimeException {
        //this method filters the movies based on the given parameters
        String urlParam = queryStringGenerator(query, genre, releaseYear, rating);
        try {
            MovieAPI apiMovies = new MovieAPI();
            List<Movie> testMovies = apiMovies.callAPI(urlParam);
            System.out.println("Received response: " + testMovies); // Debug-Ausgabe
            return testMovies.stream()
                    .filter(movie -> (query == null || movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                            movie.getDescription().toLowerCase().contains(query.toLowerCase())) &&
                            (genre == null || movie.getGenres().contains(genre)) &&
                            (releaseYear == null || movie.getReleaseYear() == releaseYear) &&
                            (rating == null || movie.getRating() >= rating))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected String queryStringGenerator(String query, Genre genre, Integer releaseYear, Double rating) {
        //this method generates the query string for the API call
        String queryStart = "?";
        String urlParam = queryStart;

        if (query != null) {
            urlParam += "title=" + query;
        }
        if (genre != null) {
            urlParam += (urlParam.length() > 1 ? "&" : "") + "genre=" + genre;
        }
        if (releaseYear != null) {
            urlParam += (urlParam.length() > 1 ? "&" : "") + "releaseYear=" + releaseYear;
        }
        if (rating != null) {
            urlParam += (urlParam.length() > 1 ? "&" : "") + "rating=" + rating;
        }
        System.out.println("Generated URL: " + urlParam); // Debug
        return urlParam;
    }

    public ObservableList<Movie> observableMovies = FXCollections.observableArrayList();   // automatically updates corresponding UI elements when underlying data changes


    public void setAllMovies(List<Movie> allMovies) {
        this.allMovies = allMovies;
    }


    // sort movies via comparison of titles
    public void sortMovies(boolean ascending) {
        Comparator<Movie> comparator = Comparator.comparing(Movie::getTitle);
        if (!ascending) {
            comparator = comparator.reversed();
        }
        FXCollections.sort(observableMovies, comparator);
    }

    protected void callDatabase(List<Movie> movies) {
        //this method creates the database and adds the movies to it
        Database database = new Database();
        database.createTables();
        MovieRepository movieRepo = new MovieRepository(database);
        movieRepo.addAllMovies(movies);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize UI stuff
        Database database = new Database();
        watchlistRepository.clearAll();
        // Check if the URL contains the watchlist-view.fxml
        if (url.getPath().contains("watchlist-view.fxml")) {
            ObservableList<Movie> observableWatchListMovies = FXCollections.observableArrayList();
            // Here will the watchlist movies be loaded and added
            List<Movie> movies = new ArrayList<>();
            watchListMovies = (ObservableList<Movie>) movies;
            observableWatchListMovies.addAll(watchListMovies);
            watchListView.setItems(observableWatchListMovies);
            movieListView.setCellFactory(movieListView -> new MovieCell(new HomeController(new WatchlistRepository(new Database())), watchlistRepository));
            watchListView.setCellFactory(watchListView -> new MovieCell(new HomeController(new WatchlistRepository(new Database())), watchlistRepository));
        } else {
            database.createTables();
            callDatabase(allMovies);
            observableMovies.addAll(allMovies);         // add dummy data to observable list
            movieListView.setItems(observableMovies);   // set data of observable list to list view
            movieListView.setCellFactory(movieListView -> new MovieCell(new HomeController(new WatchlistRepository(new Database())), watchlistRepository));
        }

        // Add genre items to the genreComboBox
        genreComboBox.setPromptText("Filter by Genre");
        genreComboBox.getItems().addAll(Genre.values());

        // Add release years to the releaseYearBox
        List<Integer> releaseYearList = allMovies.stream()
                .map(Movie::getReleaseYear)
                .sorted()
                .collect(Collectors.toList());
        releaseYearBox.getItems().addAll(releaseYearList);


        ratingFromBox.setPromptText("Filter by Rating");
        // Fill the ratingFromBox with values from 0 to 10
        for (int i = 0; i <= 10; i++) {
            ratingFromBox.getItems().add(i);
        }


        searchBtn.setOnAction(actionEvent -> {
            String query = searchField.getText();
            Genre genre = (Genre) genreComboBox.getSelectionModel().getSelectedItem();
            List<Integer> releaseYears = releaseYearBox.getCheckModel().getCheckedItems();
            Integer ratingValue = (Integer) ratingFromBox.getSelectionModel().getSelectedItem();
            Double rating = null;
            if (ratingValue != null) {
                rating = ratingValue.doubleValue();
            }
            List<Movie> filteredMovies = new ArrayList<>();
            if (releaseYears.isEmpty()) {
                filteredMovies.addAll(filterMoviesWithAPI(query, genre, null, rating));
            } else {
                for (Integer year : releaseYears) {
                    filteredMovies.addAll(filterMoviesWithAPI(query, genre, year, rating));
                }
            }
            filteredMovies = filteredMovies.stream().distinct().collect(Collectors.toList());
            observableMovies.clear();
            observableMovies.addAll(filteredMovies);

            //possibility to sort a filtered selection of the movies
            if (sortBtn.getText().equals("Sort (asc)")) {
                sortMovies(true);
            } else {
                sortMovies(false);
            }

            // Update the watchButton whenever a new movie is selected in the movieListView.
            movieListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> updateWatchButton((Movie) newValue));
        });

        resetFilterBtn.setOnAction(actionEvent -> {
            observableMovies.clear();
            observableMovies.addAll(allMovies);

            searchField.clear();
            // Handle the reset of the genreComboBox
            genreComboBox.getItems().clear();
            genreComboBox.setPromptText("Filter by Genre");
            genreComboBox.getItems().addAll(Genre.values());

            // Reset the releaseYearBox and ratingFromBox
            releaseYearBox.getCheckModel().clearChecks();
            ratingFromBox.getSelectionModel().clearSelection();
        });

        // Sort button for listing the movies in alphabetical order; either descending or ascending
        sortBtn.setOnAction(actionEvent -> {
            if (sortBtn.getText().equals("Sort (asc)")) {
                sortMovies(true);
                sortBtn.setText("Sort (desc)");
            } else {
                sortMovies(false);
                sortBtn.setText("Sort (asc)");
            }
        });
    }

    private void updateWatchButton(Movie movie) {
        if (movie != null) {
            // The getApiId() method needs to be replaced with the actual method used to get the movie's apiId.
            String movieApiId = movie.getApiId();

            if (watchlistRepository.isInWatchlist(movieApiId)) {
                watchButton.setText("Remove");
                watchButton.setOnAction(e -> removeMovie(movieApiId));
            } else {
                watchButton.setText("Add");
                watchButton.setOnAction(e -> addMovie(movie));
            }
        } else {
            watchButton.setText("Add");
            watchButton.setOnAction(null);
        }
    }

    private void addMovie(Movie movie) {
        WatchlistMovieEntity watchlistMovie = new WatchlistMovieEntity();
        watchlistMovie.setApiId(movie.getApiId());
        // add any other property setters if needed.
        watchlistRepository.addMovieToWatchlist(watchlistMovie);
        refreshWatchlist();
    }

    private void removeMovie(String apiId) {
        WatchlistMovieEntity movieToRemove = watchlistRepository.findByApiId(apiId);

        if (movieToRemove != null) {
            watchlistRepository.removeMovieFromWatchlist(movieToRemove);
            refreshWatchlist();
        }
    }

    private void refreshWatchlist() {
        List<WatchlistMovieEntity> moviesInWatchlist = watchlistRepository.getWatchlist();
        watchListMovies.clear();
        watchListMovies.addAll((Movie) moviesInWatchlist);
    }

    public void openWatchlist() {
        Stage stage = (Stage) watchlistButton.getScene().getWindow();
        try {
            app.startWatchlistScene(stage);
            System.out.println("Watchlist opened");
        } catch (DatabaseException | MovieApiException e ) {
            showErrorDialog(e.getMessage());
        }
    }
    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Fehler");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goHome() {
        Stage stage = (Stage) homeButton.getScene().getWindow();
        try {
            app.start(stage);
        } catch (DatabaseException | MovieApiException e) {
            showErrorDialog(e.getMessage());
        }
    }


    public void addToWatchlist(String apiId) {
        System.out.println("addToWatchlist called with apiId: " + apiId);
        WatchlistMovieEntity movie = new WatchlistMovieEntity();
        movie.setApiId(apiId);
        watchlistRepository.addMovieToWatchlist(movie);
    }

    public void removeFromWatchlist(String apiId) {
        System.out.println("removeFromWatchlist called with apiId: " + apiId);
        WatchlistMovieEntity movie = new WatchlistMovieEntity();
        movie.setApiId(apiId);
        watchlistRepository.removeMovieFromWatchlist(movie);
    }

    public void performActionOnMovie(String apiId, Consumer<WatchlistMovieEntity> action) {
        WatchlistMovieEntity movie = new WatchlistMovieEntity();
        movie.setApiId(apiId);
        action.accept(movie);
    }



    /*
    public void addToWatchlist(MovieEntity movie) {
        WatchlistMovieEntity watchlistMovie = new WatchlistMovieEntity();
        watchlistMovie.setApiId(movie.getApiId());
        watchlistRepository.addMovieToWatchlist(watchlistMovie);
    }
    */

    /*
    public void removeFromWatchlist(WatchlistMovieEntity watchlistMovie) {
        watchlistRepository.removeMovieFromWatchlist(watchlistMovie);
    } */
}
