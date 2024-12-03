package com.example.jelajah3.ui.nfctag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jelajah3.databinding.FragmentNfctagBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NFCTagFragment : Fragment() {
    private var _binding: FragmentNfctagBinding? = null
    private val binding get() = _binding!!
    private val viewModel: NFCTagViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNfctagBinding.inflate(inflater, container, false)
        setupInteraction()
        return binding.root
    }

    private fun setupInteraction() {
        binding.imageViewScan.setOnClickListener {
            // Check if NFC is available or enabled, you might need to add this logic in ViewModel
            if (viewModel.isNFCEnabled()) {
                showScanningDialog()
            } else {
                Toast.makeText(context, "NFC is not available or not enabled.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showScanningDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Proses Scanning...")
            .setMessage("Dekatkan smartphone anda ke alat tagging")
            .setNegativeButton("Keluar") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
