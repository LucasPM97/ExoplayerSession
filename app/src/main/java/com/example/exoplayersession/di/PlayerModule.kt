package com.example.exoplayersession.di

import androidx.media3.common.AudioAttributes
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val playerModule = module {
    single<Player> {
        ExoPlayer.Builder(androidContext())
            .setAudioAttributes(AudioAttributes.DEFAULT, true)
            .setHandleAudioBecomingNoisy(true)
            .build().apply {
                val mediaItem =
                    MediaItem.fromUri("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4")
                setMediaItem(mediaItem)
            }
    }
    single {
        MediaSession.Builder(androidContext(), get())
            .build()
    }
}