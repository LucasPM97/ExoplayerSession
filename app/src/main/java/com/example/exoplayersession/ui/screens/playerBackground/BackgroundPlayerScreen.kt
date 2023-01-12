package com.example.exoplayersession.ui.screens.playerBackground

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.exoplayersession.ui.screens.components.rememberMediaControllerState
import com.example.exoplayersession.ui.screens.playerForeground.components.PlayerView
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme

@Composable
fun BackgroundPlayerScreen() {

    val mediaController by rememberMediaControllerState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        PlayerView(
            player = mediaController,
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
        BackgroundPlayerScreen()
    }
} 