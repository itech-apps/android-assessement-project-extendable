package com.vp.favorites.ui.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vp.favorites.Item
import com.vp.favorites.R
import com.vp.favorites.adapter.ListAdapter
import com.vp.favorites.viewmodel.ListFavoriteMovieViewModel
import com.vp.roomaddons.entity.Movie
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ListFavoriteMovieFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private var listAdapter: ListAdapter? = null
    private var recyclerView: RecyclerView? = null

    lateinit var favoriteMovieViewModel: ListFavoriteMovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        favoriteMovieViewModel = ViewModelProviders.of(this, factory).get(ListFavoriteMovieViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_movie_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)

        initList()

        favoriteMovieViewModel.getFavoriteMovies().observe(this, Observer {  listFavoriteMovie ->
            val listItem = mutableListOf<Item>()
            listFavoriteMovie.forEach {
                listItem.add(Item(it.title, it.year, it.imdbID, it.poster))
            }
            listAdapter?.setItems(listItem)
        })
    }

    private fun initList() {
        listAdapter = ListAdapter()
        recyclerView!!.adapter = listAdapter
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = GridLayoutManager(context,
                if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 2 else 3)
        recyclerView!!.layoutManager = layoutManager
    }

    companion object {
        const val TAG = "ListFavoriteMovieFragment"
        private const val CURRENT_QUERY = "current_query"
    }
}