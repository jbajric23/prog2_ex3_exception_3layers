// WatchlistMovieEntity.java
package at.ac.fhcampuswien.fhmdb.data;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "watchlist")
public class WatchlistMovieEntity {

    @DatabaseField(id = true)
    private String apiId;

    @DatabaseField
    private long id;

    // getters and setters...
}
