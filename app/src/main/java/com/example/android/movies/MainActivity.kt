package com.example.android.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var moviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moviesAdapter = MoviesAdapter()
        movies_list.adapter = moviesAdapter
        swipe_refresh.setOnRefreshListener {
                Toast.makeText(this, "Refreshed", Toast.LENGTH_SHORT).show()
                swipe_refresh.isRefreshing= false
        }

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
                    moviesAdapter.addMovies(it.results)
                }, {
                    it.printStackTrace()
                })
    }


}



