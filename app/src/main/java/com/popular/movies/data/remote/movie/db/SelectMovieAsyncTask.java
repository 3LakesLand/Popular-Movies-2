package com.popular.movies.data.remote.movie.db;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.popular.movies.BuildConfig;
import com.popular.movies.data.remote.movie.db.model.GenreEntity;
import com.popular.movies.data.remote.movie.db.model.MovieEntity;
import com.popular.movies.ui.model.Movie;
import com.popular.movies.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Find the movie from the external movie database via asynchronous task.
 */
public class SelectMovieAsyncTask extends AsyncTask<Integer, Void, Void> {
    private static final String TAG = SelectMovieAsyncTask.class.getSimpleName();
    private static final String COMMA_SEPARATOR = ", ";

    private MutableLiveData<Movie> movieMutableLiveData;


    /**
     * Constructor
     *
     * @param movieMutableLiveData contains the Movie
     */
    public SelectMovieAsyncTask(MutableLiveData<Movie> movieMutableLiveData) {
        this.movieMutableLiveData = movieMutableLiveData;
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

        Call<MovieEntity> call = apiMovieDB.getMovieRequest(
                movieId,
                BuildConfig.MOVIE_DB_API_KEY,
                Utils.MOVIE_DESCRIPTION_LANGUAGE);
        try {
            Response<MovieEntity> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                MovieEntity movieEntity = response.body();
                final Movie movie = movieMutableLiveData.getValue();
                if (movie != null) {
                    movie.setMovieId(movieEntity.getId());
                    movie.setPosterPath(movieEntity.getPosterPath());
                    movie.setTitle(movieEntity.getTitle());
                    movie.setVoteAverage(movieEntity.getVoteAverage());
                    movie.setPopularity(movieEntity.getPopularity());
                    movie.setOverview(movieEntity.getOverview());
                    movie.setReleaseDate(movieEntity.getReleaseDate());
                    movie.setGenres(getGenreList(movieEntity.getGenres()));
                    movie.setMovieImage(loadMovieImage(Utils.BIG_IMAGE_URL + movie.getPosterPath()));
                    movieMutableLiveData.postValue(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        return null;
    }

    /**
     * The movie poster is reloaded. It's bigger than the overview picture.
     *
     * @param UrlString of the movie poster
     * @return the bitmap of the movie poster
     */
    private Bitmap loadMovieImage(String UrlString) {
        try {
            return Picasso.get().load(UrlString).get();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * The convenience method converts a list of genre values as a comma-separated string list.
     *
     * @param genres list of genre values
     * @return comma-separated string list
     */
    private String getGenreList(List<GenreEntity> genres) {
        StringBuilder result = new StringBuilder();
        int indexOfLastGenre = genres.size() - 1;
        for (int i = 0; i < genres.size(); i++) {
            result.append(genres.get(i).getName());
            if (i != indexOfLastGenre) {
                result.append(COMMA_SEPARATOR);
            }
        }
        return result.toString();
    }


}
