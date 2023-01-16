package com.example.exoplayersession.ui.screens.components

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.media3.common.Player


@Composable
fun rememberMediaControllerState(player: Player?): MutableState<PlayerStates> {
    val mediaControllerState = rememberSaveable {
        mutableStateOf(PlayerStates.Idle)
    }

    DisposableEffect(player) {
        val observer = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                super.onPlaybackStateChanged(playbackState)
                mediaControllerState.value = mapPlayerStateToEnum(playbackState)
            }
        }
        player?.addListener(observer)


        onDispose {
            player?.removeListener(observer)
        }
    }

    return mediaControllerState
}

private fun mapPlayerStateToEnum(state: Int): PlayerStates {
    return when (state) {
        Player.STATE_IDLE -> PlayerStates.Idle
        Player.STATE_BUFFERING -> PlayerStates.Buffering
        Player.STATE_ENDED -> PlayerStates.Ended
        Player.STATE_READY -> PlayerStates.Ready
        else -> throw Exception("Invalid Player state")
    }
}

enum class PlayerStates {
    Idle,
    Buffering,
    Ready,
    Ended
}