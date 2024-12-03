package com.example.jelajah3.ui.nfctag

import android.app.Application
import android.nfc.NfcAdapter
import androidx.lifecycle.AndroidViewModel

class NFCTagViewModel(application: Application) : AndroidViewModel(application) {
    private var nfcAdapter: NfcAdapter? = NfcAdapter.getDefaultAdapter(application)

    fun isNFCEnabled(): Boolean {
        return nfcAdapter?.isEnabled ?: false
    }
}
