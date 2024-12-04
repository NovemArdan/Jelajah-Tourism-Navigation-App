package com.example.jelajah3.ui.nfctag

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.jelajah3.databinding.FragmentNfctagBinding
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.example.jelajah3.MainActivity
import android.nfc.tech.NfcA
import android.os.Build
import java.io.IOException

class NFCTagFragment : Fragment() {
    private var _binding: FragmentNfctagBinding? = null
    private val binding get() = _binding!!

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lastLocationText: String = "Lokasi tidak diketahui"

    private val nfcAdapter: NfcAdapter?
        get() = (activity as MainActivity).nfcAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        Log.d("NFCTagFragment", "fusedLocationClient: ${fusedLocationClient}")
        checkLocationPermission()

        _binding = FragmentNfctagBinding.inflate(inflater, container, false)
        setupInteraction()



        return binding.root
    }

    private fun checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 100)
            Log.d("NFCTagFragment", "tidak diberi akses")
        } else {
            Log.d("NFCTagFragment", "diberi akses")
            fetchLocation()
        }
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            location?.let {
                lastLocationText = "Posisi Lat: ${location.latitude}, Long: ${location.longitude}"
                Log.d("NFCTagFragment", "Posisi Lat: ${location.latitude}, Long: ${location.longitude}")
            }
        }.addOnFailureListener {
            Log.e("NFCTagFragment", "Failed to get location", it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupInteraction() {
        binding.imageViewScan.setOnClickListener {
            Log.d("NFCTagFragment", "ImageView clicked")
            binding.textViewPrompt.text = "Proses scan lokasi sedang berjalan\ndekatkan gawai anda dengan nfc tag"
            Log.d("NFCTagFragment", "Text set on TextView")

            try {
                enableNfcForegroundDispatch()
                Log.d("NFCTagFragment", "NFC Foreground Dispatch enabled")
            } catch (e: Exception) {
                Log.e("NFCTagFragment", "Failed to enable NFC Foreground Dispatch", e)
            }

//            Handler(Looper.getMainLooper()).postDelayed({
//                try {
//                    disableNfcForegroundDispatch()
//                    Log.d("NFCTagFragment", "NFC Foreground Dispatch disabled")
//                } catch (e: Exception) {
//                    Log.e("NFCTagFragment", "Failed to disable NFC Foreground Dispatch", e)
//                }
//            }, 30000)
        }
    }

    private fun enableNfcForegroundDispatch() {
        nfcAdapter?.let { adapter ->
            if (adapter.isEnabled) {
                Log.d("NFCTagFragment", "masuk enableNfcForegroundDispatch")
                val nfcIntentFilter = arrayOf(
                    IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED),
                    IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED),
                    IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED)
                )

                val intent = Intent(activity, activity?.javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    PendingIntent.getActivity(
                        activity,
                        0,
                        intent,
                        //PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                        PendingIntent.FLAG_MUTABLE
                    )
                } else {
                    PendingIntent.getActivity(
                        activity,
                        0,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                    )
                }

                adapter.enableForegroundDispatch(
                    activity, pendingIntent, nfcIntentFilter, null
                )
                Log.d("NFCTagFragment", "NFC Foreground Dispatch enabled")
            } else {
                Log.e("NFCTagFragment", "NFC is disabled.")
            }
        }
    }

    private fun disableNfcForegroundDispatch() {
        nfcAdapter?.disableForegroundDispatch(activity)
    }

    @SuppressLint("SetTextI18n")
    fun processNfcIntent(intent: Intent) {
        Log.d("NFCTagFragment", "Intent received with action: ${intent.action}")
        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)

        if (tag != null) {
            // Convert tag ID to a hex string
            val tagId = tag.id.joinToString(":") { byte -> "%02X".format(byte) }
            var locationMessage = "Anda sekarang berada di\n$tagId" // Default message

            // Check specific tag IDs and update location message accordingly
            when (tagId) {
                "05:89:91:56:BD:82:00" -> locationMessage = "Anda sedang berada di Fakultas Teknik UGM"
                "63:E4:A6:ED" -> locationMessage = "Anda sedang berada di Perpustakaan FT UGM"
            }

            // Update the UI on the main thread
            activity?.runOnUiThread {
                binding.textViewPrompt.text = "Proses scan lokasi sedang berjalan\ndekatkan gawai anda dengan nfc tag\n$locationMessage\n$lastLocationText"
            }
            Log.d("NFCTagFragment", "Tag ID: $tagId") // Log the tag ID
        } else {
            // Update the UI and log if no tag is detected
            activity?.runOnUiThread {
                binding.textViewPrompt.text = "NFC Tag is null"
            }
            Log.e("NFCTagFragment", "NFC Tag is null")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}