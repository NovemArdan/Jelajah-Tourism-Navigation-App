package com.example.jelajah3.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jelajah3.databinding.FragmentHomeBinding
import com.example.jelajah3.model.Place
import com.example.jelajah3.ui.adapter.PlacesAdapter



class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observePlaces()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            findNavController().navigate(action)
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        // Do not initialize the adapter here with an empty list.
    }

    private fun observePlaces() {
        viewModel.places.observe(viewLifecycleOwner) { places ->
            if (binding.recyclerView.adapter == null) {
                // Correctly initializing the adapter
                binding.recyclerView.adapter = PlacesAdapter(places, requireContext()) { place, allPlaces ->
                    // Make sure both place and allPlaces are correctly used
                    val action = HomeFragmentDirections.actionHomeFragmentToPlaceDetailFragment(place, allPlaces.toTypedArray())
                    findNavController().navigate(action)
                }
            } else {
                // Update the existing adapter's places
                (binding.recyclerView.adapter as PlacesAdapter).updatePlaces(places)
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


