package com.example.exoplayersession.ui.screens.playerForeground.components

import android.content.ComponentName
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.work.await
import com.example.exoplayersession.MusicService

@Composable
fun rememberMediaControllerState(): MutableState<MediaController?> {
    val context = LocalContext.current
    val mediaControllerState = remember {
        mutableStateOf<MediaController?>(null)
    }

    LaunchedEffect(true) {
        context.run {
            val sessionToken =
                SessionToken(this, ComponentName(this, MusicService::class.java))

            val controllerFuture = MediaController.Builder(
                this,
                sessionToken
            ).buildAsync()
            mediaControllerState.value = controllerFuture.await()
        }
    }

    return mediaControllerState
}