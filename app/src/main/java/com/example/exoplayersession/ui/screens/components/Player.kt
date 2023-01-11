package com.example.exoplayersession.ui.screens.playerForeground.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme

@Composable
fun PlayerView(
    player: Player?,
    modifier: Modifier = Modifier,
    stopOnBackground: Boolean = false
) {
    // Just for Preview purposes
    if (player == null) {
        RenderLoading(modifier = modifier)
        return
    }

    val lifecycle by rememberLifecycleState()

    AndroidView(factory = { context ->
        PlayerView(context).also {
            it.player = player
        }
    }, update = {
        when (lifecycle) {
            Lifecycle.Event.ON_PAUSE -> {
                if (stopOnBackground) {
                    it.onPause()
                    it.player?.pause()
                }
                player.release()
            }
        }
    }, modifier = modifier
    )

}


@Composable
private fun RenderLoading(modifier: Modifier) {
    Box(
        modifier = modifier.background(Color.Black)
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerNull() {
    ExoPlayerSessionTheme {
        PlayerView(
            player = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
    }
}