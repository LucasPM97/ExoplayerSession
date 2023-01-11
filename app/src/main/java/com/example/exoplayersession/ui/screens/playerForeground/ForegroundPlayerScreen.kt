package com.example.exoplayersession.ui.screens.playerForeground

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player
import com.example.exoplayersession.ui.screens.playerForeground.components.PlayerView
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme
import org.koin.androidx.compose.getViewModel

@Composable
fun ForegroundPlayerScreen(
    viewModel: ForegroundPlayerViewModel = getViewModel()

) {
    ScreenContent(viewModel.player)
}

@Composable
fun ScreenContent(player: Player?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        PlayerView(
            player,
            stopOnBackground = true,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f),
        )
    }
}

@Preview
@Composable
private fun PreviewScreenContent() {
    ExoPlayerSessionTheme {
        ScreenContent(null)
    }
} 