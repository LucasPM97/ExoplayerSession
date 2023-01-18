package com.example.exoplayersession.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.exoplayersession.ui.theme.ExoPlayerSessionTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ExoPlayerSessionTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    BackgroundPlayerScreen()
//                }
            }
        }

        startActivity(
            Intent(this, PipPlayerActivity::class.java)
        )
        finish()
    }
}