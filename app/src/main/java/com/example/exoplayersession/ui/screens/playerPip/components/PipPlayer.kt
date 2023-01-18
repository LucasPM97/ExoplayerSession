package com.example.exoplayersession.ui.screens.playerPip.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.media3.session.MediaController
import com.example.exoplayersession.ui.screens.components.PlayerView
import com.example.exoplayersession.ui.screens.playerPip.PlayerControllerVisibility

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun PipPlayer(
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
                else -> {}
            }
            if (playerControlsVisibility != PlayerControllerVisibility.None) {
                updatePlayerControlsVisibility(PlayerControllerVisibility.None)
            }
        },
    )
}