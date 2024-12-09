package com.example.jelajah3.ui.theme.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jelajah3.databinding.FragmentRankingBinding

import com.example.jelajah3.ui.shared.SharedViewModel
import com.example.jelajah3.ui.theme.adapter.RankingAdapter

class RankingFragment : Fragment() {
    private lateinit var binding: FragmentRankingBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRankingBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val adapter = RankingAdapter()
        binding.rvRanking.layoutManager = LinearLayoutManager(requireContext())
        binding.rvRanking.adapter = adapter
        sharedViewModel.rankingList.observe(viewLifecycleOwner) { ranking ->
            adapter.submitList(ranking)
        }
    }
}
