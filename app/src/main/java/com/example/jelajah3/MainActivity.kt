package com.example.jelajah3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        val navController = findNavController(this, R.id.nav_host_fragment)


        // Setting up the BottomNavigationView with NavController
        setupWithNavController(bottomNavigationView, navController)

//        val navController = findNavController(R.id.nav_host_fragment)
//        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)
    }
}
