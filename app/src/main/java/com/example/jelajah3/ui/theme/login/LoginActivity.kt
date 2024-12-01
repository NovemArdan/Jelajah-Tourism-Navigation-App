package com.example.jelajah3.ui.theme.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.jelajah3.MainActivity
import com.example.jelajah3.databinding.ActivityLoginBinding
import com.example.jelajah3.ui.theme.signup.SignupActivity


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            // Simulate successful login (add actual login logic here)
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }
}