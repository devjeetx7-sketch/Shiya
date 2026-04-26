package com.synfusion.shiya.voice

import android.content.Context
import android.media.MediaPlayer

class AudioAssetsPlayer(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null

    fun playWakeWordInstantResponse() {
        // Play one of the dummy files from assets/audio/
        // Example: wake_haan_bolo.mp3
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }
}
