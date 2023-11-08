package com.example.servicioapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.TextView
import android.widget.Toast

class Receiver: BroadcastReceiver() {
    private val TAG = "Receiver"
    override fun onReceive(context: Context?, intent: Intent?) {
        StringBuffer().apply {
            append("Action: ${intent?.action}\n")
            append("URI: ${intent?.toUri(Intent.URI_INTENT_SCHEME)}\n")
                toString().also { log ->
                    Log.d(TAG, log)
                    Toast.makeText(context, log, Toast.LENGTH_LONG).show()


                }
        }
    }

}