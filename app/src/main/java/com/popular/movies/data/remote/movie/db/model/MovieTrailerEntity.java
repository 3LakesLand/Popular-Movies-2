package com.popular.movies.data.remote.movie.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * The class is used by retrofit to store the JSON structure of trailer name, url,
 * site (by example youtube), type (MovieTrailer, Teaser, Clip, Featurette, Behind the Scenes, Bloopers).
 */
public class MovieTrailerEntity implements Parcelable {

    /**
     * Factory class to create one or more MovieTrailerEntity objects.
     */

    public static final Creator<MovieTrailerEntity> CREATOR = new Creator<MovieTrailerEntity>() {
        @Override
        public MovieTrailerEntity createFromParcel(Parcel parcel) {
            return new MovieTrailerEntity(parcel);
        }

        @Override
        public MovieTrailerEntity[] newArray(int size) {
            return new MovieTrailerEntity[size];
        }
    };

    /**
     * deserialization of MovieTrailerEntity
     *
     * @param parcel serialized object
     */
    private MovieTrailerEntity(Parcel parcel) {
        this.urlKey = parcel.readString();
        this.name = parcel.readString();
        this.site = parcel.readString();
        this.type = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Serialization of MovieTrailerEntity.
     *
     * @param parcel Serialization object
     * @param flags  control flags - not used
     */
    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(urlKey);
        parcel.writeString(name);
        parcel.writeString(site);
        parcel.writeString(type);
    }

    @SerializedName("key")
    private String urlKey;
    @SerializedName("name")
    private String name;
    @SerializedName("site") //YouTube and other
    private String site;

    // MovieTrailer, Teaser, Clip, Featurette, Behind the Scenes, Bloopers
    @SerializedName("type")
    private String type;

    public String getUrlKey() {
        return urlKey;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }
}
