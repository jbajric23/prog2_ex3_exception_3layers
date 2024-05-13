// MovieRepository.java
package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import static at.ac.fhcampuswien.fhmdb.data.MovieEntity.genresToString;

public class MovieRepository {

    private Dao<MovieEntity, Long> movieDao;

    public MovieRepository(Database databaseManager) {
        try {
            movieDao = DaoManager.createDao(databaseManager.getConnectionSource(), MovieEntity.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public MovieRepository() {

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

    public int addAllMovies (List<Movie> movies) {
        boolean success = true;
        for (Movie movie : movies) {
            try {
                movieDao.create(new MovieEntity(movie.getTitle(), movie.getApiId(), movie.getDescription(),
                        genresToString(movie.getGenres()), movie.getReleaseYear(),
                        movie.getRating(), movie.getLengthInMinutes(), movie.getImgUrl()));
            } catch (SQLException e) {
                e.printStackTrace();
                success = false;
            }
        }
        return success ? 1 : 0;
    }

    public Integer[] getYears() {
        try {
            List<MovieEntity> movies = movieDao.queryForAll();
            return movies.stream().map(MovieEntity::getReleaseYear).distinct().toArray(Integer[]::new);
        } catch (SQLException e) {
            e.printStackTrace();
            return new Integer[0];
        }
    }
}