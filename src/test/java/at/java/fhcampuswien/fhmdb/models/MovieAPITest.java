package at.java.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.models.HomeController;
import at.ac.fhcampuswien.fhmdb.models.Genre;
import at.ac.fhcampuswien.fhmdb.data.MovieAPI;
import at.ac.fhcampuswien.fhmdb.models.Movie;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/*
class MovieAPITest {
    @Test
    public void testGetMostPopularActor() throws FileNotFoundException {
        HomeController controller = new HomeController();
        List<Movie> movies = Arrays.asList(
                new Movie("Once Upon a Time in Hollywood", "A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywoods's Golden Age in 1969 Los Angeles", Arrays.asList(Genre.COMEDY, Genre.DRAMA), 2019, 7.7, 161, Arrays.asList("Quentin Tarantino"), Arrays.asList("Quentin Tarantino"), Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"), "https://www.imdb.com/title/tt7131622/"),
                new Movie("Seven", "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his modus operandi.", Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY, Genre.THRILLER), 1995, 8.6, 127, Arrays.asList("David Fincher"), Arrays.asList("Andrew Kevin Walker"), Arrays.asList("Morgan Freeman", "Brad Pitt", "Kevin Spacey"), "https://www.imdb.com/title/tt0114369/"),
                new Movie("City of God", "Two boys growing up in a neighborhood of Rio de Janerio take different paths: one becomes a photographer, the other a drug dealer", Arrays.asList(Genre.CRIME, Genre.DRAMA), 2002, 8.6, 130, Arrays.asList("Fernando Meirelles", "Kátia Lund"), Arrays.asList("Paulo Lins", "Bráulio Mantovani"), Arrays.asList("Alexandre Rodrigues", "Leandro Firmino", "Matheus Nachtergaele"), "https://www.imdb.com/title/tt0317248/")
        );
        String result = controller.getMostPopularActor(movies);
        assertEquals("Brad Pitt", result);
    }

    @Test
    public void testGetLongestMovieTitle() throws FileNotFoundException {
        HomeController controller = new HomeController();
        List<Movie> movies = Arrays.asList(
                new Movie("Once Upon a Time in Hollywood", "A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywoods's Golden Age in 1969 Los Angeles", Arrays.asList(Genre.COMEDY, Genre.DRAMA), 2019, 7.7, 161, Arrays.asList("Quentin Tarantino"), Arrays.asList("Quentin Tarantino"), Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"), "https://www.imdb.com/title/tt7131622/"),
                new Movie("Seven", "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his modus operandi.", Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY, Genre.THRILLER), 1995, 8.6, 127, Arrays.asList("David Fincher"), Arrays.asList("Andrew Kevin Walker"), Arrays.asList("Morgan Freeman", "Brad Pitt", "Kevin Spacey"), "https://www.imdb.com/title/tt0114369/"),
                new Movie("City of God", "Two boys growing up in a neighborhood of Rio de Janerio take different paths: one becomes a photographer, the other a drug dealer", Arrays.asList(Genre.CRIME, Genre.DRAMA), 2002, 8.6, 130, Arrays.asList("Fernando Meirelles", "Kátia Lund"), Arrays.asList("Paulo Lins", "Bráulio Mantovani"), Arrays.asList("Alexandre Rodrigues", "Leandro Firmino", "Matheus Nachtergaele"), "https://www.imdb.com/title/tt0317248/")
        );
        String result = controller.getLongestMovieTitle(movies);
        assertEquals("Once Upon a Time in Hollywood", result);
    }
    /*
    @Test
    public void testCountMoviesFrom() throws FileNotFoundException {
        HomeController controller = new HomeController();
        List<Movie> movies = Arrays.asList(
                new Movie("Once Upon a Time in Hollywood", "A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywoods's Golden Age in 1969 Los Angeles", Arrays.asList(Genre.COMEDY, Genre.DRAMA), 2019, 7.7, 161, Arrays.asList("Quentin Tarantino"), Arrays.asList("Quentin Tarantino"), Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"), "https://www.imdb.com/title/tt7131622/"),
                new Movie("Seven", "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his modus operandi.", Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY, Genre.THRILLER), 1995, 8.6, 127, Arrays.asList("David Fincher"), Arrays.asList("Andrew Kevin Walker"), Arrays.asList("Morgan Freeman", "Brad Pitt", "Kevin Spacey"), "https://www.imdb.com/title/tt0114369/"),
                new Movie("City of God", "Two boys growing up in a neighborhood of Rio de Janerio take different paths: one becomes a photographer, the other a drug dealer", Arrays.asList(Genre.CRIME, Genre.DRAMA), 2002, 8.6, 130, Arrays.asList("Fernando Meirelles", "Kátia Lund"), Arrays.asList("Paulo Lins", "Bráulio Mantovani"), Arrays.asList("Alexandre Rodrigues", "Leandro Firmino", "Matheus Nachtergaele"), "https://www.imdb.com/title/tt0317248/")
        );
        long result = controller.countMoviesFrom(movies, "David Fincher");
        assertEquals(1, result);
    }

    @Test
    public void testGetMoviesBetweenYearsFromAPI() throws FileNotFoundException {
        HomeController controller = new HomeController();
        List<Movie> movies = Arrays.asList(
                new Movie("Once Upon a Time in Hollywood", "A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywoods's Golden Age in 1969 Los Angeles", Arrays.asList(Genre.COMEDY, Genre.DRAMA), 2019, 7.7, 161, Arrays.asList("Quentin Tarantino"), Arrays.asList("Quentin Tarantino"), Arrays.asList("Leonardo DiCaprio", "Brad Pitt", "Margot Robbie"), "https://www.imdb.com/title/tt7131622/"),
                new Movie("Seven", "Two detectives, a rookie and a veteran, hunt a serial killer who uses the seven deadly sins as his modus operandi.", Arrays.asList(Genre.CRIME, Genre.DRAMA, Genre.MYSTERY, Genre.THRILLER), 1995, 8.6, 127, Arrays.asList("David Fincher"), Arrays.asList("Andrew Kevin Walker"), Arrays.asList("Morgan Freeman", "Brad Pitt", "Kevin Spacey"), "https://www.imdb.com/title/tt0114369/"),
                new Movie("City of God", "Two boys growing up in a neighborhood of Rio de Janerio take different paths: one becomes a photographer, the other a drug dealer", Arrays.asList(Genre.CRIME, Genre.DRAMA), 2002, 8.6, 130, Arrays.asList("Fernando Meirelles", "Kátia Lund"), Arrays.asList("Paulo Lins", "Bráulio Mantovani"), Arrays.asList("Alexandre Rodrigues", "Leandro Firmino", "Matheus Nachtergaele"), "https://www.imdb.com/title/tt0317248/")
        );
        List<Movie> result = controller.getMoviesBetweenYearsFromAPI(movies, 1990, 2010);
        assertEquals(2, result.size());
    }


    MovieAPI movieAPI = new MovieAPI();
    List<Movie> allMovies;

    @Test
    void callAPI_returns_correct_number_of_movies() throws IOException {
        // Given
        int expectedSize = 31;

        // When
        allMovies = movieAPI.callAPI(null);
        int actualSize = allMovies.size();

        // Then
        assertEquals(expectedSize, actualSize);
    }

    @Test
    void callAPI_returns_correct_movie_title_of_first_movie() throws IOException {
        // Given
        String expectedTitle = "The Godfather";

        // When
        allMovies = movieAPI.callAPI(null);
        String actualTitle = allMovies.get(0).getTitle();

        // Then
        assertEquals(expectedTitle, actualTitle);
    }

    @Test
    void callAPI_returns_correct_movie_lengthInMinutes_from_third_Element() throws IOException {
        // Given
        int expectedLengthInMinutes = 152;

        // When
        allMovies = movieAPI.callAPI(null);
        int actualLengthInMinutes = allMovies.get(2).getLengthInMinutes();

        // Then
        assertEquals(expectedLengthInMinutes, actualLengthInMinutes);
    }

    @Test
    void callAPI_returns_correct_movies_when_genre_is_COMERDY() throws IOException {
        // Given
        int expectedSize = 6;

        // When
        allMovies = movieAPI.callAPI("?genre=COMEDY");
        int actualSize = allMovies.size();

        // Then
        assertEquals(expectedSize, actualSize);
    }
} */