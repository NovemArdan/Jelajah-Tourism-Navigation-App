package com.example.jelajah3.ui.theme.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.jelajah3.ui.challenge.ChallengeFragment
import com.example.jelajah3.ui.history.HistoryFragment

import com.example.jelajah3.ui.theme.ranking.RankingFragment

class LeaderboardViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HistoryFragment()
            1 -> RankingFragment()
            2 -> ChallengeFragment()
            else -> throw IllegalStateException("Invalid tab position: $position")
        }
    }
}