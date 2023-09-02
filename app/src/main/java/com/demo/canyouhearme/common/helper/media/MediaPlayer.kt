package com.demo.canyouhearme.common.helper.media

import android.content.res.AssetFileDescriptor

interface MediaPlayer {
    fun start(onFinish: () -> Unit)
    fun addSource(assetFileDescriptor: AssetFileDescriptor)
    fun stop()
}