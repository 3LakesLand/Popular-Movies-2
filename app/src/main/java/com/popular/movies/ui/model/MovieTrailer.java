package com.popular.movies.ui.model;

/**
 * MovieTrailer Model for the short description of the movie trailer in the surface.
 * The Id is intended to call an external youtube page.
 */
public class MovieTrailer {

    private String youTubeVideoId;
    private String name;

    public MovieTrailer(String youTubeVideoId, String name) {
        this.youTubeVideoId = youTubeVideoId;
        this.name = name;
    }

    public String getYouTubeVideoId() {
        return youTubeVideoId;
    }

    public String getName() {
        return name;
    }
}
