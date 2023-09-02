package com.demo.canyouhearme.common.helper.media

import android.content.res.AssetFileDescriptor

class DefaultMediaPlayer: MediaPlayer {

    private lateinit var mediaPlayer: android.media.MediaPlayer

    override fun start(onFinish: () -> Unit) {
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.prepare()
            mediaPlayer.start()
            mediaPlayer.setOnCompletionListener {
                onFinish()
            }
        }
    }

    override fun addSource(assetFileDescriptor: AssetFileDescriptor) {
        mediaPlayer = android.media.MediaPlayer()
        mediaPlayer.setDataSource(assetFileDescriptor)
    }

    override fun stop() {
        if (this::mediaPlayer.isInitialized) mediaPlayer.stop()
    }
}