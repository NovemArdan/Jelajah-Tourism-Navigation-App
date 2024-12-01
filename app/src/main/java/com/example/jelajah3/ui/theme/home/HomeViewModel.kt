package com.example.jelajah3.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _destinations = MutableLiveData<List<String>>().apply {
        value = listOf("Destination 1", "Destination 2", "Destination 3")  // Example destinations
    }
    val destinations: LiveData<List<String>> = _destinations

    // Add any additional logic for fetching data from a repository if needed
}
