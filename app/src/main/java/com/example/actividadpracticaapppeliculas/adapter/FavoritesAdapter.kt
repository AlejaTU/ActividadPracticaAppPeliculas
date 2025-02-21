package com.example.actividadpracticaapppeliculas.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.actividadpracticaapppeliculas.databinding.ItemMovieBinding
import com.example.actividadpracticaapppeliculas.model.FavoriteMovie
import com.squareup.picasso.Picasso

class FavoritesAdapter(private var favorites: List<FavoriteMovie>) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FavoriteMovie) {
            binding.tvTitle.text = movie.title
            // Si deseas mostrar la imagen del póster
            Picasso.get()
                .load("https://image.tmdb.org/t/p/w500${movie.posterPath}")
                .into(binding.ivPoster)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

    override fun getItemCount(): Int = favorites.size

    // Método para actualizar la lista de favoritos
    fun updateFavorites(newFavorites: List<FavoriteMovie>) {
        favorites = newFavorites
        notifyDataSetChanged()
    }
}