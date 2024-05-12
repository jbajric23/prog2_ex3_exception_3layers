// DatabaseManager.java
package at.ac.fhcampuswien.fhmdb.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:h2:mem:fhmdb";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    private Dao < WatchlistMovieEntity, Long > watchlistDao;
    private Dao < MovieEntity, Long > movieDao;
    private ConnectionSource connectionSource;

    public DatabaseManager() {
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public void createConnectionsSource(){
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
            try {
                TableUtils.createTableIfNotExists(connectionSource, WatchlistMovieEntity.class);
                TableUtils.createTableIfNotExists(connectionSource, MovieEntity.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    public void closeConnection() {
        if (connectionSource != null) {
            try {
                connectionSource.close();
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Dao < WatchlistMovieEntity, Long > getWatchlistDao() {
        return watchlistDao;
    }

    public Dao < MovieEntity, Long > getMovieDao() {
        return movieDao;
    }
}