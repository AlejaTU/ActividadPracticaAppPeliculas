package com.example.actividadpracticaapppeliculas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.actividadpracticaapppeliculas.database.AppDataBase
import com.example.actividadpracticaapppeliculas.databinding.MovieDetailBinding
import com.example.actividadpracticaapppeliculas.model.Movie
import com.example.actividadpracticaapppeliculas.repository.FavoritesRepository
import com.example.actividadpracticaapppeliculas.viewmodel.FavoritesViewModel
import com.squareup.picasso.Picasso

class MovieDetailActivity: AppCompatActivity() {

    private lateinit var binding: MovieDetailBinding
    private var isFavorite: Boolean = false
    private lateinit var favoritesViewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //recuperar el obejto moview que pasaron con el intent
        val movie = intent.getSerializableExtra("movie") as? Movie

    if(movie!=null) {
        //actualizar la vista con los detalles
        binding.tvTitle.text = movie.title
        binding.tvOriginalTitleURL.text = movie.originalTitle
        binding.tvOverviewURL.text = movie.overview
        binding.tvReleaseDate.text = "Estreno: ${movie.releaseDate}"
        binding.tvVoteAverage.text = "Valoracion: ${movie.voteAverage}"
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500/${movie.posterPath}")
            .into(binding.ivPoster)


    } else {
        Toast.makeText(this, "No se  encontro informacion de la pelicula", Toast.LENGTH_SHORT).show()
        finish()
    }

        // Inicializa el repository de favoritos usando tu base de datos
        val database = AppDataBase.getDatabase(applicationContext)
        val favoritesRepository = FavoritesRepository(database.favoriteMovieDao())
        favoritesViewModel = ViewModelProvider(this, FavoritesViewModelFactory(favoritesRepository))
            .get(FavoritesViewModel::class.java)

        //configurar boton para anadir favoritos


        binding.btnFavorites.setOnClickListener {

                if (isFavorite) {
                    // Si ya está marcado, lo eliminamos de favoritos
                    Toast.makeText(this, "Esta en favoritos", Toast.LENGTH_SHORT).show()

                } else {
                    // Si no está marcado, lo añadimos a favoritos
                  updateFavoriteIcon()
                    //anadir a room
                    if (movie != null) {
                        favoritesViewModel.addFavorite(movie)
                    }
                }


        }





    }
    private fun updateFavoriteIcon() {
        if (isFavorite) {
            binding.ivFavoriteStar.setImageResource(R.drawable.ic_estrella_llena)
        } else {
            binding.ivFavoriteStar.setImageResource(R.drawable.ic_estrella_vacia)
        }


    }}