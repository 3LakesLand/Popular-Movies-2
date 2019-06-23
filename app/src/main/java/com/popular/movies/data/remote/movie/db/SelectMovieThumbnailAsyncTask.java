package com.popular.movies.data.remote.movie.db;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.popular.movies.BuildConfig;
import com.popular.movies.data.remote.movie.db.model.MovieThumbnailEntity;
import com.popular.movies.data.remote.movie.db.model.MovieThumbnailEntityList;
import com.popular.movies.ui.model.MovieThumbnail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Finds all movie thumbnails from the external movie database via asynchronous task.
 */
public class SelectMovieThumbnailAsyncTask extends AsyncTask<Boolean, Void, Void> {
    private static final String TAG = SelectMovieThumbnailAsyncTask.class.getSimpleName();

    private MutableLiveData<List<MovieThumbnail>> movieThumbnailListMutableLiveData;


    /**
     * Constructor
     *
     * @param movieThumbnailListMutableLiveData contains all found Movie Thumbnails
     */
    public SelectMovieThumbnailAsyncTask(MutableLiveData<List<MovieThumbnail>> movieThumbnailListMutableLiveData) {
        this.movieThumbnailListMutableLiveData = movieThumbnailListMutableLiveData;
    }


    /**
     * The method calls the external movie database and creates a copy of the receiving data
     * for the Surface representation.
     *
     * @param isPopular is popular or top rated (not popular)
     * @return - directly nothing
     */
    @Override
    protected Void doInBackground(Boolean... isPopular) {
        ApiMovieDB apiMovieDB = ApiClient.getInstance().create(ApiMovieDB.class);

        Call<MovieThumbnailEntityList> call;
        if (isPopular[0]) {
            call = apiMovieDB.getMovieThumbnailPopularRequest(BuildConfig.MOVIE_DB_API_KEY);
        } else {
            call = apiMovieDB.getMovieThumbnailTopRatedRequest(BuildConfig.MOVIE_DB_API_KEY);
        }
        try {
            Response<MovieThumbnailEntityList> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                MovieThumbnailEntityList movieThumbnailEntityList = response.body();
                List<MovieThumbnail> movieThumbnailList = new ArrayList<>();
                for (MovieThumbnailEntity movieThumbnailEntity : movieThumbnailEntityList.getResults()) {
                    movieThumbnailList.add(
                            new MovieThumbnail(
                                    movieThumbnailEntity.getId(),
                                    movieThumbnailEntity.getPosterPath(),
                                    movieThumbnailEntity.getTitle())
                    );
                }
                movieThumbnailListMutableLiveData.postValue(movieThumbnailList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return null;

    }
}
