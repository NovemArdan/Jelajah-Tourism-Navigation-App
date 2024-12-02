package com.example.jelajah3.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.jelajah3.databinding.FragmentPlaceDetailBinding
import com.example.jelajah3.model.Place

class PlaceDetailFragment : Fragment() {
    private var _binding: FragmentPlaceDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPlaceDetailBinding.inflate(inflater, container, false)
        val place = PlaceDetailFragmentArgs.fromBundle(requireArguments()).place
        updateUI(place)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
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
