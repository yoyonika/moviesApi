package com.example.android.movies

import io.reactivex.Observable
import retrofit2.http.GET

interface ApiMoviesInterface {

    @GET("/3/discover/movie?api_key=b81549e79fa118bb8ddc65ab7c9bf578")
    fun getMovies() : Observable <MoviesResponse>
}