package com.example.actividadpracticaapppeliculas.repository

import com.example.actividadpracticaapppeliculas.api.TMDBService
import com.example.actividadpracticaapppeliculas.model.MovieResponse
import retrofit2.Response

class MovieRepository(
    private val service: TMDBService,
    private val apiKey: String
) {

    suspend fun getUpcomingMovies(): Response<MovieResponse> =
        service.getUpcomingMovies(apiKey = apiKey)

    suspend fun getPopularMovies(): Response<MovieResponse> =
        service.getPopularMovies(apiKey = apiKey)

    suspend fun getTopRatedMovies(): Response<MovieResponse> =
        service.getTopRatedMovies(apiKey = apiKey)

    suspend fun getNowPlayingMovies(): Response<MovieResponse> =
        service.getNowPlayingMovies(apiKey = apiKey)

}