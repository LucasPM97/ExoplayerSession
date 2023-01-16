package com.example.exoplayersession.ui.screens.playerPip

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.lifecycle.Lifecycle
import androidx.media3.session.MediaController
import com.example.exoplayersession.ui.screens.components.*
import com.example.exoplayersession.ui.screens.playerPip.components.PictureInPictureButton

@Composable
fun PipPlayerScreen(
    launchPictureInPictureMode: () -> Unit = {},
    onGloballyPositioned: (LayoutCoordinates) -> Unit = {}
) {

    var playerControllerVisibility by rememberSaveable {
        mutableStateOf(PlayerControllerVisibility.None)
    }

    val mediaController by rememberMediaController()
    val mediaControllerState by rememberMediaControllerState(mediaController)
    LaunchedEffect(mediaControllerState) {
        when (mediaControllerState) {
            PlayerStates.Ended -> {
                playerControllerVisibility = PlayerControllerVisibility.Show
            }
        }
    }

    val lifecycle by rememberLifecycleState()
    LaunchedEffect(lifecycle) {
        when (lifecycle) {
            Lifecycle.Event.ON_PAUSE -> playerControllerVisibility =
                PlayerControllerVisibility.Hide
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {

        PipPlayer(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
                .onGloballyPositioned {
                    onGloballyPositioned(it)
                },
            mediaController,
            playerControllerVisibility,
            updatePlayerControlsVisibility = {
                playerControllerVisibility = it
            }
        )
        PictureInPictureButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                launchPictureInPictureMode()
            }
        )
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
private fun PipPlayer(
    modifier: Modifier,
    mediaController: MediaController?,
    playerControlsVisibility: PlayerControllerVisibility,
    updatePlayerControlsVisibility: (PlayerControllerVisibility) -> Unit

) {

    PlayerView(
        player = mediaController,
        modifier = modifier,
        controllerAutoShow = false,
        update = {
            when (playerControlsVisibility) {
                PlayerControllerVisibility.Show -> it.showController()
                PlayerControllerVisibility.Hide -> it.hideController()
            }
            if (playerControlsVisibility != PlayerControllerVisibility.None) {
                updatePlayerControlsVisibility(PlayerControllerVisibility.None)
            }
        },


        )
}

enum class PlayerControllerVisibility {
    Show,
    Hide,
    None
}