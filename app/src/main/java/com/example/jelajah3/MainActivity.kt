package com.example.jelajah3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        // Mengirim username ke HomeFragment
        val username = intent.getStringExtra("username") ?: ""
        val bundle = Bundle().apply {
            putString("username", username)
        }
        navController.setGraph(R.navigation.nav_graph, bundle)
    }
}
