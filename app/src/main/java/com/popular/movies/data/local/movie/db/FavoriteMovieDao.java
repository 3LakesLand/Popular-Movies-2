package com.popular.movies.data.local.movie.db;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.popular.movies.data.local.movie.db.model.FavoriteEntity;

import java.util.List;

/**
 * Data Access Object for insert, select and delete favorite movies.
 * In addition, it can be checked whether a movie belongs to a favorite movie (via count).
 */
@Dao
public interface FavoriteMovieDao {
    @Query("SELECT id, movie_id, poster_path, title FROM favorit_table")
    LiveData<List<FavoriteEntity>> loadAllFavoriteMovie();

    @Insert
    void insertFavoriteMovie(FavoriteEntity favoriteMovieEntity);

    @Query("DELETE FROM favorit_table WHERE movie_id = :movieId")
    void deleteFavoriteMovie(int movieId);

    @Query("SELECT COUNT(movie_id) FROM favorit_table WHERE movie_id = :movieId")
    LiveData<Integer> countFavoriteMovie(int movieId);
}

