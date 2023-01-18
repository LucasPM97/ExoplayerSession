package com.example.exoplayersession.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class PipBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        print("Try to pause the video")
    }
}