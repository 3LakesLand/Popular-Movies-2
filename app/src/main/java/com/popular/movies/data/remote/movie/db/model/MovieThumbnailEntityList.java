package com.popular.movies.data.remote.movie.db.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The class is used by retrofit to drop JSON results list of MovieEntity thumbnail.
 */
public class MovieThumbnailEntityList {

    @SerializedName("results")
    private List<MovieThumbnailEntity> results;

    /**
     * Constructor
     *
     * @param results list of MovieThumbnailEntity
     */
    public MovieThumbnailEntityList(List<MovieThumbnailEntity> results) {
        this.results = results;
    }

    public List<MovieThumbnailEntity> getResults() {
        return results;
    }
}
