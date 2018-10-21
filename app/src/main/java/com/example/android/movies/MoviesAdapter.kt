package com.example.android.movies

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.movie_layout.view.*

class MoviesAdapter(private val mainActivity: MainActivity) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    val movies: MutableList<Movie> = mutableListOf()
    var data:MoviesResponse?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(mainActivity.layoutInflater.inflate(R.layout.movie_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        holder.bindModel(movies[position])
    }

    fun setMovies (data: MoviesResponse) {
        movies.addAll(data.data)
        notifyDataSetChanged()
    }

    inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movieHeading: TextView = itemView.movieTitle
        private val movieDescription: TextView = itemView.movieOverview
        private val movieScore: TextView = itemView.movieRating

        fun bindModel(movie: Movie){
            movieHeading.text = movie.title
            movieDescription.text = movie.overview
            movieScore.text = movie.vote_average.toString()
        }
    }
}

