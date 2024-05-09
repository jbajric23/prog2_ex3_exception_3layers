package at.ac.fhcampuswien.fhmdb;

import at.ac.fhcampuswien.fhmdb.models.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomeControllerTest {

    private HomeController controller;

    @BeforeEach
    public void setup() throws FileNotFoundException {
        controller = new HomeController();
    }

    @ParameterizedTest
    @MethodSource("provideFilterMoviesScenarios")
    void testFilterMovies(String query, Genre genre, int expectedSize) {
        // Arrange
        List<Movie> movies = Arrays.asList(
                new Movie("TestMovie1", "Description1", Arrays.asList(Genre.ACTION), 2020, 5.0, 120, null, null, null, "imgUrl1"),
                new Movie("TestMovie2", "Description2", Arrays.asList(Genre.COMEDY), 2021, 6.0, 100, null, null, null, "imgUrl2")
        );
        controller.setAllMovies(movies);

        // Act
        List<Movie> filteredMovies = controller.filterMovies(query, genre);

        // Assert
        assertEquals(expectedSize, filteredMovies.size());
    }

    private static Stream<Arguments> provideFilterMoviesScenarios() {
        return Stream.of(
                Arguments.of("TestMovie1", null, 1),
                Arguments.of(null, Genre.ACTION, 1),
                Arguments.of(null, null, 2)
        );
    }
}




