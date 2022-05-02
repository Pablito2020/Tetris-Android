package com.pablo.tetris.infra.music

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.pablo.tetris.presentation.common.MUSIC_RESOURCE_ID

class TetrisMusicService : Service() {

    private var musicPlayer: MediaPlayer? = null

    override fun onBind(p0: Intent?): IBinder = throw NotImplementedError("Unsupported")

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent != null && intent.hasExtra(MUSIC_RESOURCE_ID)) {
            musicPlayer = MediaPlayer.create(this, intent.getIntExtra(MUSIC_RESOURCE_ID, -1))
            musicPlayer!!.isLooping = true
            musicPlayer!!.start()
        }
        return startId
    }

    override fun onDestroy() {
        super.onDestroy()
        if (musicPlayer != null && musicPlayer!!.isPlaying)
            musicPlayer!!.stop()
    }

}