package com.example.exoplayersession.ui.screens.playerPip

import android.graphics.Rect
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toAndroidRect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.lifecycle.Lifecycle
import com.example.exoplayersession.ui.screens.components.*
import com.example.exoplayersession.ui.screens.playerPip.components.PictureInPictureButton
import com.example.exoplayersession.ui.screens.playerPip.components.PipPlayer

@Composable
fun PipPlayerScreen() {

    // This is just used to trigger the Player controller's Show and Hide methods, and it backs to None after changed
    // This value will not change after a UI interaction
    var playerControllerVisibility by rememberSaveable {
        mutableStateOf(PlayerControllerVisibility.None)
    }

    var videoViewBounds by remember {
        mutableStateOf(Rect())
    }

    val mediaController by rememberMediaController()
    val mediaControllerState by rememberMediaControllerState(mediaController)
    LaunchedEffect(mediaControllerState) {
        when (mediaControllerState.state) {
            PlayerStates.Ended -> {
                playerControllerVisibility = PlayerControllerVisibility.Show
            }
            else -> {}
        }
    }

    val lifecycle by rememberLifecycleState()
    LaunchedEffect(lifecycle) {
        when (lifecycle) {
            Lifecycle.Event.ON_PAUSE -> playerControllerVisibility =
                PlayerControllerVisibility.Hide
            else -> {}
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        PipPlayer(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .onGloballyPositioned {
                    videoViewBounds = it
                        .boundsInWindow()
                        .toAndroidRect()
                },
            mediaController,
            playerControllerVisibility,
            updatePlayerControlsVisibility = {
                playerControllerVisibility = it
            }
        )
        PictureInPictureButton(
            isPlaying = mediaControllerState.isPlaying,
            videoViewBounds = videoViewBounds,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

enum class PlayerControllerVisibility {
    Show,
    Hide,
    None
}