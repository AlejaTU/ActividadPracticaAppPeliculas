package com.example.actividadpracticaapppeliculas.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.actividadpracticaapppeliculas.databinding.ItemMovieBinding
import com.example.actividadpracticaapppeliculas.model.Movie
import com.squareup.picasso.Picasso

class MovieViewHolder(private val binding: ItemMovieBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        binding.tvTitle.text = movie.title
        Picasso.get()
            .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
            .into(binding.ivPoster
            )

    }
}