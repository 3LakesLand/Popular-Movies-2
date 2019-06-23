package com.popular.movies.data.local.movie.db.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * The class is used by room database to store the structure of favorite movie title,
 * poster path, movie id and the primary key (id).
 */
@Entity(tableName = "favorit_table")
public class FavoriteEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "movie_id")
    private int movieId;

    private String title;

    @ColumnInfo(name = "poster_path")
    private String posterPath;

    /**
     * The Constructor is called by room
     *
     * @param id         primary key
     * @param movieId    of the movie
     * @param title      of the movie
     * @param posterPath of the movie
     */
    public FavoriteEntity(int id, int movieId, String title, String posterPath) {
        this.id = id;
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
    }

    /**
     * Constructor
     *
     * @param movieId    of the movie
     * @param title      of the movie
     * @param posterPath of the movie
     */
    @Ignore
    public FavoriteEntity(int movieId, String title, String posterPath) {
        this.movieId = movieId;
        this.title = title;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

}

