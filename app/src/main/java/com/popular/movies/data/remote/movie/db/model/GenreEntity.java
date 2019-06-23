package com.popular.movies.data.remote.movie.db.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * The class contains the name of the movie genre. The class can be serialized via parcel.
 */
public class GenreEntity implements Parcelable {

    /**
     * Factory class to create one or more genre objects.
     */
    public static final Creator<GenreEntity> CREATOR = new Creator<GenreEntity>() {
        @Override
        public GenreEntity createFromParcel(Parcel parcel) {
            return new GenreEntity(parcel);
        }

        @Override
        public GenreEntity[] newArray(int size) {
            return new GenreEntity[size];
        }
    };
    @SerializedName("name")
    private String name;


    private GenreEntity(Parcel parcel) {
        this.name = parcel.readString();
    }

    public String getName() {
        return name;
    }


    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
