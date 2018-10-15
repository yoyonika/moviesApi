package com.example.android.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movie_layout.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var moviesList: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesAdapter= MoviesAdapter()
        movies_list.adapter = moviesAdapter

        moviesList = findViewById(R.id.movies_list)

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        val apiMovies = retrofit.create(ApiMoviesInterface::class.java)
        apiMovies.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    moviesAdapter.setMovies(it.data)
                }, {
                Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                })
    }


    inner class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

        val movies: MutableList<Movie> = mutableListOf()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
            return MoviesViewHolder(layoutInflater.inflate(R.layout.movie_layout,parent,false))
        }

        override fun getItemCount(): Int {
            return movies.size
        }

        override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
            holder.bindModel(movies[position])
        }

        fun setMovies (data: List<Movie>) {
            movies.addAll(data)
            notifyDataSetChanged()
        }

        inner class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            private val movieHeading: TextView = movieTitle
            private val movieDescription: TextView = movieOverview
            private val movieScore: TextView = movieRating

            fun bindModel(movie: Movie){
                movieHeading.text = movie.title
                movieDescription.text = movie.overview
                movieScore.text = movie.vote_average.toString()
            }
        }
    }
}

