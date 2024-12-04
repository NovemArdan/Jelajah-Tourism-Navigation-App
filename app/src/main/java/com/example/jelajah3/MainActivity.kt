package com.example.jelajah3

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.nfc.tech.NfcA
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.ui.setupWithNavController
import com.example.jelajah3.ui.nfctag.NFCTagFragment
import android.nfc.Tag


class MainActivity : AppCompatActivity() {

    var nfcAdapter: NfcAdapter? = null
    private val TAG = "NFCTagFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: Started.")

        val navController = findNavController(R.id.nav_host_fragment)
        findViewById<BottomNavigationView>(R.id.bottom_nav).setupWithNavController(navController)

        val username = intent.getStringExtra("username") ?: ""
        val bundle = Bundle().apply {
            putString("username", username)
        }
        navController.setGraph(R.navigation.nav_graph, bundle)

        setupNFC()
    }

    private fun setupNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Log.e(TAG, "setupNFC: NFC is not supported on this device.")
            return
        }

        if (!nfcAdapter!!.isEnabled) {
            Log.e(TAG, "setupNFC: NFC is disabled.")
        } else {
            Log.d(TAG, "setupNFC: NFC is enabled.")
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume: Activity resumed.")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Activity paused.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy: Activity is being destroyed.")
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        //Log.d(TAG, "Intent: ${intent}")
        Log.d(TAG, "New intent received with action: ${intent.action}")

        Log.d(TAG, "New NfcAdapter.ACTION_NDEF_DISCOVERED received: ${NfcAdapter.ACTION_NDEF_DISCOVERED}")
        Log.d(TAG, "New NfcAdapter.ACTION_TECH_DISCOVERED received: ${NfcAdapter.ACTION_TECH_DISCOVERED}")
        Log.d(TAG, "New NfcAdapter.ACTION_TAG_DISCOVERED received: ${NfcAdapter.ACTION_TAG_DISCOVERED}")

        //if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action || NfcAdapter.ACTION_TECH_DISCOVERED == intent.action) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED == intent.action) {
            Log.d(TAG, "sebelum masuk handle intent")
            handleNfcIntent(intent)
        } else {
            Log.e(TAG, "Unhandled NFC Intent Action: ${intent.action}")
        }
    }

    private fun handleNfcIntent(intent: Intent) {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.first()

        val tag: Tag? = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
        if (tag != null) {
            try {
                // Mencoba membaca sebagai NDEF terlebih dahulu
                val nfcMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                if (nfcMessages != null) {
                    Log.d(TAG, "NFC Messages received: ${nfcMessages}")
                    val ndefMessages = nfcMessages.map { it as NdefMessage }
                    for (message in ndefMessages) {
                        for (record in message.records) {
                            val payload = String(record.payload, Charsets.UTF_8) // Decoding payload
                            Log.d(TAG, "Read payload: $payload")
                        }
                    }
                } else {
                    Log.e(TAG, "No NDEF messages found.")
                }

                // Membaca informasi dasar dari tag seperti ID
                val id = tag.id.joinToString(separator = ":") { String.format("%02X", it) }
                Log.d(TAG, "Tag ID: $id")

                // Contoh untuk membaca menggunakan NfcA
                val nfcA = NfcA.get(tag)
                nfcA.connect()
                val atqa = nfcA.atqa.joinToString(":") { String.format("%02X", it) }
                val sak = String.format("%02X", nfcA.sak)
                Log.d(TAG, "ATQA: $atqa, SAK: $sak")
                nfcA.close()

            } catch (e: Exception) {
                Log.e(TAG, "Error processing NFC tag", e)
            }
        } else {
            Log.e(TAG, "NFC Tag is null")
        }

        if (currentFragment is NFCTagFragment) {
            Log.d("NFCTagFragment", "Forwarding NFC intent to NFCTagFragment")
            currentFragment.processNfcIntent(intent)
        } else {
            Log.e("NFCTagFragment", "Current fragment is not NFCTagFragment")
        }
    }

    private fun handleIntent(intent: Intent) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val currentFragment = navHostFragment?.childFragmentManager?.fragments?.first()

        if (currentFragment is NFCTagFragment) {
            Log.d("NFCTagFragment", "Forwarding NFC intent to NFCTagFragment")
            currentFragment.processNfcIntent(intent)
        } else {
            Log.e("NFCTagFragment", "Current fragment is not NFCTagFragment")
        }
    }
}