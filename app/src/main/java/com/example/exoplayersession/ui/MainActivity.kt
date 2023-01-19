package com.example.exoplayersession.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.exoplayersession.data.PipBroadcastReceiver
import com.example.exoplayersession.data.getMediaSessionController
import com.example.exoplayersession.ui.screens.playerPip.PipPlayerScreen
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var pipReceiver: PipBroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pipReceiver = PipBroadcastReceiver()
        setContent {
            ExoPlayerSessionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PipPlayerScreen(pipReceiver)
                }
            }
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)

        if (isInPictureInPictureMode) {
            registerReceiver(pipReceiver, pipReceiver.intentFilter)
        } else {
            unregisterReceiver(pipReceiver)
        }
    }

    override fun onStop() {
        super.onStop()
        if (isInPictureInPictureMode) {
            lifecycleScope.launch {
                val mediaController = getMediaSessionController()
                mediaController.pause()
                mediaController.release()
            }
        }
    }
}