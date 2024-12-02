package com.example.jelajah3.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jelajah3.model.Place

class PlaceDetailViewModel : ViewModel() {
    private val _place = MutableLiveData<Place>()
    val place: LiveData<Place> = _place

    fun setPlace(place: Place) {
        _place.value = place
    }
}
