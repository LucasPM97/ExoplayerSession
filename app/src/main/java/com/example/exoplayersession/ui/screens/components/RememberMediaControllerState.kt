package com.example.exoplayersession.ui.screens.components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.media3.session.MediaController
import com.example.exoplayersession.getMediaSessionController

@Composable
fun rememberMediaController(): MutableState<MediaController?> {
    val context = LocalContext.current
    val mediaControllerState = remember {
        mutableStateOf<MediaController?>(null)
    }

    LaunchedEffect(true) {
        mediaControllerState.value = context.getMediaSessionController()
    }

    return mediaControllerState
}

