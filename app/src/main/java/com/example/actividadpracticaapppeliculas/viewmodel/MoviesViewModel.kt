package com.example.actividadpracticaapppeliculas.viewmodel

import com.example.actividadpracticaapppeliculas.model.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.actividadpracticaapppeliculas.model.MovieResponse
import com.example.actividadpracticaapppeliculas.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response

//definir el enum para representar las opciones de cada categoria
enum class MovieCategory {
    UPCOMING, POPULAR, TOP_RATED, NOW_PLAYING
}


class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {

    //la clase extiende de view model para que sobreviva a cambios de configuracion y manejar el ciclo de vida
    //se inyecta el movie repository para poder acceder a los datos con las funciones que relizan las peticion de la api

    //crear la lista mutable para almacenar las pelis
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> get() = _movies

    //cargamos las peliculas segun la categoria seleccionada
    fun loadMovies(category: MovieCategory) {
        viewModelScope.launch {
            //dependendiendo de la categoria se llama a un metodo u otro
            val response: Response<MovieResponse> = when (category) {
                MovieCategory.UPCOMING -> repository.getUpcomingMovies()
                MovieCategory.POPULAR -> repository.getPopularMovies()
                MovieCategory.TOP_RATED -> repository.getTopRatedMovies()
                MovieCategory.NOW_PLAYING -> repository.getNowPlayingMovies()

            }

            //si la respuesta es exitosa se actualiza la lista de peliculas
            if (response.isSuccessful) {
                _movies.postValue(response.body()?.results ?: emptyList())
            } else {
                _movies.postValue(emptyList())
            }
        }


    }
}