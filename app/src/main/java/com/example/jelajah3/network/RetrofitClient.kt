package com.example.jelajah3.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val FIREBASE_BASE_URL = "https://restapijelajah-default-rtdb.firebaseio.com/"
    private const val GOOGLE_MAPS_BASE_URL = "https://maps.googleapis.com/"


    private val retrofitFirebase: Retrofit = Retrofit.Builder()
        .baseUrl(FIREBASE_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val jelajahApiService: JelajahApiService by lazy {
        retrofitFirebase.create(JelajahApiService::class.java)
    }


    private val retrofitGoogleMaps: Retrofit = Retrofit.Builder()
        .baseUrl(GOOGLE_MAPS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val directionsApiService: DirectionsApi by lazy {
        retrofitGoogleMaps.create(DirectionsApi::class.java)
    }
}
