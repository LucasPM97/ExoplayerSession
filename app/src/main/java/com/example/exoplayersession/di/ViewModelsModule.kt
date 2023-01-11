package com.example.exoplayersession.di

import com.example.exoplayersession.ui.screens.playerForeground.ForegroundPlayerViewModel
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val viewModelModule = module {
    viewModel {
        ForegroundPlayerViewModel(get())
    }
}