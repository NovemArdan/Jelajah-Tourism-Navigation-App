package com.example.jelajah3.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.jelajah3.databinding.FragmentPlaceDetailBinding
import com.example.jelajah3.model.Place

class PlaceDetailFragment : Fragment() {
    private var _binding: FragmentPlaceDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)

        // Access the 'selectedPlace' argument correctly
        val place = PlaceDetailFragmentArgs.fromBundle(requireArguments()).selectedPlace
        updateUI(place)

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(place: Place) {
        with(binding) {
            collapsingToolbar.title = place.name
            Glide.with(imgPlaceDetail.context).load(place.photoUrl).into(imgPlaceDetail)
            tvName.text = place.name
            tvCategoryRating.text = "${place.category} - ★ ${place.rating}"
            tvLocation.text = "Lokasi GPS: ${place.location.latitude}, ${place.location.longitude}"
            tvDescription.text = place.description
            btnFindRoute.setOnClickListener {
                val action = PlaceDetailFragmentDirections.actionPlaceDetailFragmentToMapFragment(
                    latitude = place.location.latitude.toFloat(),
                    longitude = place.location.longitude.toFloat()
                )
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

