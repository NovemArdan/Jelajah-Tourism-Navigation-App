package com.example.jelajah3.ui.leaderboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jelajah3.model.ChallengeItem
import com.example.jelajah3.model.HistoryItem
import com.example.jelajah3.model.RankingItem
import com.example.jelajah3.repository.LeaderboardRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel() {
    private val repository = LeaderboardRepository()  // Assumed repository

    private val _historyData = MutableLiveData<List<HistoryItem>>()
    val historyData: LiveData<List<HistoryItem>> = _historyData

    private val _rankingData = MutableLiveData<List<RankingItem>>()
    val rankingData: LiveData<List<RankingItem>> = _rankingData

    private val _challengeData = MutableLiveData<List<ChallengeItem>>()
    val challengeData: LiveData<List<ChallengeItem>> = _challengeData

    init {
        loadHistory()
        loadRanking()
        loadChallenges()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            _historyData.value = repository.getHistoryData()
        }
    }

    private fun loadRanking() {
        viewModelScope.launch {
            _rankingData.value = repository.getRankingData()
        }
    }

    private fun loadChallenges() {
        viewModelScope.launch {
            _challengeData.value = repository.getChallengeData()
        }
    }
}
