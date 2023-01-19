package com.example.exoplayersession.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.example.exoplayersession.data.getMediaSessionController
import com.example.exoplayersession.ui.screens.playerPip.PipPlayerScreen
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme
import kotlinx.coroutines.launch

class PipPlayerActivity : ComponentActivity() {

    @androidx.media3.common.util.UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExoPlayerSessionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PipPlayerScreen()
                }
            }
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