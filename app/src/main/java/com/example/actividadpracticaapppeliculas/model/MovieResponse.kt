package com.example.actividadpracticaapppeliculas.model

import android.graphics.Movie
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
): Serializable


data class Movie(
    val id: Int,
    val title: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("original_title") val originalTitle: String,
    val overview: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,



): Serializable