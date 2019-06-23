package com.popular.movies.data.remote.movie.db.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Lost of MovieTrailerEntities
 */
public class MovieTrailerEntityList {

    @SerializedName("results")
    private List<MovieTrailerEntity> results;

    /**
     * Constructor
     *
     * @param results list of MovieTrailerEntity
     */
    public MovieTrailerEntityList(List<MovieTrailerEntity> results) {
        this.results = results;
    }

    public List<MovieTrailerEntity> getResults() {
        return results;
    }
}
