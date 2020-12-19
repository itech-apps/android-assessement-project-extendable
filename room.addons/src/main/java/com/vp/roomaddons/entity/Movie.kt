package com.vp.roomaddons.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_movie_table")
data class Movie(
        @PrimaryKey
        @ColumnInfo(name = "imdbID")
        var imdbID: String,

        @ColumnInfo(name = "Title")
        var title: String,

        @ColumnInfo(name = "Year")
        var year: Int,

        @ColumnInfo(name = "Runtime")
        var runtime: String,

        @ColumnInfo(name = "Director")
        var director: String,

        @ColumnInfo(name = "Plot")
        var plot: String,

        @ColumnInfo(name = "Poster")
        var poster: String
)