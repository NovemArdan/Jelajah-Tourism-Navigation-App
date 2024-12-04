package com.example.jelajah3.ui.theme.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jelajah3.databinding.FragmentRankingBinding
import com.example.jelajah3.model.RankingItem
import com.example.jelajah3.ui.theme.adapter.RankingAdapter

class RankingFragment : Fragment() {

    private lateinit var binding: FragmentRankingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val rankingList = listOf(
            RankingItem(rank = 1, user = "User 01", score = 1000),
            RankingItem(rank = 2, user = "User 02", score = 500),
            RankingItem(rank = 3, user = "User 03", score = 200)
        )

        val adapter = RankingAdapter(rankingList)
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
    }
}

