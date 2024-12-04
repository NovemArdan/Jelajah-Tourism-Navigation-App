package com.example.jelajah3.ui.leaderboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jelajah3.databinding.FragmentLeaderboardBinding
import com.google.android.material.tabs.TabLayoutMediator
import androidx.fragment.app.viewModels
import com.example.jelajah3.ui.theme.adapter.LeaderboardViewPagerAdapter


class LeaderboardFragment : Fragment() {

    private lateinit var binding: FragmentLeaderboardBinding
    private val viewModel: LeaderboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        setupViewPager()
        observeViewModel()
        return binding.root
    }

    private fun setupViewPager() {
        val adapter = LeaderboardViewPagerAdapter(this)
        binding.viewPager.adapter = adapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "History"
                1 -> "Ranking"
                2 -> "Challenge"
                else -> null
            }
        }.attach()
    }

    private fun observeViewModel() {
        viewModel.historyData.observe(viewLifecycleOwner, { historyItems ->
            // Update your UI for history
        })
        viewModel.rankingData.observe(viewLifecycleOwner, { rankingItems ->
            // Update your UI for ranking
        })
        viewModel.challengeData.observe(viewLifecycleOwner, { challengeItems ->
            // Update your UI for challenges
        })
    }
}
