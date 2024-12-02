package com.example.jelajah3.network

import com.example.jelajah3.model.Place
import retrofit2.Call
import retrofit2.http.GET

interface JelajahApiService {
    @GET("jelajah.json")
    fun getAllPlaces(): Call<Map<String, Place>>
}
