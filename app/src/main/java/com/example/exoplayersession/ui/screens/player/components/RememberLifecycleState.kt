package com.example.exoplayersession.ui.screens.player.components

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle

@Composable
fun rememberLifecycleState(): MutableState<Lifecycle.Event> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState = remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycleState.value = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }

    }

    return lifecycleState
}