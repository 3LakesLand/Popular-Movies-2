package com.popular.movies.ui.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;

import com.popular.movies.repository.MovieRepository;

import java.util.List;

/**
 * The model contains all the data for the selected movie and the review or trailer lists.
 * Because there is a separation between surfaces and database data,
 * copies of the data are located here (via MutableLiveData).
 * The data is made available through the Movie Repository.
 */
public class MovieViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<Movie> movieMutableLiveData;
    private MutableLiveData<List<MovieReview>> movieReviewListMutableLiveData;
    private MutableLiveData<List<MovieTrailer>> movieTrailerListMutableLiveData;


    /**
     * Constructor
     *
     * @param application needed for local database calls via MovieRepository
     */
    public MovieViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        movieMutableLiveData = new MutableLiveData<>();
        movieReviewListMutableLiveData = new MutableLiveData<>();
        movieTrailerListMutableLiveData = new MutableLiveData<>();
    }


    /**
     * see also {@link ViewModel#onCleared()}
     */
    @Override
    protected void onCleared() {
        super.onCleared();
    }

    /**
     * Checks if there is an Internet connection.
     *
     * @return internet connection exists
     */
    public boolean isInternetConnection() {
        return movieRepository.isInternetConnection();
    }


    /**
     * The method retrieves the movie information via movie id.
     *
     * @param movieId of the current movie
     */
    public void selectMovie(Integer movieId) {
        movieRepository.selectMovie(movieId, movieMutableLiveData);
    }

    /**
     * The method retrieves the movie reviews via movie id.
     *
     * @param movieId of the current movie
     */
    public void selectMovieReview(Integer movieId) {
        movieRepository.selectMovieReview(movieId, movieReviewListMutableLiveData);
    }

    /**
     * The method retrieves the movie trailers via movie id.
     *
     * @param movieId of the current movie
     */
    public void selectMovieTrailer(Integer movieId) {
        movieRepository.selectMovieTrailer(movieId, movieTrailerListMutableLiveData);
    }

    /**
     * The method retrieves the favorite movie via movie id in the current movie (in the movieMutableLiveData).
     *
     * @param lifecycleOwner (status) of the current Activity
     */
    public void foundFavoriteMovie(LifecycleOwner lifecycleOwner) {
        movieRepository.foundFavoriteMovieThumbnail(lifecycleOwner, movieMutableLiveData);
    }

    /**
     * If the movie is not already in the Favorites database, the method causes the movie to be written to the database.
     * If the movie is in the Favorites database, the method causes the movie to be deleted from the database.
     */
    public void changeFavoriteStatus() {
        if (movieMutableLiveData.getValue() != null) {
            if (movieMutableLiveData.getValue().isFavorite()) {
                movieRepository.deleteFavoriteMovieThumbnail(movieMutableLiveData.getValue());
            } else {
                movieRepository.insertFavoriteMovieThumbnail(movieMutableLiveData.getValue());
            }
        }
    }


    public MutableLiveData<Movie> getMovie() {
        return movieMutableLiveData;
    }

    public MutableLiveData<List<MovieReview>> getMovieReview() {
        return movieReviewListMutableLiveData;
    }

    public MutableLiveData<List<MovieTrailer>> getMovieTrailerList() {
        return movieTrailerListMutableLiveData;
    }

}
