package com.popular.movies.data.local.movie.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.popular.movies.data.local.movie.db.model.FavoriteEntity;

/**
 * Create a movie database in room via singleton pattern.
 */
@Database(entities = {FavoriteEntity.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "favorite_movie_db";
    private static MovieDatabase databaseInstance;

    public static synchronized MovieDatabase getInstance(Context context) {
        if (databaseInstance == null) {
            //Creating new database instance
            databaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                    MovieDatabase.class, MovieDatabase.DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return databaseInstance;
    }

    public abstract FavoriteMovieDao favoriteMovieThumbnailDao();

}

