package com.vp.favorites.di

import com.vp.favorites.ui.fragments.ListFavoriteMovieFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ListFavoriteMovieFragmentModule {

    @ContributesAndroidInjector
    abstract fun bindListFavoriteMovieFragment(): ListFavoriteMovieFragment
}
