package com.example.actividadpracticaapppeliculas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.actividadpracticaapppeliculas.repository.FavoritesRepository
import com.example.actividadpracticaapppeliculas.viewmodel.FavoritesViewModel

class FavoritesViewModelFactory(private val repository: FavoritesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}