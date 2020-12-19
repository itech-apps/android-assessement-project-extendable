package com.vp.roomaddons.dao;

import android.media.audiofx.BassBoost;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vp.roomaddons.entity.Movie;

import java.util.List;


@Dao
public interface MovieDao {

    @Insert
    Long insertFavoriteMovie(Movie movieDetail);


    @Query("SELECT * FROM favorite_movie_table")
    LiveData<List<Movie>> fetchAllFavoriteMovie();


    @Query("SELECT * FROM favorite_movie_table WHERE imdbID =:imdbID")
    LiveData<Movie> getFavoriteMovie(int imdbID);


    @Update
    void updateFavoriteMovie(Movie movie);


    @Delete
    void deleteFavoriteMovie(Movie movie);

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_movie_table WHERE imdbID = :imdbID)")
    boolean favoriteMovieExist(String imdbID);
}