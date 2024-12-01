package com.example.jelajah3.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SignupViewModel : ViewModel() {
    private val _signupResult = MutableLiveData<Boolean>()
    val signupResult: LiveData<Boolean> = _signupResult

    fun signup(email: String, password: String) {
        // Here you would put your signup logic
        // For now, let's simulate a successful signup
        _signupResult.value = true  // Simulate signup success
        // _signupResult.value = false // Uncomment to simulate failure
    }
}
