package com.example.jelajah3.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    fun login(email: String, password: String) {

        if (email == "example@example.com" && password == "password") {
            _loginResult.value = true
        } else {
            _loginResult.value = false
        }
    }
}
