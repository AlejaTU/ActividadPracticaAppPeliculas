package com.example.actividadpracticaapppeliculas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadpracticaapppeliculas.databinding.ItemMovieBinding
import com.example.actividadpracticaapppeliculas.model.Movie

class MoviesAdapter(private var movies: List<Movie>): RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size


    //actualizar lista de peliculas
    fun updateMovies(newMovies: List<Movie>){
        movies = newMovies
        notifyDataSetChanged()

    }




}