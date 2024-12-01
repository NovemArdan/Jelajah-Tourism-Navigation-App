package com.example.jelajah3.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    // Add LiveData to handle data changes and view state
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun login(email: String, password: String) {
        // Assuming a function that checks login credentials
        if (email == "user@example.com" && password == "password") {
            _loginResult.value = true
        } else {
            _loginResult.value = false
        }
    }
}
