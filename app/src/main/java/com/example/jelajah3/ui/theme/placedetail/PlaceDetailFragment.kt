package com.example.jelajah3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.jelajah3.databinding.FragmentPlaceDetailBinding
import com.example.jelajah3.model.Place
import com.example.jelajah3.viewmodel.PlaceDetailViewModel

class PlaceDetailFragment : Fragment() {
    private var _binding: FragmentPlaceDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PlaceDetailViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.place.observe(viewLifecycleOwner) { place ->
            if (place != null) {
                updateUI(place)
            }
        }
    }

    private fun updateUI(place: Place) {
        with(binding) {
            collapsingToolbar.title = place.name
            Glide.with(imgPlaceDetail.context).load(place.photoUrl).into(imgPlaceDetail)
            tvDescription.text = place.description
            tvCategory.text = place.category
            tvLocation.text = "Location: ${place.location.latitude}, ${place.location.longitude}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
