package com.popular.movies.ui.model;

/**
 * MovieReview Model for the short description of the movie review in the surface.
 * The URL is intended to call an external review page.
 */
public class MovieReview {

    private String author;
    private String content;
    private String url;

    public MovieReview(String author, String content, String url) {
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }
}
