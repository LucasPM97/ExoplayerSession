package com.example.exoplayersession

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.exoplayersession.ui.screens.player.PlayerScreen
import com.example.exoplayersession.ui.screens.player.PlayerViewModel
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExoPlayerSessionTheme {

                val viewModel: PlayerViewModel = getViewModel()
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlayerScreen(viewModel)
                }
            }
        }
    }
}

