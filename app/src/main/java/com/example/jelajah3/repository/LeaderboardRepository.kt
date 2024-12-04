package com.example.jelajah3.repository

import com.example.jelajah3.model.ChallengeItem
import com.example.jelajah3.model.HistoryItem
import com.example.jelajah3.model.RankingItem

class LeaderboardRepository {
    suspend fun getHistoryData(): List<HistoryItem> {
        return listOf()
    }

    suspend fun getRankingData(): List<RankingItem> {
        return listOf()
    }

    suspend fun getChallengeData(): List<ChallengeItem> {
        return listOf()
    }
}
