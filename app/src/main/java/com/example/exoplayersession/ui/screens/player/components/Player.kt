package com.example.exoplayersession.ui.screens.player.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.Player
import androidx.media3.ui.PlayerView
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme

@Composable
fun Player(
    player: Player?,
    modifier: Modifier = Modifier
) {

    if (player == null) {
        Box(
            modifier = modifier
                .background(Color.Black)
        )
        return
    }

    AndroidView(
        factory = { context ->
            PlayerView(context).also {
                it.player = player
            }
        },
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPlayerNull() {
    ExoPlayerSessionTheme {
        Player(
            player = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
    }
}