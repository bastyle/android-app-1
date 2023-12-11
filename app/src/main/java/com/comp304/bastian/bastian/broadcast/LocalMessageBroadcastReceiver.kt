package com.comp304.bastian.bastian.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.comp304.bastian.bastian.view.BastianActivity

class LocalMessageBroadcastReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "LocalMessageBroadcastReceiver"

    }

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) {
            return
        }
        when(intent.action) {
            BastianActivity.LOCAL_BROADCAST_ACTION -> {
                Log.e(TAG, "This is a local broadcast")
                val stockInfo = intent.getStringExtra(BastianActivity.STOCK_INFO_KEY)
                Toast.makeText(context, "Stock Info received:\r\n$stockInfo", Toast.LENGTH_SHORT).show()
            }
        }
    }
}