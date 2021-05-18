package zy.musiccloudandroid.const

import android.media.MediaPlayer

object MyMediaPlayer {
    @Volatile
    var mediaPlayer:MediaPlayer? = null

    public final fun getMediaPlayer():MediaPlayer {

    }
}