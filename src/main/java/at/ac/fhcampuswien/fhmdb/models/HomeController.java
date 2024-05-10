package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.FhmdbApplication;
import at.ac.fhcampuswien.fhmdb.data.MovieAPI;
import at.ac.fhcampuswien.fhmdb.ui.MovieCell;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.controlsfx.control.CheckComboBox;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class HomeController implements Initializable {
    @FXML
    public JFXButton searchBtn;

    @FXML
    public TextField searchField;

    @FXML
    public JFXListView movieListView;

    @FXML
    public JFXListView watchListView; // ListView for the watchlist

    @FXML
    public JFXComboBox genreComboBox;



    @FXML
    CheckComboBox releaseYearBox;
    @FXML
    private Label releaseYearLabel;

    @FXML
    ComboBox ratingFromBox;



    @FXML
    public JFXButton sortBtn;

    @FXML
    public JFXButton resetFilterBtn;

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

    // List for the watchlist movies
    public List<Movie> watchListMovies;


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

    public List<Movie> filterMoviesWithAPI(String query, Genre genre, Integer releaseYear, Double rating) throws RuntimeException{
        //this method filters the movies based on the given parameters
        String urlParam = queryStringGenerator(query, genre, releaseYear, rating);
        try {
            MovieAPI apiMovies = new MovieAPI();
            List<Movie>testMovies = apiMovies.callAPI(urlParam);
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

    public HomeController() throws FileNotFoundException {
    }

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

    public static void setWatchButtonAction(Movie movie) {
        // This method sets the watch button
        Stage stage = new Stage();
        FhmdbApplication app = new FhmdbApplication();
        try {
            app.startWatchlistScene(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // initialize UI stuff

        // Check if the URL contains the watchlist-view.fxml
        if (url.getPath().contains("watchlist-view.fxml")) {
            ObservableList<Movie> observableWatchListMovies = FXCollections.observableArrayList();
            // Here will the watchlist movies be loaded and added
            List<Movie> movies = new ArrayList<>();
            watchListMovies = movies;
            observableWatchListMovies.addAll(watchListMovies);
            watchListView.setItems(observableWatchListMovies);
            watchListView.setCellFactory(movieListView -> new MovieCell());
        // Else block for the home-view.fxml
        } else {
            observableMovies.addAll(allMovies);         // add dummy data to observable list
            movieListView.setItems(observableMovies);   // set data of observable list to list view
            movieListView.setCellFactory(movieListView -> new MovieCell()); // use custom cell factory to display data
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
            System.out.println("Filter button clicked"); // Debug output

            String query = searchField.getText();
            System.out.println("Query: " + query); // Debug output

            Genre genre = (Genre) genreComboBox.getSelectionModel().getSelectedItem();
            System.out.println("Genre: " + genre); // Debug output

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
            if(sortBtn.getText().equals("Sort (asc)")) {
                sortMovies(true);
                sortBtn.setText("Sort (desc)");
            } else {
                sortMovies(false);
                sortBtn.setText("Sort (asc)");
            }
        });
    }
}