package com.example.exoplayersession.ui

import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Rational
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.lifecycle.lifecycleScope
import com.example.exoplayersession.R
import com.example.exoplayersession.data.PipBroadcastReceiver
import com.example.exoplayersession.data.getMediaSessionController
import com.example.exoplayersession.ui.screens.playerPip.PipPlayerScreen
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme
import kotlinx.coroutines.launch

class PipPlayerActivity : ComponentActivity() {

    private val isPipSupported by lazy {
        // This feature is not on lower versions than API 26
        packageManager.hasSystemFeature(
            PackageManager.FEATURE_PICTURE_IN_PICTURE
        )
    }

    private var videoViewBounds = Rect()

    @androidx.media3.common.util.UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExoPlayerSessionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PipPlayerScreen(
                        launchPictureInPictureMode = {
                            launchPictureInPictureMode()
                        },
                        onGloballyPositioned = {
                            videoViewBounds = it
                                .boundsInWindow()
                                .toAndroidRect()
                        }
                    )
                }
            }
        }
    }

    private fun launchPictureInPictureMode() {
        if (!isPipSupported) return

        updatePipParams()?.let { params ->
            enterPictureInPictureMode(params)
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

    private fun updatePipParams(): PictureInPictureParams? {
        return PictureInPictureParams.Builder()
            .setSourceRectHint(videoViewBounds)
            .setAspectRatio(Rational(16, 9))
            .setActions(
                listOf(
                    RemoteAction(
                        Icon.createWithResource(
                            applicationContext,
                            R.drawable.ic_baseline_pause_24
                        ),
                        "Pause video",
                        "Pause video",
                        PendingIntent.getBroadcast(
                            applicationContext,
                            0,
                            Intent(applicationContext, PipBroadcastReceiver::class.java),
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                )
            ).build()
    }
}