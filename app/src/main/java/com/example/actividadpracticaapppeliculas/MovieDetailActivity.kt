package com.example.actividadpracticaapppeliculas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.actividadpracticaapppeliculas.database.AppDataBase
import com.example.actividadpracticaapppeliculas.databinding.MovieDetailBinding
import com.example.actividadpracticaapppeliculas.model.FavoriteMovie
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
            movie?.let { m ->
                // Crea una instancia de FavoriteMovie a partir del objeto Movie
                val favMovie = FavoriteMovie(
                    id = m.id,
                    title = m.title,
                    overview = m.overview,
                    posterPath = m.posterPath,
                    releaseDate = m.releaseDate,
                    voteAverage = m.voteAverage
                )
                if (!isFavorite) {
                    // Si no está marcado, lo añadimos a favoritos
                    favoritesViewModel.addFavorite(favMovie)
                    isFavorite = true
                } else {
                    // Si ya está, lo eliminamos de favoritos
                    favoritesViewModel.removeFavorite(favMovie)
                    isFavorite = false
                }
                updateFavoriteIcon()
                val message = if (isFavorite) "${m.title} añadido a favoritos"
                else "${m.title} eliminado de favoritos"
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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