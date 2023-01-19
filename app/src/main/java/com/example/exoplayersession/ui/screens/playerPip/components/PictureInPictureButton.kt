package com.example.exoplayersession.ui.screens.playerPip.components

import android.app.Activity
import android.app.PictureInPictureParams
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Rect
import android.util.Rational
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.exoplayersession.R
import androidx.compose.runtime.*
import com.example.exoplayersession.data.PipBroadcastReceiver


@Composable
fun PictureInPictureButton(
    isPlaying: Boolean,
    videoViewBounds: Rect,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current
    val isPipSupported by rememberSaveable {
        mutableStateOf(
            supportsPictureInPicture(context)
        )
    }

    if (!isPipSupported) return

    fun handleOnClick() {
        if (context !is Activity) return

        updatePipParams(context, videoViewBounds, isPlaying)?.let { params ->
            context.enterPictureInPictureMode(params)
        }

    }


    IconButton(
        modifier = modifier,
        onClick = { handleOnClick() }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_picture_in_picture_white_24),
            contentDescription = "picture in picture",
            tint = Color.Black
        )
    }
}

private fun supportsPictureInPicture(context: Context): Boolean {
    return if (context !is Activity) false
    else {
        context.packageManager.hasSystemFeature(
            PackageManager.FEATURE_PICTURE_IN_PICTURE
        )
    }
}

private fun updatePipParams(
    context: Context,
    videoViewBounds: Rect,
    isPlaying: Boolean
): PictureInPictureParams? {
    return PictureInPictureParams.Builder()
        .setSourceRectHint(videoViewBounds)
        .setAspectRatio(Rational(16, 9))
        .setActions(
            PipBroadcastReceiver.getPipActions(
                context,
                isPlaying
            )
        ).build()
}