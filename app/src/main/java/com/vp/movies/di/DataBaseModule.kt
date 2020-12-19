package com.vp.movies.di

import android.app.Application
import androidx.room.Room
import com.vp.roomaddons.MovieDatabase
import com.vp.roomaddons.dao.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataBaseModule {

    private val mDBName = "movie_database.db"

    @Singleton
    @Provides
    fun providesMovieDatabase(application: Application): MovieDatabase {
       return Room.databaseBuilder(application, MovieDatabase::class.java, mDBName).fallbackToDestructiveMigration().build()
    }


    @Singleton
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao {
        return movieDatabase.MovieDao()
    }

}