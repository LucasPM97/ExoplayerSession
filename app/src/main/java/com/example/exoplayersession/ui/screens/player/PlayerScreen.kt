package com.example.exoplayersession.ui.screens.player

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.example.exoplayersession.ui.screens.player.components.Player
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme

@Composable
fun PlayerScreen(
    viewModel: PlayerViewModel
) {
    ScreenContent(player = viewModel.player)
}

@Composable
fun ScreenContent(player: Player?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Player(
            player,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )
    }
}

@Preview
@Composable
private fun PreviewScreenContent() {
    ExoPlayerSessionTheme {
        ScreenContent(player = null)
    }
} 