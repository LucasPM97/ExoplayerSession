package com.example.exoplayersession.data

import android.app.PendingIntent
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.example.exoplayersession.R
import com.example.exoplayersession.domain.extensions.getParcelable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.parcelize.Parcelize

class PipBroadcastReceiver : BroadcastReceiver() {

    private var _actionState = MutableStateFlow(PlayerActions.None)
    val actionState = _actionState.asStateFlow()

    val intentFilter = IntentFilter().apply {
        addAction(CONTROLS_ACTION)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val result = intent?.getParcelable(
            EXTRA_CONTROLS_ACTION,
            PlayerActions::class.java
        ) ?: PlayerActions.None
        _actionState.update {
            result
        }

    }

    companion object {

        const val CONTROLS_ACTION = "controls_action"
        const val EXTRA_CONTROLS_ACTION = "extra_controls_Action"
        fun getPipActions(context: Context, isPlaying: Boolean): List<RemoteAction> {

            val createPendingIntent: (Int, PlayerActions) -> PendingIntent =
                { requestCode, action ->
                    val intent = Intent(CONTROLS_ACTION).apply {
                        setPackage(context.packageName)
                        putExtras(bundleOf(EXTRA_CONTROLS_ACTION to action))
                    }
                    PendingIntent.getBroadcast(
                        context,
                        requestCode,
                        intent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
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

    @Parcelize
    enum class PlayerActions : Parcelable { Play, Pause, None }
}