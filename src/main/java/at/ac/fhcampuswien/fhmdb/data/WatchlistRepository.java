// WatchlistRepository.java
package at.ac.fhcampuswien.fhmdb.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.List;

public class WatchlistRepository {

    private Dao<WatchlistMovieEntity, Long> dao;


    public WatchlistRepository(Database databaseManager) {
        try {
            dao = DaoManager.createDao(databaseManager.getConnectionSource(), WatchlistMovieEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<WatchlistMovieEntity> getWatchlist() {
        try {
            return dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addMovieToWatchlist(WatchlistMovieEntity movie) {
        try {
            dao.create(movie);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeMovieFromWatchlist(WatchlistMovieEntity movie) {
        try {
            dao.delete(movie);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // other methods...
}
