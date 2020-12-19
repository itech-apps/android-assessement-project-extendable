package com.vp.detail.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vp.detail.DetailActivity
import com.vp.detail.model.MovieDetail
import com.vp.detail.service.DetailService
import com.vp.roomaddons.dao.MovieDao
import com.vp.roomaddons.entity.Movie
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

class DetailsViewModel @Inject constructor(private val detailService: DetailService, private val movieDao: MovieDao) : ViewModel() {
    private val details: MutableLiveData<MovieDetail> = MutableLiveData()
    private val loadingState: MutableLiveData<LoadingState> = MutableLiveData()
    private var movieDetail : MovieDetail? = null
    fun movieDetailObserver(): LiveData<MovieDetail> = details

    fun details(): LiveData<MovieDetail> = details

    fun state(): LiveData<LoadingState> = loadingState

    fun fetchDetails() {
        loadingState.value = LoadingState.IN_PROGRESS
        detailService.getMovie(DetailActivity.queryProvider.getMovieId()).enqueue(object : Callback, retrofit2.Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>?, response: Response<MovieDetail>?) {

                movieDetail = response?.body()

                details.postValue(response?.body())

                response?.body()?.let {
                    details.postValue(it)
                }

                loadingState.value = LoadingState.LOADED
            }

            override fun onFailure(call: Call<MovieDetail>?, t: Throwable?) {
                details.postValue(null)
                loadingState.value = LoadingState.ERROR
            }
        })
    }

    @SuppressLint("LongLogTag")
    private val errorHandler = CoroutineExceptionHandler { _, error ->
        Log.e("Exception", "Exception", error)
    }


    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + errorHandler)

    fun addFavoriteMovie() {
        coroutineScope.launch {
            movieDetail?.apply {
                val favoriteMovie = Movie(imdbID, title, year, runtime, director, plot, poster)
                movieDao.insertFavoriteMovie(favoriteMovie)
            }
        }
    }

    fun deleteFavoriteMovie(){
        coroutineScope.launch {
            movieDetail?.apply {
                val favoriteMovie = Movie(imdbID, title, year, runtime, director, plot, poster)
                movieDao.deleteFavoriteMovie(favoriteMovie)
            }
        }
    }

    fun isFavoriteMovieExist(): Boolean {
        return runBlocking(Dispatchers.IO) {
            movieDao.favoriteMovieExist(movieDetail?.imdbID)
        }
    }

    enum class LoadingState {
        IN_PROGRESS, LOADED, ERROR
    }
}