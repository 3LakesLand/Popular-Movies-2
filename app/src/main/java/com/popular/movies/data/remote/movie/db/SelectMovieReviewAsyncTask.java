package com.popular.movies.data.remote.movie.db;

import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.popular.movies.BuildConfig;
import com.popular.movies.data.remote.movie.db.model.MovieReviewEntity;
import com.popular.movies.data.remote.movie.db.model.MovieReviewEntityList;
import com.popular.movies.ui.model.MovieReview;
import com.popular.movies.utils.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Finds all movie reviews from the external movie database via asynchronous task.
 */
public class SelectMovieReviewAsyncTask extends AsyncTask<Integer, Void, Void> {
    private static final String TAG = SelectMovieReviewAsyncTask.class.getSimpleName();

    private MutableLiveData<List<MovieReview>> movieReviewListMutableLiveData;

    /**
     * Constructor
     *
     * @param movieReviewListMutableLiveData contains all found Movie reviews
     */
    public SelectMovieReviewAsyncTask(MutableLiveData<List<MovieReview>> movieReviewListMutableLiveData) {
        this.movieReviewListMutableLiveData = movieReviewListMutableLiveData;
    }

    /**
     * The method calls the external movie database and creates a copy of the receiving data
     * for the Surface representation.
     *
     * @param params on the first position is the movie id
     * @return - directly nothing
     */
    @Override
    protected Void doInBackground(Integer... params) {
        Integer movieId = params[0];
        ApiMovieDB apiMovieDB = ApiClient.getInstance().create(ApiMovieDB.class);

        Call<MovieReviewEntityList> call = apiMovieDB.getMovieReviews(
                movieId,
                BuildConfig.MOVIE_DB_API_KEY,
                Utils.MOVIE_DESCRIPTION_LANGUAGE);
        try {
            Response<MovieReviewEntityList> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                MovieReviewEntityList movieReviewEntityList = response.body();
                List<MovieReview> movieReviewList = new ArrayList<>();
                for (MovieReviewEntity movieReviewEntity : movieReviewEntityList.getResults()) {
                    movieReviewList.add(
                            new MovieReview(
                                    movieReviewEntity.getAuthor(),
                                    movieReviewEntity.getContent(),
                                    movieReviewEntity.getUrl()
                            )
                    );
                }
                movieReviewListMutableLiveData.postValue(movieReviewList);

            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

}
