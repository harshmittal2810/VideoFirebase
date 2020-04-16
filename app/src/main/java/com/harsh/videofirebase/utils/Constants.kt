@file:JvmName("Constants")

package com.harsh.videofirebase.utils

import android.Manifest

val REQUEST_VIDEO_PERMISSIONS = 1
val STORAGE_PERMISSION = 101
val VIDEO_PERMISSIONS = arrayOf(
    Manifest.permission.CAMERA,
    Manifest.permission.RECORD_AUDIO,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)