package com.popular.movies.ui.model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.popular.movies.repository.MovieRepository;
import com.popular.movies.utils.MarkOfMovie;

import java.util.List;

/**
 * The model contains all data for the Movie Thumbnail and the Menu Selection (popular, top rated, favorite).
 * Because there is a separation between surfaces and database data,
 * copies of the data are located here (via MutableLiveData).
 * The data is made available through the Movie Repository.
 */
public class MovieThumbnailViewModel extends AndroidViewModel {
    private MovieRepository movieRepository;
    private MutableLiveData<List<MovieThumbnail>> movieThumbnailFavoriteListMutableLiveData;
    private MutableLiveData<List<MovieThumbnail>> movieThumbnailPopularListMutableLiveData;
    private MutableLiveData<List<MovieThumbnail>> movieThumbnailTopRatedListMutableLiveData;
    private MarkOfMovie markOfMovie;


    /**
     * Constructor
     *
     * @param application needed for local database calls via MovieRepository
     */
    public MovieThumbnailViewModel(@NonNull Application application) {
        super(application);
        movieRepository = new MovieRepository(application);
        movieThumbnailFavoriteListMutableLiveData = new MutableLiveData<>();
        movieThumbnailPopularListMutableLiveData = new MutableLiveData<>();
        movieThumbnailTopRatedListMutableLiveData = new MutableLiveData<>();
        markOfMovie = MarkOfMovie.POPULAR;
        Log.d("MovieThumbnailViewModel", "create");
    }

    /**
     * According to the menu selection, the data is queried via the Movie Repository.
     * Where the data comes from, only ds Movie Repository knows.
     *
     * @param lifecycleOwner (status) of the current activity
     */
    public void selectMovieThumbnails(LifecycleOwner lifecycleOwner) {
        if (markOfMovie != null) {
            switch (markOfMovie) {
                case POPULAR:
                    movieRepository.selectMovieThumbnails(true, movieThumbnailPopularListMutableLiveData);
                    break;
                case TOP_RATED:
                    movieRepository.selectMovieThumbnails(false, movieThumbnailTopRatedListMutableLiveData);
                    break;
                case FAVORITE:
                    movieRepository.selectFavoriteMovieThumbnails(lifecycleOwner, movieThumbnailFavoriteListMutableLiveData);
                    break;
            }
        }
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
     * see also {@link ViewModel#onCleared()}
     */
    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("MovieThumbnailViewModel", "destroy: -> onCleared()");
    }

    public MutableLiveData<List<MovieThumbnail>> getMovieThumbnailFavoriteList() {
        return movieThumbnailFavoriteListMutableLiveData;
    }
    public MutableLiveData<List<MovieThumbnail>> getMovieThumbnailPopularList() {
        return movieThumbnailPopularListMutableLiveData;
    }
    public MutableLiveData<List<MovieThumbnail>> getMovieThumbnailTopRatedList() {
        return movieThumbnailTopRatedListMutableLiveData;
    }

    public void setMarkOfMovie(MarkOfMovie markOfMovie) {
        this.markOfMovie = markOfMovie;
    }

    public MarkOfMovie getMarkOfMovie() {return markOfMovie;}


}
