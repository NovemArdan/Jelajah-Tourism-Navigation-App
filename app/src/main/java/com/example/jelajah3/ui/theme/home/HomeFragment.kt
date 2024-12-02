package com.example.jelajah3.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jelajah3.R
import com.example.jelajah3.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val username = arguments?.getString("username") ?: ""
        viewModel.setUserName(username)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.userName.observe(viewLifecycleOwner) { name ->
            binding.welcomeText.text = "Welcome, $name!"
        }

        // Setting click listener untuk button settings
        binding.settingButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        // Set up recycler view dan lainnya seperti sebelumnya...
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
