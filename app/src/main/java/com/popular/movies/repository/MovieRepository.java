package com.popular.movies.repository;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.popular.movies.data.local.movie.db.DeleteFavoriteDbAsyncTask;
import com.popular.movies.data.local.movie.db.FavoriteMovieDao;
import com.popular.movies.data.local.movie.db.InsertFavoriteDbAsyncTask;
import com.popular.movies.data.local.movie.db.MovieDatabase;
import com.popular.movies.data.local.movie.db.model.FavoriteEntity;
import com.popular.movies.data.network.ConnectionHelper;
import com.popular.movies.data.remote.movie.db.SelectMovieAsyncTask;
import com.popular.movies.data.remote.movie.db.SelectMovieReviewAsyncTask;
import com.popular.movies.data.remote.movie.db.SelectMovieThumbnailAsyncTask;
import com.popular.movies.data.remote.movie.db.SelectMovieTrailerAsyncTask;
import com.popular.movies.ui.model.Movie;
import com.popular.movies.ui.model.MovieReview;
import com.popular.movies.ui.model.MovieThumbnail;
import com.popular.movies.ui.model.MovieTrailer;

import java.util.ArrayList;
import java.util.List;

/**
 * The movie repository class split the surface and the database or ConnectivityManager (Separation of Concerns).
 * The surface calls the display data only from the repository. The repository organizes data delivery and storage.
 * To prevent the dependency between DB data (external and internal) with the surface, the live data is copied (via MutableLiveData).
 */
public class MovieRepository {
    private Application application;
    private FavoriteMovieDao favoriteMovieDao;
    private MovieDatabase database;

    /**
     * Constructor
     *
     * @param application needed for local database calls
     */
    public MovieRepository(Application application) {
        this.application = application;
        database = MovieDatabase.getInstance(application);
        favoriteMovieDao = database.favoriteMovieThumbnailDao();
    }

    /**
     * By copying the database data to the surface data, an independence between the database and
     * the interface is achieved. The Observer copies all Favorite Movies.
     *
     * @param lifecycleOwner                    (status) of the current activity
     * @param movieThumbnailListMutableLiveData list of movieThumbnails
     */
    public void selectFavoriteMovieThumbnails(LifecycleOwner lifecycleOwner,
                                              final MutableLiveData<List<MovieThumbnail>> movieThumbnailListMutableLiveData) {
        favoriteMovieDao.loadAllFavoriteMovie().observe(lifecycleOwner, new Observer<List<FavoriteEntity>>() {
            @Override
            public void onChanged(@Nullable List<FavoriteEntity> favoriteEntities) {
                if (favoriteEntities != null) {
                    List<MovieThumbnail> movieThumbnails = new ArrayList<>();
                    for (FavoriteEntity favoriteEntity : favoriteEntities) {
                        movieThumbnails.add(
                                new MovieThumbnail(
                                        favoriteEntity.getMovieId(),
                                        favoriteEntity.getPosterPath(),
                                        favoriteEntity.getTitle()));
                    }
                    movieThumbnailListMutableLiveData.postValue(movieThumbnails);
                }
            }
        });
    }

    /**
     * By copying the database data to the surface data, an independence between the database and
     * the interface is achieved.
     * The Observer determines whether a favorite was found (via the count) and,
     * according to the findings, set the Favorite Flag.
     *
     * @param lifecycleOwner       (status) of the current activity
     * @param movieMutableLiveData contains the movie with its Id
     */
    public void foundFavoriteMovieThumbnail(LifecycleOwner lifecycleOwner,
                                            final MutableLiveData<Movie> movieMutableLiveData) {
        if (movieMutableLiveData.getValue() != null) {
            favoriteMovieDao.countFavoriteMovie(movieMutableLiveData.getValue().getMovieId())
                    .observe(lifecycleOwner, new Observer<Integer>() {
                        @Override
                        public void onChanged(@Nullable Integer count) {
                            final Boolean isFavorite = count != null && count > 0;
                            Movie movie = movieMutableLiveData.getValue();
                            movie.setFavorite(isFavorite);
                            movieMutableLiveData.postValue(movie);
                        }
                    });

        }
    }

    /**
     * By copying the surface data to the database data, an independence between the database and
     * the interface is achieved.
     * Inserts the favorite movie into the favorite database via an asynchronous task.
     *
     * @param movieThumbnail current favorite movie Thumbnail
     */
    public void insertFavoriteMovieThumbnail(MovieThumbnail movieThumbnail) {
        FavoriteEntity favoriteEntity =
                new FavoriteEntity(
                        movieThumbnail.getMovieId(),
                        movieThumbnail.getTitle(),
                        movieThumbnail.getPosterPath());
        new InsertFavoriteDbAsyncTask(database).execute(favoriteEntity);
    }

    /**
     * Deletes the favorite movie in the favorite database via an asynchronous task.
     *
     * @param movieThumbnail current favorite movie Thumbnail
     */
    public void deleteFavoriteMovieThumbnail(MovieThumbnail movieThumbnail) {
        new DeleteFavoriteDbAsyncTask(database).execute(movieThumbnail.getMovieId());
    }


    /**
     * Checks if there is an Internet connection.
     *
     * @return internet connection exists
     */
    public boolean isInternetConnection() {
        return ConnectionHelper.getInstance().isInternetConnection(application);
    }

    /**
     * Finds all movie thumbnails from the external movie database via asynchronous task.
     *
     * @param isPopular                         is popular or top rated (not popular)
     * @param movieThumbnailListMutableLiveData contains all found Movie Thumbnails
     */
    public void selectMovieThumbnails(Boolean isPopular, MutableLiveData<List<MovieThumbnail>> movieThumbnailListMutableLiveData) {
        new SelectMovieThumbnailAsyncTask(movieThumbnailListMutableLiveData).execute(isPopular);
    }

    /**
     * Finds all movie information from the external movie database using the Movie ID via asynchronous task.
     *
     * @param movieId              of the current movie
     * @param movieMutableLiveData contains all found Movie information
     */
    public void selectMovie(Integer movieId, MutableLiveData<Movie> movieMutableLiveData) {
        new SelectMovieAsyncTask(movieMutableLiveData).execute(movieId);
    }

    /**
     * Finds the reviews from the external movie database using the Movie ID via asynchronous task.
     *
     * @param movieId                        of the current movie
     * @param movieReviewListMutableLiveData contains all found review information
     */
    public void selectMovieReview(Integer movieId, MutableLiveData<List<MovieReview>> movieReviewListMutableLiveData) {
        new SelectMovieReviewAsyncTask(movieReviewListMutableLiveData).execute(movieId);
    }

    /**
     * Finds the trailers from the external movie database using the Movie ID via asynchronous task.
     *
     * @param movieId                         of the current movie
     * @param movieTrailerListMutableLiveData contains all found trailer information
     */
    public void selectMovieTrailer(Integer movieId, MutableLiveData<List<MovieTrailer>> movieTrailerListMutableLiveData) {
        new SelectMovieTrailerAsyncTask(movieTrailerListMutableLiveData).execute(movieId);
    }

}
