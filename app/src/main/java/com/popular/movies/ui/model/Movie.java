package com.popular.movies.ui.model;

import android.graphics.Bitmap;

/**
 * Movie Model for the detailed representation of the movie information in the surface.
 */
public class Movie extends MovieThumbnail {
    private static final String UNKNOWN_VOTE_AVERAGE = "0.0";
    private static final String UNKNOWN = "";

    private String voteAverage;
    private String popularity;
    private String overview;
    private String releaseDate;
    private String genres;
    private Bitmap movieImage;
    private Boolean isFavorite;

    public Movie(Integer movieId) {
        super(movieId, UNKNOWN, UNKNOWN);
        this.isFavorite = Boolean.FALSE;
        this.voteAverage = UNKNOWN_VOTE_AVERAGE;
        this.popularity = UNKNOWN;
        this.overview = UNKNOWN;
        this.releaseDate = UNKNOWN;
        this.genres = UNKNOWN;

    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public Bitmap getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(Bitmap movieImage) {
        this.movieImage = movieImage;
    }

    public Boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }
}
