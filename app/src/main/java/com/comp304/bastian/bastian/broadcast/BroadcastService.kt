package com.comp304.bastian.bastian.broadcast

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.comp304.bastian.bastian.view.BastianActivity

class BroadcastService : Service() {

    companion object {
        private const val TAG = "BroadcastService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.e(TAG, "Service onBind")
        return null
    }

    override fun onCreate() {
        super.onCreate()
        Log.e(TAG, "Service onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "Service onDestroy")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        Log.e(TAG, "Service onStartCommand")
        LocalBroadcastManager.getInstance(this).sendBroadcast(
            Intent(BastianActivity.LOCAL_BROADCAST_ACTION).apply {
                action = BastianActivity.LOCAL_BROADCAST_ACTION
            }
        )
        return START_STICKY
    }
}