package com.example.actividadpracticaapppeliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividadpracticaapppeliculas.model.FavoriteMovie
import com.example.actividadpracticaapppeliculas.model.Movie
import com.example.actividadpracticaapppeliculas.repository.FavoritesRepository
import kotlinx.coroutines.launch

// hacemos el view model para la lista de favoritos

class FavoritesViewModel(private val repository: FavoritesRepository) : ViewModel() {

    val favorites: LiveData<List<FavoriteMovie>> = repository.getAllFavorites()

    fun addFavorite(movie: FavoriteMovie) {
        viewModelScope.launch { repository.addFavorite(movie) }
    }

    fun removeFavorite(movie: FavoriteMovie) {
        viewModelScope.launch { repository.removeFavorite(movie) }
    }
}