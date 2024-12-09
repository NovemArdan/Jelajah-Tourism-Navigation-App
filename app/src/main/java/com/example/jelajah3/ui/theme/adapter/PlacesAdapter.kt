package com.example.jelajah3.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jelajah3.databinding.ItemPlaceBinding
import com.example.jelajah3.model.Place
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class PlacesAdapter(
    private var places: List<Place>,
    private val context: Context,
    private val onPlaceClicked: (Place, List<Place>) -> Unit
) : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(val binding: ItemPlaceBinding, val onPlaceClicked: (Place, List<Place>) -> Unit, val context: Context) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(place: Place, allPlaces: List<Place>) {
            with(binding) {
                textViewName.text = place.name
                textViewRating.text = "Rating: ${place.rating}"
                Glide.with(imagePlace.context).load(place.photoUrl).into(imagePlace)
                root.setOnClickListener { onPlaceClicked(place, allPlaces) }

                fetchAndDisplayLocation(place.location.latitude, place.location.longitude)
            }
        }

        @SuppressLint("SetTextI18n")
        private fun fetchAndDisplayLocation(latitude: Double, longitude: Double) {
            CoroutineScope(Dispatchers.IO).launch {
                val locationDetails = getAddressFromLocation(latitude, longitude)
                withContext(Dispatchers.Main) {
                    binding.textViewLocation.text = "${locationDetails.first}, ${locationDetails.second}"
                }
            }
        }

        private fun getAddressFromLocation(latitude: Double, longitude: Double): Pair<String?, String?> {
            val geocoder = Geocoder(context, Locale.getDefault())
            return try {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses?.isNotEmpty() == true) {
                    val address = addresses[0]
                    val city = address.locality // kota
                    val subDistrict = address.subAdminArea // kecamatan
                    Pair(city, subDistrict)
                } else {
                    Pair(null, null)
                }
            } catch (e: Exception) {
                Pair(null, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding, onPlaceClicked, context)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(places[position], places)  // Here places is passed as allPlaces, which seems correct
    }

    override fun getItemCount(): Int = places.size

    @SuppressLint("NotifyDataSetChanged")
    fun updatePlaces(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }
}

