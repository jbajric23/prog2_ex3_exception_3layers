package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.data.MovieEntity;
import at.ac.fhcampuswien.fhmdb.data.MovieRepository;
import at.ac.fhcampuswien.fhmdb.models.DatabaseException;
import at.ac.fhcampuswien.fhmdb.models.MovieApiException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class FhmdbApplication extends Application {
    /*
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb");
        stage.setScene(scene);
        stage.show();
    }
    */
    private MovieRepository movieRepository = new MovieRepository();

    @Override
    public void start(Stage stage) throws DatabaseException, MovieApiException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("home-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 890, 620);
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
            stage.setTitle("FHMDb");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            if (e instanceof SQLException) {
                throw new DatabaseException("Database error: " + e.getMessage());
            } else if (e instanceof IOException) {
                throw new MovieApiException("Movie API error: " + e.getMessage());
            } else {
                throw new RuntimeException("Unexpected error: " + e.getMessage());
            }
        }
    }


    /**
     * Starts the watchlist scene
     * @param stage the stage to show the scene on
     * @throws IOException if the fxml file could not be loaded
     */

    /*
    public void startWatchlistScene(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
        stage.setTitle("FHMDb - Watchlist");
        stage.setScene(scene);
        stage.show();
    }
    */
    public void startWatchlistScene(Stage stage) throws DatabaseException, MovieApiException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(FhmdbApplication.class.getResource("watchlist-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 890, 620);
            scene.getStylesheets().add(Objects.requireNonNull(FhmdbApplication.class.getResource("styles.css")).toExternalForm());
            stage.setTitle("FHMDb - Watchlist");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            if (e instanceof SQLException) {
                throw new DatabaseException("Database error: " + e.getMessage());
            } else if (e instanceof IOException) {
                throw new MovieApiException("Movie API error: " + e.getMessage());
            } else {
                throw new RuntimeException("Unexpected error: " + e.getMessage());
            }
        }
    }
    /*
    private void cacheMovies() throws DatabaseException, MovieApiException {
        try {
            List<MovieEntity> movies = fetchMoviesFromApi();
            movieRepository.saveMovies(movies);
        } catch (Exception e) {
            if (e instanceof IOException) {
                List<MovieEntity> movies = movieRepository.getMovies();
                if (movies.isEmpty()) {
                    throw new DatabaseException("No movies in cache and API is unreachable");
                }
                // Verwenden Sie die Filme aus der Datenbank
            } else {
                throw new RuntimeException("Unexpected error: " + e.getMessage());
            }
        }
    }
    */
    //private List<MovieEntity> fetchMoviesFromApi() throws IOException {}

    public static void main(String[] args) {
        launch();
    }
}