package com.example.moviemasalahub

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("/")   //here "/" is important
    fun getMovieData(
        @Query("apikey") apikey: String,
        @Query("t") moviename: String
    ): Call<MovieApp>
}