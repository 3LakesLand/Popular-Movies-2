package com.popular.movies.data.local.movie.db;

import android.os.AsyncTask;

import com.popular.movies.data.local.movie.db.model.FavoriteEntity;

/**
 * The class inserts a favorite movie in the room database via asynchronous task.
 */
public class InsertFavoriteDbAsyncTask extends AsyncTask<FavoriteEntity, Void, Void> {
    private MovieDatabase database;

    /**
     * Constructor
     *
     * @param database of movie data
     */
    public InsertFavoriteDbAsyncTask(MovieDatabase database) {
        this.database = database;
    }

    /**
     * Method inserts a favorite movie.
     *
     * @param favoriteEntities - first element is the favorite movie
     * @return nothing
     */
    @Override
    protected Void doInBackground(FavoriteEntity... favoriteEntities) {
        FavoriteMovieDao favoriteMovieDao = database.favoriteMovieThumbnailDao();
        if (favoriteEntities != null && favoriteEntities[0] != null) {
            favoriteMovieDao.insertFavoriteMovie(favoriteEntities[0]);
        }
        return null;
    }
}
