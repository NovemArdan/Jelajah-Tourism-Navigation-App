package com.example.jelajah3.ui.nfctag

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.jelajah3.R

class NFCTagFragment : Fragment() {

    private lateinit var viewModel: NFCTagViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[NFCTagViewModel::class.java]
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nfctag, container, false)
    }

    // Additional functions for NFC handling can be added here
}
