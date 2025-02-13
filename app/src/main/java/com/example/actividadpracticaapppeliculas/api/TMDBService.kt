package com.example.actividadpracticaapppeliculas.api

import com.example.actividadpracticaapppeliculas.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TMDBService {
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "es-ES",
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>


    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "es-ES",
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>


    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "es-ES",
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>


    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String = "es-ES",
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>
}