package com.example.actividadpracticaapppeliculas

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.actividadpracticaapppeliculas.databinding.MovieDetailBinding
import com.example.actividadpracticaapppeliculas.model.Movie
import com.squareup.picasso.Picasso

class MovieDetailActivity: AppCompatActivity() {

    private lateinit var binding: MovieDetailBinding

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

        //configurar boton para anadir favoritos

        Toast.makeText(this, "${movie?.title} añadido a favoritos", Toast.LENGTH_SHORT).show()


    }

}