package com.vp.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.vp.detail.databinding.ActivityDetailBinding
import com.vp.detail.viewmodel.DetailsViewModel
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import javax.inject.Inject


class DetailActivity : DaggerAppCompatActivity(), QueryProvider {


    private var favoriteMovieItem: MenuItem? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var detailViewModel: DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        detailViewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = detailViewModel
        queryProvider = this
        binding.setLifecycleOwner(this)
        detailViewModel.fetchDetails()
        detailViewModel.movieDetailObserver().observe(this, Observer {
            supportActionBar?.title = it.title
            updateFavoriteMovieIcon()
        })
    }

    private fun updateFavoriteMovieIcon() {
        val iconId: Int = if (detailViewModel.isFavoriteMovieExist()) {
            R.drawable.ic_favorite_selected
        } else {
            R.drawable.ic_favorite_unselected
        }
        favoriteMovieItem?.icon = ContextCompat.getDrawable(this, iconId)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        favoriteMovieItem = menu.findItem(R.id.star)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.star -> {
                val message :String = if (detailViewModel.isFavoriteMovieExist()) {
                    detailViewModel.deleteFavoriteMovie()
                    "This movie was deleted with success from your favorite movie list!"
                } else {
                    detailViewModel.addFavoriteMovie()
                    "This movie was added with success to your favorite movie list!"
                }
                showMessageFavoriteMovie(message)
                updateFavoriteMovieIcon()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getMovieId(): String {
        return intent?.getStringExtra("imdbID") ?: run {
            throw IllegalStateException("You must provide movie id to display details")
        }
    }

    companion object {
        lateinit var queryProvider: QueryProvider
    }

    private fun showMessageFavoriteMovie(message: String) {
        val snackBar: Snackbar = Snackbar
                .make(detail_container, message, Snackbar.LENGTH_LONG)
                .setAction("Dismiss") {
                }
        snackBar.show()
    }
}
