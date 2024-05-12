// MovieRepository.java
package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.data.MovieEntity;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.List;

public class MovieRepository {

    private Dao<MovieEntity, Long> movieDao;

    public MovieRepository(DatabaseManager databaseManager) {
        try {
            movieDao = DaoManager.createDao(databaseManager.getConnectionSource(), MovieEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MovieEntity> getAllMovies() {
        try {
            return movieDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int removeAll(){
        try {
            return movieDao.deleteBuilder().delete();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public MovieEntity getMovie(){
        try {
            return movieDao.queryForId(1L);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addAllMovies (List<MovieEntity> movies){
        try {
            return movieDao.create(movies);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}