package com.example.android.movies

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiMoviesInterface {

    @GET("/3/discover/movie?api_key=b81549e79fa118bb8ddc65ab7c9bf578")
    fun getMovies() : Observable <MoviesResponse>

    @GET("/3/search/movie?api_key=b81549e79fa118bb8ddc65ab7c9bf578")
    fun searchMovies(@Query("query") title:String): Observable<MoviesResponse>
}
