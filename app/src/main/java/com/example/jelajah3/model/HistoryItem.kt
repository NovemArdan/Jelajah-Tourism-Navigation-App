package com.example.jelajah3.model

data class HistoryItem(
    val nomor: Int,          // Nomor urut untuk entry dalam history
    val tagId: String,       // Kode tag ID dari NFC tag
    val gpsLocation: String  // Lokasi GPS dalam format string (latitude, longitude)
)