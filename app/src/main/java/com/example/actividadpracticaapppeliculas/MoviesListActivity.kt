package com.example.actividadpracticaapppeliculas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.example.actividadpracticaapppeliculas.model.Movie

import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actividadpracticaapppeliculas.adapter.MoviesAdapter
import com.example.actividadpracticaapppeliculas.api.TMDBService
import com.example.actividadpracticaapppeliculas.databinding.MovieListBinding
import com.example.actividadpracticaapppeliculas.repository.MovieRepository
import com.example.actividadpracticaapppeliculas.viewmodel.MovieCategory
import com.example.actividadpracticaapppeliculas.viewmodel.MoviesViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesListActivity: AppCompatActivity() {

    private lateinit var  binding: MovieListBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var adapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //inflar el binding
        binding = MovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //toolbar
        setSupportActionBar(binding.tbmovielist)



        //configurar recycler view
        adapter = MoviesAdapter(emptyList())
        binding.rvmovielist.layoutManager = LinearLayoutManager(this)
        binding.rvmovielist.adapter = adapter


        //configurar retrofitB
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //creamos el serivicio de tmdb y repositorio encapsula las llamadas a la api
        val service = retrofit.create(TMDBService::class.java)
        //reemplazamos la api key
        val repository = MovieRepository(service, apiKey = "256d139b296656fa3680bdf9635e2ca4")

//creamos el movieviewmodel
        viewModel = ViewModelProvider(this, object: ViewModelProvider.Factory{
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MoviesViewModel(repository) as T
            }
        }).get(MoviesViewModel::class.java)

        //observamos el livedata de peliculas
        viewModel.movies.observe(this, Observer {
            movies ->
            adapter.updateMovies(movies)
        })

        viewModel.loadMovies(MovieCategory.UPCOMING)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_movies, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val category = when (item.itemId) {
            R.id.menu_upcoming -> {
                supportActionBar?.title = "Próximamente"
                MovieCategory.UPCOMING
            }
            R.id.menu_popular -> {
                supportActionBar?.title = "Populares"
                MovieCategory.POPULAR
            }
            R.id.menu_top_rated -> {
                supportActionBar?.title = "Mejor Valoradas"
                MovieCategory.TOP_RATED
            }
            R.id.menu_now_playing -> {
                supportActionBar?.title = "En Cartelera"
                MovieCategory.NOW_PLAYING
            }
            R.id.menu_favorites -> {
                startActivity(Intent(this, FavoritesActivity::class.java))
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
        viewModel.loadMovies(category as MovieCategory)
        return true
    }



}