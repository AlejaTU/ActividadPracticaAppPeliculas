package com.example.actividadpracticaapppeliculas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actividadpracticaapppeliculas.adapter.FavoritesAdapter
import com.example.actividadpracticaapppeliculas.adapter.MoviesAdapter
import com.example.actividadpracticaapppeliculas.api.TMDBService
import com.example.actividadpracticaapppeliculas.database.AppDataBase
import com.example.actividadpracticaapppeliculas.databinding.FavoriteListBinding
import com.example.actividadpracticaapppeliculas.repository.FavoritesRepository
import com.example.actividadpracticaapppeliculas.repository.MovieRepository
import com.example.actividadpracticaapppeliculas.viewmodel.FavoritesViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoritesActivity: AppCompatActivity() {


    private lateinit var binding: FavoriteListBinding
    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var adapter: FavoritesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FavoriteListBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.tbmovielist)


        //configurar recycler view
        adapter = FavoritesAdapter(emptyList())
        binding.rvmovielist.layoutManager = LinearLayoutManager(this)
        binding.rvmovielist.adapter = adapter


        // obtenemos la instancia de la base de datos y el DAO de favoritos.
        val database = AppDataBase.getDatabase(applicationContext)
        val favoritesRepository = FavoritesRepository(database.favoriteMovieDao())


        //viewmodel
        favoritesViewModel = ViewModelProvider(this, FavoritesViewModelFactory(favoritesRepository))
            .get(FavoritesViewModel::class.java)


        //observar livedata par ala lista de fav

        favoritesViewModel.favorites.observe(this, Observer { favoriteMovies ->
            adapter.updateFavorites(favoriteMovies)
        })

    }
}