package com.example.exoplayersession

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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.lifecycle.Lifecycle
import com.example.exoplayersession.ui.screens.components.PlayerView
import com.example.exoplayersession.ui.screens.playerForeground.ForegroundPlayerViewModel
import com.example.exoplayersession.ui.screens.playerForeground.components.rememberLifecycleState
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {


    class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            print("Try to pause the video")
        }

    }

    private val isPipSupported by lazy {
        // This feature is not on lower versions than API 26
        packageManager.hasSystemFeature(
            PackageManager.FEATURE_PICTURE_IN_PICTURE
        )
    }

    private var videoViewBounds = Rect()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val viewModel: ForegroundPlayerViewModel = getViewModel()

        setContent {
            ExoPlayerSessionTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val lifecycle by rememberLifecycleState()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        PlayerView(
                            player = viewModel.player,
                            update = {
                                when (lifecycle) {
                                    Lifecycle.Event.ON_PAUSE -> {
                                        if (!isInPictureInPictureMode) {
                                            it.onPause()
                                            it.player?.pause()
                                        }
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16 / 9f)
                                .onGloballyPositioned {
                                    videoViewBounds = it
                                        .boundsInWindow()
                                        .toAndroidRect()
                                }

                        )
                    }
                }
            }
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        if (!isPipSupported) return

        updatePipParams()?.let { params ->
            enterPictureInPictureMode(params)
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
                            Intent(applicationContext, MyReceiver::class.java),
                            PendingIntent.FLAG_IMMUTABLE
                        )
                    )
                )
            ).build()
    }
}

