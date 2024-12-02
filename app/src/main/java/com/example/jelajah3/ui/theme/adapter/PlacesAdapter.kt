package com.example.jelajah3.ui.adapter

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jelajah3.databinding.ItemPlaceBinding
import com.example.jelajah3.model.Place
import java.io.IOException
import java.util.*

class PlacesAdapter(private var places: List<Place>, private val context: Context) : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place, context: Context) {
            with(binding) {
                textViewName.text = place.name
                textViewRating.text = "Rating: ${place.rating}"

                // Get the location details asynchronously or use a previously fetched value
                val locationDetails = getAddressFromLocation(place.location.latitude, place.location.longitude, context)
                textViewLocation.text = "${locationDetails.first}, ${locationDetails.second}"

                Glide.with(imagePlace.context).load(place.photoUrl).into(imagePlace)
            }
        }

        private fun getAddressFromLocation(latitude: Double, longitude: Double, context: Context): Pair<String?, String?> {
            val geocoder = Geocoder(context, Locale.getDefault())
            return try {
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                if (addresses?.isNotEmpty() == true) {
                    val address = addresses.first()
                    val city = address.locality  // City
                    val kecamatan = address.subAdminArea  // Sub-district
                    Pair(city, kecamatan)
                } else {
                    Pair(null, null)
                }
            } catch (exception: Exception) {
                // Log error and return nulls
                Pair(null, null)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(places[position], context)
    }

    override fun getItemCount(): Int = places.size

    fun updatePlaces(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }
}
