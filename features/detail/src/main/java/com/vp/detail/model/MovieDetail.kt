package com.vp.detail.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(@SerializedName("Title") val title: String,
@SerializedName("imdbID") val imdbID: String,
@SerializedName("Year") val year: Int,
@SerializedName("Runtime") val runtime: String,
@SerializedName("Director") val director: String,
@SerializedName("Plot") val plot: String,
@SerializedName("Poster") val poster: String)