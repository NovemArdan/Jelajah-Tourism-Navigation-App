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
import com.example.jelajah3.ui.adapter.PlacesAdapter
//import com.example.jelajah3.ui.home.HomeViewModel
//import com.example.jelajah3.ui.home.HomeFragmentDirections


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
        binding.recyclerView.adapter = PlacesAdapter(listOf(), requireContext()) { place ->
            val action = HomeFragmentDirections.actionHomeFragmentToPlaceDetailFragment(place)
            findNavController().navigate(action)
        }
    }

    private fun observePlaces() {
        viewModel.places.observe(viewLifecycleOwner) { places ->
            (binding.recyclerView.adapter as PlacesAdapter).updatePlaces(places ?: listOf())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


