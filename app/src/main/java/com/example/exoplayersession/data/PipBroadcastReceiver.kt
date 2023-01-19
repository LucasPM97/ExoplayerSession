package com.example.exoplayersession.data

import android.app.PendingIntent
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.core.os.bundleOf
import com.example.exoplayersession.R

class PipBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        print("Try to pause the video")
    }

    companion object {

        const val CONTROLS_ACTION = "controls_action"
        const val EXTRA_CONTROLS_ACTION = "extra_controls_Action"
        fun getPipActions(context: Context, isPlaying: Boolean): List<RemoteAction> {

            val createPendingIntent: (Int, PlayerActions) -> PendingIntent =
                { requestCode, action ->
                    val intent = Intent(CONTROLS_ACTION).apply {
                        setPackage(context.packageName)
                        putExtras(bundleOf(EXTRA_CONTROLS_ACTION to type))
                    }
                    PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE
                    )
                }

            val playPauseAction = if (isPlaying) {
                RemoteAction(
                    Icon.createWithResource(context, R.drawable.ic_baseline_pause_24),
                    context.getString(R.string.pip_pause_title),
                    context.getString(R.string.pip_pause_description),
                    createPendingIntent(1, PlayerActions.Pause)
                )
            } else {
                RemoteAction(
                    Icon.createWithResource(context, R.drawable.baseline_play_arrow_24),
                    context.getString(R.string.pip_play_title),
                    context.getString(R.string.pip_play_description),
                    createPendingIntent(1, PlayerActions.Play)
                )
            }

            return listOf(playPauseAction)
        }
    }

    enum class PlayerActions { Play, Pause }
}