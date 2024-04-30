package at.ac.fhcampuswien.fhmdb.models;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class MovieAPI {
    private final OkHttpClient client;
    private final Gson gson;

    public MovieAPI() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<String> getDirectorFromAPI(Movie movie) {
        return movie.getDirectors();
    }

    public String getMostPopularActorFromAPI(List<Movie> movies) {
        return movies.stream()
                .flatMap(movie -> movie.getMainCast().stream())
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public String getLongestMovieTitleFromAPI(List<Movie> movies) {
        return movies.stream()
                .map(Movie::getTitle)
                .max(Comparator.comparingInt(String::length))
                .orElse(null);
    }

    public List<Movie> getMoviesBetweenYearsFromAPI(List<Movie> movies, int startYear, int endYear) {
        return movies.stream()
                .filter(movie -> movie.getReleaseYear() >= startYear && movie.getReleaseYear() <= endYear)
                .collect(Collectors.toList());
    }

    public List<Movie> callAPI(String filter) throws IOException {
        List<Movie> movies = new ArrayList<>();
        Request request;
        if (filter != null) {
             request = new Request.Builder()
                    .url("https://prog2.fh-campuswien.ac.at/movies" + filter)
                    .header("User-Agent", "FHMDB-App")
                    .build();
        } else {
            request = new Request.Builder()
                    .url("https://prog2.fh-campuswien.ac.at/movies")
                    .header("User-Agent", "FHMDB-App")
                    .build();
        }
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Movie data could not be loaded. " + response);
            JsonArray jsonArray = gson.fromJson(response.body().string(), JsonArray.class);
            return loadMovies(movies, jsonArray);
        }
    }

    public List<Movie> loadMovies(List<Movie> movies, JsonArray jsonArray) {
            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject movieElement = jsonArray.get(i).getAsJsonObject();
                movies.add(createMovie(movieElement));
            }
        return movies;
    }

    private Movie createMovie(JsonObject movieArray) {
        // Create Movie and attributes needed for the constructor
        String title = movieArray.get("title").getAsString();
        String description = movieArray.get("description").getAsString();
        List<Genre> genres = new ArrayList<>();
        JsonElement genresElement = movieArray.get("genres");
        genres = setGenresToList(genresElement);
        int releaseYear = movieArray.get("releaseYear").getAsInt();
        double rating = movieArray.get("rating").getAsFloat();
        int lengthInMinutes = movieArray.get("lengthInMinutes").getAsInt();
        List<String> directors = setListFromJsonElement(movieArray.get("directors"));
        List<String> writers = setListFromJsonElement(movieArray.get("writers"));
        List<String> mainCast = setListFromJsonElement(movieArray.get("mainCast"));
        String imgUrl = movieArray.get("imgUrl").getAsString();
        Movie movie = new Movie(title, description, genres, releaseYear, rating, lengthInMinutes, directors, writers, mainCast, imgUrl);
        return movie;
    }

    private List<Genre> setGenresToList(JsonElement genresElement) {
        List<Genre> genres = new ArrayList<>();
        if (genresElement.isJsonArray()) {
            JsonArray genresArray = genresElement.getAsJsonArray();
            for (JsonElement genreElement : genresArray) {
                String genreName = genreElement.getAsString();
                Genre genre = Genre.valueOf(genreName);
                genres.add(genre);
            }
        } else {
            String genresString = genresElement.getAsString();
            for (String genreName : genresString.split(",")) {
                Genre genre = Genre.valueOf(genreName);
                genres.add(genre);
            }
        }
        return genres;
    }

    private List<String> setListFromJsonElement(JsonElement element) {
        List<String> list = new ArrayList<>();
        if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                list.add(jsonElement.getAsString());
            }
        } else {
            list.add(element.getAsString());
        }
        return list;
    }
}
