package com.vp.favorites.di

import com.vp.favorites.ui.activites.FavoriteMovieActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FavoriteMovieActivityModule {
    @ContributesAndroidInjector(modules = [FavoriteMovieViewModelModule::class, ListFavoriteMovieFragmentModule::class])
    abstract fun bindFavoriteMovieActivity(): FavoriteMovieActivity
}