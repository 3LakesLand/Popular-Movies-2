package com.popular.movies.data.remote.movie.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * The class MovieThumbnailEntity is used by retrofit to store the JSON structure of title, poster URL
 * and movie id.
 */
public class MovieThumbnailEntity implements Parcelable {

    /**
     * Factory class to create one or more MovieThumbnailEntity objects.
     */
    public static final Creator<MovieThumbnailEntity> CREATOR = new Creator<MovieThumbnailEntity>() {
        @Override
        public MovieThumbnailEntity createFromParcel(Parcel parcel) {
            return new MovieThumbnailEntity(parcel);
        }

        @Override
        public MovieThumbnailEntity[] newArray(int size) {
            return new MovieThumbnailEntity[size];
        }
    };
    @SerializedName("id")
    private Integer id;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("title")
    private String title;

    @SuppressWarnings("all")
    protected MovieThumbnailEntity(Parcel parcel) {
        id = parcel.readInt();
        posterPath = parcel.readString();
        title = parcel.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getTitle() {
        return title;
    }
}
