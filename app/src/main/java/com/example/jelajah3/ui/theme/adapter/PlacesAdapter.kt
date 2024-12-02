package com.example.jelajah3.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jelajah3.databinding.ItemPlaceBinding
import com.example.jelajah3.model.Place

class PlacesAdapter(private var places: List<Place>) : RecyclerView.Adapter<PlacesAdapter.PlaceViewHolder>() {

    class PlaceViewHolder(val binding: ItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place) {
            with(binding) {
                textViewName.text = place.name
                textViewRating.text = "Rating: ${place.rating}"
                textViewLocation.text = "Lat: ${place.location.latitude}, Lon: ${place.location.longitude}"
                Glide.with(imagePlace.context).load(place.photoUrl).into(imagePlace)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val binding = ItemPlaceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        holder.bind(places[position])
    }

    override fun getItemCount(): Int = places.size

    fun updatePlaces(newPlaces: List<Place>) {
        places = newPlaces
        notifyDataSetChanged()
    }
}
