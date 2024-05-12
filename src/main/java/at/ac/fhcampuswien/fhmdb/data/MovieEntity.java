
// MovieEntity.java
package at.ac.fhcampuswien.fhmdb.data;

import at.ac.fhcampuswien.fhmdb.models.Movie;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.List;

@DatabaseTable(tableName = "movies")
public class MovieEntity {

   @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField(id = true)
    private String apiId;

    @DatabaseField
    private String title;

    @DatabaseField
    private String description;

    @DatabaseField
    private String genres;

    @DatabaseField
    private int releaseYear;

    @DatabaseField
    private double rating;

    @DatabaseField
    private int lengthInMinutes;

    @DatabaseField
    private String imgUrl;


    public MovieEntity() {

    }

    public MovieEntity(String title, String apiId, String description, String genres, int releaseYear, double rating, int lengthInMinutes, String imgUrl) {
        this.title = title;
        this.apiId = apiId;
        this.description = description;
        this.genres = genres;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.lengthInMinutes = lengthInMinutes;
        this.imgUrl = imgUrl;
    }

    public String genresToString() {
        return genres;
    }

    public List <MovieEntity> fromMovies (List <MovieEntity> movies) {
        return movies;
    }

    public List <Movie> toMovies (List <Movie> movies) {
        return movies;
    }
}

