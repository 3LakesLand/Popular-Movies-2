package com.popular.movies.data.remote.movie.db;

import com.popular.movies.data.remote.movie.db.model.MovieEntity;
import com.popular.movies.data.remote.movie.db.model.MovieReviewEntityList;
import com.popular.movies.data.remote.movie.db.model.MovieThumbnailEntityList;
import com.popular.movies.data.remote.movie.db.model.MovieTrailerEntityList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * The interface describes the three query methods required.
 * The methods are used to create the URLs for retrieval movie data of the MovieDB.
 */
public interface ApiMovieDB {

    /**
     * List of popular movies (descending by popularity).
     * The number of list items is limited by the MovieDB.
     *
     * @param apiKey - Personal access code
     * @return List of popular movies
     */
    @GET("movie/popular")
    Call<MovieThumbnailEntityList> getMovieThumbnailPopularRequest(@Query("api_key") String apiKey);

    /**
     * List of top voted movies (descending by Votation).
     * The number of list items is limited by the MovieDB.
     *
     * @param apiKey - Personal access code
     * @return List of top voted movies
     */
    @GET("movie/top_rated")
    Call<MovieThumbnailEntityList> getMovieThumbnailTopRatedRequest(@Query("api_key") String apiKey);

    /**
     * More film data from the MovieDB is selected via the MovieEntity ID.
     *
     * @param movieID  of a specific movie
     * @param apiKey   - Personal access code
     * @param language - movie description language
     * @return movie data with current movie ID
     */
    @GET("movie/{id}")
    Call<MovieEntity> getMovieRequest(@Path("id") int movieID,
                                      @Query("api_key") String apiKey,
                                      @Query("language") String language);


    /**
     * Reviews data from the MovieDB is selected via the MovieEntity ID.
     *
     * @param movieID  of a specific movie
     * @param apiKey   - Personal access code
     * @param language - movie description language
     * @return reviews data
     */
    @GET("movie/{id}/reviews")
    Call<MovieReviewEntityList> getMovieReviews(@Path("id") int movieID,
                                                @Query("api_key") String apiKey,
                                                @Query("language") String language);


    /**
     * Trailers data from the MovieDB is selected via the MovieEntity ID.
     *
     * @param movieID  of a specific movie
     * @param apiKey   - Personal access code
     * @param language - movie description language
     * @return trailers data
     */
    @GET("movie/{id}/videos")
    Call<MovieTrailerEntityList> getMovieTrailer(@Path("id") int movieID,
                                                 @Query("api_key") String apiKey,
                                                 @Query("language") String language);


}
