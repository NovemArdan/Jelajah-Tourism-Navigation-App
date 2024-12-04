package com.example.jelajah3.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jelajah3.model.HistoryItem

class HistoryViewModel : ViewModel() {

    private val _historyList = MutableLiveData<List<HistoryItem>>()
    val historyList: LiveData<List<HistoryItem>> = _historyList

    private val historyData = mutableListOf<HistoryItem>()

    fun addHistoryItem(item: HistoryItem) {
        historyData.add(item)
        _historyList.value = historyData
    }
}
