package com.popular.movies.data.remote.movie.db.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * The class is used by retrofit to store the JSON structure of title, poster URL,
 * movie vote average, movie popularity, release date and genres.
 */
public class MovieEntity extends MovieThumbnailEntity implements Parcelable {

    /**
     * Factory class to create one or more movie objects.
     */
    public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
        @Override
        public MovieEntity createFromParcel(Parcel parcel) {
            return new MovieEntity(parcel);
        }

        @Override
        public MovieEntity[] newArray(int size) {
            return new MovieEntity[size];
        }
    };
    @SerializedName("vote_average")
    private String voteAverage;
    @SerializedName("popularity")
    private String popularity;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SuppressWarnings("unused")
    @SerializedName("genres")
    private List<GenreEntity> genres;

    private Bitmap movieImage;

    /**
     * deserialization of MovieEntity
     *
     * @param parcel serialized object
     */
    private MovieEntity(Parcel parcel) {
        super(parcel);
        this.voteAverage = parcel.readString();
        this.popularity = parcel.readString();
        this.overview = parcel.readString();
        this.releaseDate = parcel.readString();
        parcel.readTypedList(this.genres, GenreEntity.CREATOR);
    }

    /**
     * Serialization of MovieEntity.
     *
     * @param parcel Serialization object
     * @param flags  control flags - not used
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        super.writeToParcel(parcel, flags);
        parcel.writeString(voteAverage);
        parcel.writeString(popularity);
        parcel.writeString(overview);
        parcel.writeString(releaseDate);
        parcel.writeTypedList(genres);
    }

    public Bitmap getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(Bitmap movieImage) {
        this.movieImage = movieImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public List<GenreEntity> getGenres() {
        return genres;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }
}
