package com.vp.favorites.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vp.roomaddons.dao.MovieDao
import com.vp.roomaddons.entity.Movie
import kotlinx.coroutines.*
import javax.inject.Inject

class ListFavoriteMovieViewModel @Inject constructor(private val movieDao: MovieDao) : ViewModel() {

    @SuppressLint("LongLogTag")
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        Log.e("Exception", "Exception", error)
    }


    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + errorHandler)

    fun addFavoriteMovie(favoriteMovie: Movie) {
        coroutineScope.launch {
            movieDao.insertFavoriteMovie(favoriteMovie)
        }
    }

    fun getFavoriteMovies(): LiveData<List<Movie>> {
        return movieDao.fetchAllFavoriteMovie()
    }

    override fun onCleared() {
        super.onCleared()
        coroutineScope.coroutineContext.cancel()
    }
}