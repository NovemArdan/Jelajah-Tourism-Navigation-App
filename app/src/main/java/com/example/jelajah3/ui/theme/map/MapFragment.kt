package com.example.jelajah3.ui.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jelajah3.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val latitude = arguments?.getFloat("latitude") ?: 0f // Ensure these are floats if defined so in nav_graph
        val longitude = arguments?.getFloat("longitude") ?: 0f
        displayGpsLocation(latitude, longitude)
        return binding.root
    }

    private fun displayGpsLocation(latitude: Float, longitude: Float) {
        // Display GPS coordinates in TextView
        binding.tvGpsLocation.text = "GPS Coordinates: Lat = $latitude, Lon = $longitude"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
