package com.example.jelajah3.ui.theme.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jelajah3.ui.challenge.ChallengeFragment
import com.example.jelajah3.ui.theme.history.HistoryFragment
import com.example.jelajah3.ui.theme.ranking.RankingFragment

class LeaderboardViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3 // Jumlah tab

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HistoryFragment()    // Tab pertama
            1 -> RankingFragment()    // Tab kedua
            2 -> ChallengeFragment()  // Tab ketiga
            else -> throw IllegalStateException("Invalid tab position: $position")
        }
    }
}

