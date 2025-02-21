package com.example.actividadpracticaapppeliculas.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadpracticaapppeliculas.MovieDetailActivity
import com.example.actividadpracticaapppeliculas.databinding.ItemMovieBinding
import com.example.actividadpracticaapppeliculas.model.Movie

class MoviesAdapter(private var movies: List<Movie>): RecyclerView.Adapter<MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, MovieDetailActivity::class.java)
            intent.putExtra("movie", movie)
            context.startActivity(intent)
        }
    }

        override fun getItemCount(): Int = movies.size


        //actualizar lista de peliculas
        fun updateMovies(newMovies: List<Movie>) {
            movies = newMovies
            notifyDataSetChanged()

        }


    }
