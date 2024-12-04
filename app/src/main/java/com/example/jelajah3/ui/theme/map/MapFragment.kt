package com.example.jelajah3.ui.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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

class MapFragment : Fragment(), OnMapReadyCallback, SensorEventListener {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var destinationMarker: MarkerOptions? = null
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null
    private val gravity = FloatArray(3)
    private val geomagnetic = FloatArray(3)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        initializeSensors()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> System.arraycopy(event.values, 0, gravity, 0, event.values.size)
            Sensor.TYPE_MAGNETIC_FIELD -> System.arraycopy(event.values, 0, geomagnetic, 0, event.values.size)
        }
        updateDirection()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        Log.d("MapFragment", "Sensor accuracy changed: Sensor = ${sensor.name}, Accuracy = $accuracy")
    }

    private fun updateDirection() {
        val rotationMatrix = FloatArray(9)
        val orientation = FloatArray(3)
        if (SensorManager.getRotationMatrix(rotationMatrix, null, gravity, geomagnetic)) {
            SensorManager.getOrientation(rotationMatrix, orientation)
            val azimuthInRadians = orientation[0]
            val azimuthInDegrees = Math.toDegrees(azimuthInRadians.toDouble()).toFloat()
            updateCompassUI(azimuthInDegrees)
        }
    }

    private fun updateCompassUI(azimuth: Float) {
        val compassDirection = getCompassDirection(azimuth)
        activity?.runOnUiThread {
            // Menambahkan derajat ke teks
            binding.directionText.text = "Arah Anda: $compassDirection (${azimuth.toInt()}°)"
        }
    }


    private fun getCompassDirection(azimuth: Float): String {
        val directions = arrayOf("Utara", "Timur Laut", "Timur", "Tenggara", "Selatan", "Barat Daya", "Barat", "Barat Laut")
        val index = ((azimuth + 360) % 360 / 45).toInt() % directions.size
        return directions[index]
    }



    private fun initializeSensors() {
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
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

                        val route = routes.first()
                        val leg = route.legs.firstOrNull()
                        activity?.runOnUiThread {
                            if (leg != null) {
                                //val modes = leg.steps?.map { it.travel_mode }?.distinct()?.joinToString(", ")
                                //binding.directionText.text = "Arah Anda: ${leg.startAddress} to ${leg.endAddress}"
                                binding.distanceText.text = "Jarak: ${leg.distance?.text}"
                                binding.timeText.text = "Waktu tempuh: ${leg.duration?.text}"
                                binding.transportModeText.text = "Mode transportasi: ${leg.steps?.firstOrNull()?.travel_mode?: "Unknown"}"
                                //binding.travelmode.text = "Waktu tempuh: ${leg.steps.traveltravel_mode?.text}"
                            }
                        }

                        Log.d("MapFragment", "polyline data success send to decodepolyline ${routes?.first()}")

                        val encoded = "`skn@kct`TDUFIDA\\\\?PDP{@BG?SZyANk@HIJE^IP]Zu@x@{Ac@e@UM[Ig@EkBQw@Uk@WSa@Ie@C@E?QCMKAQDQLIHAb@oA\\\\iAv@_CpA}DFQgF_Be@Q{CeA}DiAKb@"

                        val path = mutableListOf<LatLng>()

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        _binding = null
    }
}