package at.ac.fhcampuswien.fhmdb.models;

import at.ac.fhcampuswien.fhmdb.data.WatchlistMovieEntity;
import at.ac.fhcampuswien.fhmdb.data.WatchlistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class WatchlistControllerTest {
    private WatchlistRepository watchlistRepository;
    private WatchlistController watchlistController;

    @BeforeEach
    public void setup() {
        watchlistRepository = Mockito.mock(WatchlistRepository.class);
        watchlistController = new WatchlistController(watchlistRepository);
    }

    @Test
    public void testAddToWatchlist() {
        String apiId = "testApiId";
        watchlistController.addToWatchlist(apiId);

        verify(watchlistRepository, times(1)).addMovieToWatchlist(any(WatchlistMovieEntity.class));
    }

    @Test
    public void testRemoveFromWatchlist() {
        String apiId = "testApiId";
        watchlistController.removeFromWatchlist(apiId);

        verify(watchlistRepository, times(1)).removeMovieFromWatchlist(any(WatchlistMovieEntity.class));
    }
}