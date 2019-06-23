package com.popular.movies.data.remote.movie.db;

import com.popular.movies.utils.Utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * The singleton class ApiClient creates a retrofit object with a given base URL.
 */
class ApiClient {
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Utils.BASE_MOVIE_DATA_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    /**
     * Private constructor
     */
    private ApiClient() {
    }

    /**
     * Get the singleton retrofit object.
     *
     * @return retrofit object
     */
    static Retrofit getInstance() {
        return retrofit;
    }


}
