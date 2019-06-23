package com.popular.movies.ui.model;

/**
 * MovieThumbnail Model for the short description of the movie in the surface.
 */
public class MovieThumbnail {

    private Integer movieId;
    private String posterPath;
    private String title;

    public MovieThumbnail(Integer movieId, String posterPath, String title) {
        this.movieId = movieId;
        this.posterPath = posterPath;
        this.title = title;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
