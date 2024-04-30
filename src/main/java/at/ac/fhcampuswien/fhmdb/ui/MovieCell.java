package at.ac.fhcampuswien.fhmdb.ui;

import at.ac.fhcampuswien.fhmdb.models.Movie;
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
//class done with quite a bit of ChatGPT help because of performance issues
public class MovieCell extends ListCell<Movie> {

    private final ImageView movieImage = new ImageView();
    private final HBox layout = new HBox();
    private final VBox textLayout = new VBox();
    private static final Map<String, Image> imageCache = new HashMap<>();

    public MovieCell() {
        movieImage.setFitWidth(100);
        movieImage.setPreserveRatio(true);
        layout.getChildren().addAll(movieImage, textLayout);
        textLayout.setFillWidth(true);
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

        textLayout.getChildren().addAll(titleLabel, directorLabel, mainCastLabel, releaseYearLabel, ratingLabel, lengthLabel, genresLabel, descriptionLabel);
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



