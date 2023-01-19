package com.example.exoplayersession.domain.extensions

import android.content.Intent
import android.os.Build

fun <T> Intent.getParcelable(name: String, parcelToClass: Class<T>): T? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(name, parcelToClass)
    } else {
        getParcelableExtra(name)
    }