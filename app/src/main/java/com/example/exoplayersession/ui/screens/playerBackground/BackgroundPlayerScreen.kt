package com.example.exoplayersession.ui.screens.playerBackground

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import com.example.exoplayersession.ui.screens.components.PlayerView
import com.example.exoplayersession.ui.screens.components.rememberMediaController
import com.example.exoplayersession.ui.screens.components.rememberLifecycleState
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme

@Composable
fun BackgroundPlayerScreen() {

    val mediaController by rememberMediaController()
    val lifecycle by rememberLifecycleState()

    LaunchedEffect(lifecycle) {
        when (lifecycle) {
            Lifecycle.Event.ON_PAUSE -> {
                mediaController?.release()
            }
            else -> {}
        }
    }

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