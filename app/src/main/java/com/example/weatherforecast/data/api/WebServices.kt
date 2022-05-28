package com.example.weatherforecast.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {
   @GET("data/2.5/onecall")
   fun getWeather(
       @Query("lat") latitude: Int,
       @Query("lon") longitude:Int,
       @Query("appid") apiKey:String,
       @Query("units")units:String="metric"
   ) : Call<Response>
}