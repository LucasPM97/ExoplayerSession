package com.example.exoplayersession.ui.screens.playerPip.components

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
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


@Composable
fun PictureInPictureButton(
    modifier: Modifier,
    onClick: () -> Unit,
) {
    val context = LocalContext.current
    val isPipSupported by rememberSaveable {
        mutableStateOf(
            supportsPictureInPicture(context)
        )
    }

    if (!isPipSupported) return

    IconButton(
        modifier = modifier,
        onClick = onClick
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