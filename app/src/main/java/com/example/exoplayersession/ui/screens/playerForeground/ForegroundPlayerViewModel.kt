package com.example.exoplayersession.ui.screens.playerForeground

import androidx.lifecycle.ViewModel
import androidx.media3.common.Player

class ForegroundPlayerViewModel(
    val player: Player
) : ViewModel() {


    init {
        player.prepare()
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}