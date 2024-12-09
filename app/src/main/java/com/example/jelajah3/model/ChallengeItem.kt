package com.example.jelajah3.model

data class ChallengeItem(
    val challengeId: Int,      // ID unik untuk tantangan
    val title: String,         // Judul tantangan
    val description: String,   // Deskripsi detail tantangan
    val progress: Int,         // Progress tantangan (e.g., 10 Tap)
    val points: Int,           // Poin yang bisa diperoleh
    val completed: Boolean     // Status apakah selesai
)