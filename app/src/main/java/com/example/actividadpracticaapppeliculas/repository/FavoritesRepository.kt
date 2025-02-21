package com.example.actividadpracticaapppeliculas.repository

import androidx.lifecycle.LiveData
import com.example.actividadpracticaapppeliculas.dao.FavoriteMovieDAO
import com.example.actividadpracticaapppeliculas.model.FavoriteMovie

class FavoritesRepository(private val dao: FavoriteMovieDAO) {

    fun getAllFavorites(): LiveData<List<FavoriteMovie>> = dao.getAllFavorites()

    suspend fun addFavorite(movie: FavoriteMovie) {
        dao.insertFavorite(movie)
    }

    suspend fun removeFavorite(movie: FavoriteMovie) {
        dao.deleteFavorite(movie)
    }

    suspend fun isFavorite(movieId: Int): Boolean {
        return dao.isFavorite(movieId)
    }

}