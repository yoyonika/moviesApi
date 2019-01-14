package com.example.android.movies

import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.android.movies.MoviesDescriptionActivity.Companion.KEY_TITLE
import com.example.android.movies.MoviesDescriptionActivity.Companion.KEY_DESCRIPTION
import kotlinx.android.synthetic.main.movie_layout.view.*

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    val adapterMoviesList: MutableList<Movie> = mutableListOf()
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
        private var moviePoster: ImageView = itemView.image
        private var cardView: CardView = itemView.card

        fun bindModel(movie: Movie) {
            movieHeading.text = movie.title
            movieDescription.text = movie.overview
            movieScore.text = movie.vote_average.toString()

            Glide.with(itemView)
                    .load(IMAGE_BASE_URL + movie.poster_path)
                    .into(moviePoster)

       cardView.setOnClickListener {view ->
           val intent = Intent(view.context, MoviesDescriptionActivity::class.java)

           intent.putExtra(KEY_TITLE, movie.title)
           intent.putExtra(KEY_DESCRIPTION, movie.overview)
           view.context.startActivity(intent)
       }
        }


        //make an interface
        //call that interface in the adapter
        //come back to the setonclick listener to call that interface so that you can get access to the description


//        fun bind(item: ViewItem, pos: Int, listener: (Int) -> Unit) = with(itemView) {
//            val cvItem = findViewById<CardView>(R.id.cv_item)
//            image.setOnClickListener {
//                listener(pos)
//            }
        //}}
    }
}