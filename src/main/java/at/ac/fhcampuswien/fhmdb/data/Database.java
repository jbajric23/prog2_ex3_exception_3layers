// Database.java
package at.ac.fhcampuswien.fhmdb.data;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class Database {

    private static final String DATABASE_URL = "jdbc:h2:file:./data/movies.db";
    private static final String USER_NAME = "username";
    private static final String PASSWORD = "password";

    private Dao <WatchlistMovieEntity, Long> watchlistDao;
    private Dao <MovieEntity, Long> movieDao;
    private static ConnectionSource connectionSource;

    private static Database instance;

    public Database() {
        try {
            createConnectionsSource();
            movieDao = DaoManager.createDao(connectionSource, MovieEntity.class);
            watchlistDao = DaoManager.createDao(connectionSource, WatchlistMovieEntity.class);
            createTables();
        } catch (SQLException e) {
            // TODO: Implement new exception handling and Message to user in app
            System.out.println(e.getMessage());
        }
    }

    private void createConnectionsSource() throws SQLException {
        connectionSource = new JdbcConnectionSource(DATABASE_URL, USER_NAME, PASSWORD);
    }

    public ConnectionSource getConnectionSource() {
        if(instance == null){
            instance = new Database();
        }
        return connectionSource;
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
                watchlistDao.deleteBuilder().delete();
                movieDao.deleteBuilder().delete();
                connectionSource.close();
            } catch (SQLException e) {
                System.err.println("Error closing the database connection: " + e.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

}