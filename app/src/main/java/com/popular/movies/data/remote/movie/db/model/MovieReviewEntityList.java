package com.popular.movies.data.remote.movie.db.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * List of MovieReviewEntities
 */
public class MovieReviewEntityList {

    @SerializedName("results")
    private List<MovieReviewEntity> results;

    /**
     * Constructor
     *
     * @param results list of MovieThumbnailEntity
     */
    public MovieReviewEntityList(List<MovieReviewEntity> results) {
        this.results = results;
    }

    public List<MovieReviewEntity> getResults() {
        return results;
    }
}
