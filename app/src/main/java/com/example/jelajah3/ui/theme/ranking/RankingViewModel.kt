package com.example.jelajah3.ui.theme.ranking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jelajah3.model.RankingItem

class RankingViewModel : ViewModel() {
    private val _rankingList = MutableLiveData<List<RankingItem>>()
    val rankingList: LiveData<List<RankingItem>> = _rankingList

    init {
        // Initialize with User01 only
        _rankingList.value = listOf(
            RankingItem(rank = 1, user = "User 01", score = 1000)
        )
    }

//    fun updateScoreForUser(user: String, additionalPoints: Int) {
//        _rankingList.value = _rankingList.value?.map {
//            if (it.user == user) {
//                it.copy(score = it.score + additionalPoints)
//            } else {
//                it
//            }
//        }
//    }
}