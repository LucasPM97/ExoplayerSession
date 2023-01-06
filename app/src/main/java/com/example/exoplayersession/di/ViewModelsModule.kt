package com.example.exoplayersession.di

import com.example.exoplayersession.ui.screens.player.PlayerViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel {
        PlayerViewModel(get())
    }
}