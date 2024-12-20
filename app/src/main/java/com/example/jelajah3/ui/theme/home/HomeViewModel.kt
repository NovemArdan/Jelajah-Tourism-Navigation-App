package com.example.jelajah3.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jelajah3.model.Place
import com.example.jelajah3.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _places = MutableLiveData<List<Place>>()
    val places: LiveData<List<Place>> = _places

    init {
        loadPlaces()
    }

    private fun loadPlaces() {
        RetrofitClient.jelajahApiService.getAllPlaces().enqueue(object : Callback<Map<String, Place>> {
            override fun onResponse(call: Call<Map<String, Place>>, response: Response<Map<String, Place>>) {
                if (response.isSuccessful) {
                    // Convert the Map to a List of Place objects
                    _places.postValue(response.body()?.values?.toList() ?: listOf())
                } else {
                    // Handle the case where the response is not successful
                    _places.postValue(listOf())
                }
            }

            override fun onFailure(call: Call<Map<String, Place>>, t: Throwable) {
                // Log error or handle the failure of the call
                _places.postValue(listOf())
            }
        })
    }
}