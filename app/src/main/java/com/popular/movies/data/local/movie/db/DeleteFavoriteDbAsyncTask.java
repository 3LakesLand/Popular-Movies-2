package com.popular.movies.data.local.movie.db;

import android.os.AsyncTask;

/**
 * The class deletes a favorite movie from the room database via asynchronous task.
 */
public class DeleteFavoriteDbAsyncTask extends AsyncTask<Integer, Void, Void> {
    private MovieDatabase database;

    /**
     * Constructor
     *
     * @param database of movie data
     */
    public DeleteFavoriteDbAsyncTask(MovieDatabase database) {
        this.database = database;
    }

    /**
     * Method deletes a favorite movie via movie id.
     * @param movieId of movie
     * @return nothing
     */
    @Override
    protected Void doInBackground(Integer... movieId) {
        FavoriteMovieDao favoriteMovieDao = database.favoriteMovieThumbnailDao();
        if (movieId != null && movieId[0] != null) {
            favoriteMovieDao.deleteFavoriteMovie(movieId[0]);
        }
        return null;
    }
}
