package com.vp.roomaddons;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vp.roomaddons.dao.MovieDao;
import com.vp.roomaddons.entity.Movie;


@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract MovieDao MovieDao();
}