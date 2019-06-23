package com.popular.movies.data.remote.movie.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * The class is used by retrofit to store the JSON structure of author, content and url.
 */
public class MovieReviewEntity implements Parcelable {

    /**
     * Factory class to create one or more movie review objects.
     */
    public static final Creator<MovieReviewEntity> CREATOR = new Creator<MovieReviewEntity>() {
        @Override
        public MovieReviewEntity createFromParcel(Parcel parcel) {
            return new MovieReviewEntity(parcel);
        }

        @Override
        public MovieReviewEntity[] newArray(int size) {
            return new MovieReviewEntity[size];
        }
    };

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @SerializedName("url")
    private String url;

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * deserialization of MovieReviewEntity
     *
     * @param parcel serialized object
     */
    private MovieReviewEntity(Parcel parcel) {
        this.author = parcel.readString();
        this.content = parcel.readString();
        this.url = parcel.readString();
    }


    /**
     * Serialization of MovieReviewEntity.
     *
     * @param parcel Serialization object
     * @param flags  control flags - not used
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(author);
        parcel.writeString(content);
        parcel.writeString(url);
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
