package com.example.android.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var apiMovies: ApiMoviesInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesAdapter = MoviesAdapter()
        movies_list.adapter = moviesAdapter

        search_button.setOnClickListener {
            beginSearch(searchView.text.toString())
        }

        swipe_refresh.setOnRefreshListener {
            swipe_refresh.isRefreshing = false
            addMoviesApi()
            Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
            //logic to get the data again
            // clear recyc
            // make new api call
            // update recyc
        }

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        apiMovies = retrofit.create(ApiMoviesInterface::class.java)
        addMoviesApi()
    }

    private fun addMoviesApi() {
        apiMovies.getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    moviesAdapter.addMovies(it.results)
                }, {
                    it.printStackTrace()
                })
    }

    private fun beginSearch (search: String) {
        val disposable = apiMovies.searchMovies(search)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    moviesAdapter.addMovies(it.results)
                }, {it.printStackTrace()
                })

    }

    //start details activity - first
}






