package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.data.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import at.ac.fhcampuswien.fhmdb.models.ClickEventHandler;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static at.ac.fhcampuswien.fhmdb.data.MovieEntity.genresToString;

//class done with quite a bit of ChatGPT help because of performance issues
public class MovieCell extends ListCell<Movie> implements ClickEventHandler {

    private final ImageView movieImage = new ImageView();
    private final HBox layout = new HBox();
    private final VBox textLayout = new VBox();
    //private final Button watchButton = new Button("Watchlist"); // New Watchlist button
    private static final Map<String, Image> imageCache = new HashMap<>();
    private final Button watchButton = new Button();
    private WatchlistRepository watchlistRepository;
//    private final Image plusIcon = new Image("/at/ac/fhcampuswien/fhmdb/icons/plus-icon.png");
//    private final Image minusIcon = new Image("/at/ac/fhcampuswien/fhmdb/icons/minus-icon.png");

    public MovieCell() {
        movieImage.setFitWidth(100);
        movieImage.setPreserveRatio(true);
        layout.getChildren().addAll(movieImage, textLayout, watchButton); // Add watchButton to layout
        textLayout.setFillWidth(true);


        watchButton.setOnAction(event -> {
            Movie movie = getItem();
            if (movie != null) {
                if (watchButton.getText().equals("Add")) {
                    onClick();
                    //watchButton.setGraphic(new ImageView(minusIcon));
                    watchButton.getStyleClass().remove("button-plus");
                    watchButton.getStyleClass().add("button-minus");
                } else {
                    // Remove the movie from the watchlist
                    // watchlist.remove(movie);
                    //watchButton.setGraphic(new ImageView(plusIcon));
                    watchButton.getStyleClass().remove("button-minus");
                    watchButton.getStyleClass().add("button-plus");
                }
            }
        });
    }

    public MovieCell(WatchlistRepository watchlistRepository) {
        this.watchlistRepository = watchlistRepository;
    }


    public void onClick() {
        //This method is called when the cell is clicked
        Movie movie = getItem();
        if (movie != null) {
            WatchlistMovieEntity watchlistMovieEntity = new WatchlistMovieEntity();
            watchlistMovieEntity.setApiId(movie.getApiId());
            watchlistMovieEntity.setId(1L);
            watchlistRepository.addMovieToWatchlist(watchlistMovieEntity);
        }
    }

    @Override
    protected void updateItem(Movie movie, boolean empty) {
        //This is a method that is called whenever the cell needs to be updated
        super.updateItem(movie, empty);

        if (empty || movie == null) {
            setText(null);
            setGraphic(null);
        } else {
            updateTextLayout(movie);
            loadImage(movie.getImgUrl());
            if (true /* check if movie is in watchlist */) {
                watchButton.setText("Remove");
                watchButton.getStyleClass().remove("button-plus");
                watchButton.getStyleClass().add("button-minus");
            } else {
                watchButton.setText("Add");
                watchButton.getStyleClass().remove("button-minus");
                watchButton.getStyleClass().add("button-plus");
            }
            layout.prefWidthProperty().bind(getListView().widthProperty());
            setGraphic(layout);
        }
    }
    private void updateTextLayout(Movie movie) {
        //This method updates the text layout with the movie information
        textLayout.getChildren().clear();

        Label titleLabel = createStyledLabel(movie.getTitle(), 20, "-fx-text-fill: #FFD700;"); // Yellow for title
        Label directorLabel = createStyledLabel("Director: " + String.join(", ", movie.getDirectors()), 12, "-fx-text-fill: white;");
        Label mainCastLabel = createStyledLabel("Main Cast: " + String.join(", ", movie.getMainCast()), 12, "-fx-text-fill: white;");
        Label releaseYearLabel = createStyledLabel("Release: " + movie.getReleaseYear(), 12, "-fx-text-fill: white;");
        Label ratingLabel = createStyledLabel("Rating: " + String.format("%.2f", movie.getRating()), 12, "-fx-text-fill: white;");
        Label lengthLabel = createStyledLabel("Length: " + movie.getLengthInMinutes() + " min", 12, "-fx-text-fill: white;");
        String genresText = movie.getGenres().stream().map(Enum::name).collect(Collectors.joining(", "));
        Label genresLabel = createStyledLabel("Genres: " + genresText, 12, "-fx-text-fill: white;");
        Label descriptionLabel = createStyledLabel(movie.getDescription(), 12, "-fx-text-fill: white;");
        descriptionLabel.setWrapText(true);

        textLayout.getChildren().addAll(titleLabel, directorLabel, mainCastLabel, releaseYearLabel, ratingLabel, lengthLabel, genresLabel, descriptionLabel); //, watchButton
    }

    private Label createStyledLabel(String text, double fontSize, String style) {
        //this method creates a label with the given text, font size and style
        Label label = new Label(text);
        label.setFont(Font.font("Arial", FontWeight.NORMAL, fontSize));
        label.setStyle(style);
        return label;
    }

    private void loadImage(String imageUrl) {
        //This method loads the image from the given URL
        Image image = imageCache.get(imageUrl);
        if (image == null) {
            image = new Image(imageUrl, true); // Load in background
            imageCache.put(imageUrl, image);
        }
        movieImage.setImage(image);
    }

}



