package com.example.jelajah3.ui.history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jelajah3.model.HistoryItem

class HistoryViewModel : ViewModel() {
    private val _historyItems = MutableLiveData<List<HistoryItem>>()
    val historyItems: LiveData<List<HistoryItem>> = _historyItems

    // Method to add a history item to the LiveData list
    fun addHistoryItem(item: HistoryItem) {
        val updatedItems = _historyItems.value.orEmpty().toMutableList()
        updatedItems.add(item)
        _historyItems.value = updatedItems
        Log.d("HistoryViewModel", "Added item: $item")
    }

//    fun addHistoryItem(item: HistoryItem) {
//        historyData.add(item)
//        _historyList.value = historyData
//        checkAndUpdateScores(item)
//    }

//    private fun checkAndUpdateScores(item: HistoryItem) {
//        if (item.tagId == "63:E4:A6:ED") {
//            val count = historyData.count { it.tagId == "63:E4:A6:ED" }
//            if (count == 2) {
//                rankingViewModel.updateScoreForUser("User 01", 100)
//            }
//        }
//    }
}
