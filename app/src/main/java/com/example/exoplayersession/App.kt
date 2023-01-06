package com.example.exoplayersession

import android.app.Application
import com.example.exoplayersession.di.playerModule
import com.example.exoplayersession.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@App)
            // Load modules
            modules(playerModule, viewModelModule)
        }
    }
}