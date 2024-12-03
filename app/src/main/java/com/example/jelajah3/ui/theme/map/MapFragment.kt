package com.example.jelajah3.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.jelajah3.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.example.jelajah3.databinding.FragmentMapBinding
import com.example.jelajah3.network.RetrofitClient
import com.example.jelajah3.model.DirectionsResponse
import com.google.maps.android.PolyUtil
import retrofit2.Call
import retrofit2.Response

class MapFragment : Fragment(), OnMapReadyCallback {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var destinationMarker: MarkerOptions? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setupDestinationMarker()
        enableMyLocation()
    }

    private fun setupDestinationMarker() {
        val destinationLat = arguments?.getFloat("latitude") ?: 0.0f
        val destinationLng = arguments?.getFloat("longitude") ?: 0.0f
        val destination = LatLng(destinationLat.toDouble(), destinationLng.toDouble())
        destinationMarker = MarkerOptions().position(destination).title("Destination")
        map.addMarker(destinationMarker!!)
    }

    private fun enableMyLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        } else {
            map.isMyLocationEnabled = true
            startLocationUpdates()
        }
    }

    private var isFirstLocationUpdate = true

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    val currentLocation = LatLng(location.latitude, location.longitude)
                    if (isFirstLocationUpdate) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f))
                        isFirstLocationUpdate = false
                        drawRoute(currentLocation)
                    }

                }
            }
        }

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        }
    }

    private fun drawRoute(currentLocation: LatLng) {
        val destinationLat = arguments?.getFloat("latitude") ?: 0.0f
        val destinationLng = arguments?.getFloat("longitude") ?: 0.0f
        val destination = LatLng(destinationLat.toDouble(), destinationLng.toDouble())

        val directionsApi = RetrofitClient.directionsApiService
        val originParam = "${currentLocation.latitude},${currentLocation.longitude}"
        val destinationParam = "${destination.latitude},${destination.longitude}"
        val apiKey = "AIzaSyDJHQXB7dOVSg88kQX6a47YD9srrCrWZ1o"

        Log.d("MapFragment", "Requesting directions from $originParam to $destinationParam")

        val call = directionsApi.getDirections(originParam, destinationParam, apiKey)
        call.enqueue(object : retrofit2.Callback<DirectionsResponse> {
            override fun onResponse(call: Call<DirectionsResponse>, response: Response<DirectionsResponse>) {
                Log.d("MapFragment", "Response: ${response.raw().request.url}")

                Log.d("MapFragment", "Response received: ${response.body()}")

                if (response.isSuccessful && response.body() != null) {
                    val routes = response.body()?.routes
                    if (routes.isNullOrEmpty()) {
                        Log.e("MapFragment", "No routes available")
                    } else {
                        Log.d("MapFragment", "polyline data success send to decodepolyline ${routes?.first()}")
//                        val steps = routes.first().legs.flatMap { it.steps }
                        val encoded = "`skn@kct`TDUFIDA\\\\?PDP{@BG?SZyANk@HIJE^IP]Zu@x@{Ac@e@UM[Ig@EkBQw@Uk@WSa@Ie@C@E?QCMKAQDQLIHAb@oA\\\\iAv@_CpA}DFQgF_Be@Q{CeA}DiAKb@"
                        //steps[0].polyline.
                        val path = mutableListOf<LatLng>()
//                        steps.forEach { step ->
////                            path.addAll(decodePolyline(encoded))
//                        }
                        Log.d("MapFragment", "${routes?.first()?.overview_polyline}")

                        if (routes?.first()?.overview_polyline?.points?.isNullOrBlank() == false) {

                            Log.d("MapFragment", "drawpolyline called")
                            routes.first().overview_polyline?.points?.let { drawPolyline(it) }
                        } else {
                            Log.e("MapFragment", "No points decoded from steps' polylines")
                        }
                    }
                } else {
                    Log.e("MapFragment", "Response not successful or null: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<DirectionsResponse>, t: Throwable) {
                Log.e("MapFragment", "Failed to fetch directions: ${t.message}")
                Log.e("MapFragment", "API Request failed: ${t.message}")
            }
        })
    }

    private fun drawPolyline(polyline: String) {
        val points = decodePolyline(polyline)
        //val points = listOf(LatLng(-7.762802236377258, 110.37982844174554
        //        ),LatLng(-7.765693726244566, 110.37863754096037))

        //return PolyUtil.decode(polyline)

        if (points.isNotEmpty()) {
            Log.d("MapFragment", "drawpolyline is not empty and was done ${points}")
            val lineOptions = PolylineOptions().addAll(points).width(10f).color(Color.RED)
            Log.d("MapFragment", "lineoptions : ${lineOptions}")
            map.addPolyline(lineOptions)
        } else {
            Log.e("MapFragment", "Decoded polyline is empty")
        }
    }

    private fun decodePolyline(encoded: String): List<LatLng> {
        return PolyUtil.decode(encoded)
        val poly = ArrayList<LatLng>()
        var index = 0
        var lat = 0
        var lng = 0

        while (index < encoded.length) {
            var b: Int
            var shift = 0
            var result = 0
            do {
                if (index >= encoded.length) {  // Safety check to prevent going out of bounds
                    break
                }
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlat = if (result and 1 != 0) -(result shr 1) else result shr 1
            lat += dlat

            shift = 0
            result = 0
            do {
                if (index >= encoded.length) {  // Safety check to prevent going out of bounds
                    break
                }
                b = encoded[index++].code - 63
                result = result or (b and 0x1f shl shift)
                shift += 5
            } while (b >= 0x20)
            val dlng = if (result and 1 != 0) -(result shr 1) else result shr 1
            lng += dlng

            val p = LatLng(lat.toDouble() / 1E5, lng.toDouble() / 1E5)
            poly.add(p)
        }

        return poly
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        _binding = null
    }
}