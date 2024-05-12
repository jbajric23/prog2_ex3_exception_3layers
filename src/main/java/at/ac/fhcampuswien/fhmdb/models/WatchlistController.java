package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.data.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;

import java.util.function.Consumer;

public class WatchlistController {
    private final WatchlistRepository watchlistRepository;

    public WatchlistController(WatchlistRepository watchlistRepository) {

        this.watchlistRepository = watchlistRepository;
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
}
