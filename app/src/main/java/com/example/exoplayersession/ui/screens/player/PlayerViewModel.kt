package com.example.exoplayersession.ui.screens.player

import androidx.lifecycle.ViewModel
import androidx.media3.common.Player

class PlayerViewModel(
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