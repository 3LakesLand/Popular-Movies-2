package com.popular.movies.data.remote.movie.db;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.popular.movies.BuildConfig;
import com.popular.movies.data.remote.movie.db.model.MovieTrailerEntity;
import com.popular.movies.data.remote.movie.db.model.MovieTrailerEntityList;
import com.popular.movies.ui.model.MovieTrailer;
import com.popular.movies.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Finds all movie trailers from the external movie database via asynchronous task.
 */

public class SelectMovieTrailerAsyncTask extends AsyncTask<Integer, Void, Void> {
    private static final String TAG = SelectMovieTrailerAsyncTask.class.getSimpleName();
    private static final String YOUTUBE_SITE = "YouTube";
    private static final String TRAILER = "Trailer";


    private MutableLiveData<List<MovieTrailer>> movieTrailerListMutableLiveData;

    /**
     * Constructor
     *
     * @param movieTrailerListMutableLiveData contains all found Movie trailers
     */
    public SelectMovieTrailerAsyncTask(MutableLiveData<List<MovieTrailer>> movieTrailerListMutableLiveData) {
        this.movieTrailerListMutableLiveData = movieTrailerListMutableLiveData;
    }

    /**
     * The method calls the external movie database and creates a copy of the receiving data
     * for the Surface representation. Get only trailers for youtube site.
     *
     * @param params on the first position is the movie id
     * @return - directly nothing
     */
    @Override
    protected Void doInBackground(Integer... params) {
        Integer movieId = params[0];
        ApiMovieDB apiMovieDB = ApiClient.getInstance().create(ApiMovieDB.class);

        Call<MovieTrailerEntityList> call = apiMovieDB.getMovieTrailer(
                movieId,
                BuildConfig.MOVIE_DB_API_KEY,
                Utils.MOVIE_DESCRIPTION_LANGUAGE);
        try {
            Response<MovieTrailerEntityList> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                MovieTrailerEntityList movieTrailerEntityList = response.body();
                List<MovieTrailer> movieTrailerList = new ArrayList<>();
                for (MovieTrailerEntity movieTrailerEntity : movieTrailerEntityList.getResults()) {
                    //Get only trailers for youtube site.
                    if (YOUTUBE_SITE.equals(movieTrailerEntity.getSite()) &&
                            TRAILER.equals(movieTrailerEntity.getType())) {
                        movieTrailerList.add(
                                new MovieTrailer(
                                        movieTrailerEntity.getUrlKey(),
                                        movieTrailerEntity.getName()
                                )
                        );
                    }
                }
                movieTrailerListMutableLiveData.postValue(movieTrailerList);

            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return null;
    }
}
