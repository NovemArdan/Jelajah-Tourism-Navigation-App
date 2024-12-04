package com.example.jelajah3.model

import com.google.android.gms.maps.model.LatLng

data class DirectionsResponse(
    val routes: List<Route>,
    val status: String
)

data class Route(
    val overview_polyline: Polyline,
    val legs: List<Leg>
)

data class Leg(
    val steps: List<Step>,
    val duration: Duration,
    val distance: Distance,
    val start_address: String,
    val end_address: String
)

data class Step(
    val start_location: LatLng,
    val end_location: LatLng,
    val polyline: Polyline,
    val duration: Duration,
    val distance: Distance,
    val html_instructions: String,
    val travel_mode: String
)

data class Polyline(
    val points: String
)

data class Duration(
    val value: Int,
    val text: String
)

data class Distance(
    val value: Int,
    val text: String
)
