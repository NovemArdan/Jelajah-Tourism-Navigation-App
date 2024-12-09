package com.example.jelajah3.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jelajah3.model.HistoryItem
import com.example.jelajah3.model.RankingItem

class SharedViewModel : ViewModel() {
    private val _historyList = MutableLiveData<List<HistoryItem>>()
    val historyList: LiveData<List<HistoryItem>> = _historyList

    private val _rankingList = MutableLiveData<List<RankingItem>>(mutableListOf(
        RankingItem(rank = 1, user = "User 01", score = 1000)  // Initial user and score
    ))
    val rankingList: LiveData<List<RankingItem>> = _rankingList

//    fun addHistoryItem(item: HistoryItem) {
//        val newHistory = _historyList.value.orEmpty().toMutableList()
//        newHistory.add(item)
//        _historyList.value = newHistory
//        if (item.tagId == "63:E4:A6:ED" && newHistory.count { it.tagId == item.tagId } == 2) {
//            updateScoreForUser("User 01", 100)
//        }
//    }

    fun addHistoryItem(item: HistoryItem) {
        val newHistory = _historyList.value.orEmpty().toMutableList()
        newHistory.add(item)
        _historyList.value = newHistory

        if (item.tagId == "63:E4:A6:ED") {
            updateScoreForUser("User 01", 100)
        }
    }

    private fun updateScoreForUser(user: String, additionalPoints: Int) {
        _rankingList.value = _rankingList.value?.map {
            if (it.user == user) it.copy(score = it.score + additionalPoints) else it
        }
    }
}
