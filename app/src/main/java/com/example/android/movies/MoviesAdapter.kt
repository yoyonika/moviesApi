package com.example.android.movies

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_layout.view.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    private val adapterMoviesList: MutableList<Movie> = mutableListOf()
    private var IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val from = LayoutInflater.from(parent.context)
        return MoviesViewHolder(from.inflate(R.layout.movie_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return adapterMoviesList.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindModel(adapterMoviesList[position])
    }

    fun addMovie (movieApi: Movie) {
        adapterMoviesList.add(movieApi)
        notifyDataSetChanged()
    }

    fun addMovies (movies: Collection<Movie>){
        adapterMoviesList.addAll(movies)
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieDescription: TextView = itemView.movieOverview
        private val movieScore: TextView = itemView.movieRating
        private val movieHeading: TextView = itemView.movieTitle
        private var moviePoster: ImageView= itemView.image

        fun bindModel(movie: Movie){
            movieHeading.text = movie.title
            movieDescription.text = movie.overview
            movieScore.text = movie.vote_average.toString()

            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.poster_path)
                    .into(moviePoster)
        }
    }


}