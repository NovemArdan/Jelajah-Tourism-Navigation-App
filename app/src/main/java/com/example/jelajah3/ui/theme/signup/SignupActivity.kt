package com.example.jelajah3.ui.theme.signup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jelajah3.databinding.ActivitySignupBinding


class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignUp.setOnClickListener {
            // Handle sign up logic here
        }
    }
}