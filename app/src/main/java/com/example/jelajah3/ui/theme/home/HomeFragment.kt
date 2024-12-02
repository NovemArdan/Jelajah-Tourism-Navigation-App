package com.example.jelajah3.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jelajah3.databinding.FragmentHomeBinding
import com.example.jelajah3.ui.adapter.PlacesAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observePlaces()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PlacesAdapter(emptyList())
        }
    }

    private fun observePlaces() {
        viewModel.places.observe(viewLifecycleOwner) { places ->
            (binding.recyclerView.adapter as PlacesAdapter).updatePlaces(places)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
