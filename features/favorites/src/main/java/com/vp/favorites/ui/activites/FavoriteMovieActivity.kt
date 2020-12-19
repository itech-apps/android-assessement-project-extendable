package com.vp.favorites.ui.activites

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.vp.favorites.R
import com.vp.favorites.ui.fragments.ListFavoriteMovieFragment
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class FavoriteMovieActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorite_movie_activity)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainer, ListFavoriteMovieFragment(), ListFavoriteMovieFragment.TAG)
                .commit()

    }
}