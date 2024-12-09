package com.example.jelajah3.ui.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jelajah3.databinding.FragmentChallengeBinding
import com.example.jelajah3.model.ChallengeItem

class ChallengeFragment : Fragment() {

    private lateinit var binding: FragmentChallengeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChallengeBinding.inflate(inflater, container, false)
        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {
        val challengeList = listOf(
            ChallengeItem(challengeId = 1, title = "Tap 63:E4:A6:ED", description = "100 poin per tap", progress = 1, points = 100, completed = false),
            //ChallengeItem(challengeId = 2, title = "50 Tap Challenge", description = "Complete 50 taps", progress = 50, points = 500, completed = true)
        )

        val adapter = ChallengeAdapter(challengeList)
        binding.rvChallenge.layoutManager = LinearLayoutManager(requireContext())
        binding.rvChallenge.adapter = adapter
    }
}
