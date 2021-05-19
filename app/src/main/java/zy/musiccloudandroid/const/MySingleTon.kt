package zy.musiccloudandroid.const

import android.content.Context
import android.media.MediaPlayer
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import zy.musiccloudandroid.entity.Song

class MySingleTon(context: Context) {
    companion object{
        @Volatile
        private var INSTANCE: MySingleTon? = null
        fun getInstance(context: Context) =
                INSTANCE?: synchronized(this){
                    INSTANCE?: MySingleTon(context).also {
                        INSTANCE = it
                    }
                }

        @Volatile
        private var mediaPlayer:MediaPlayer? = null
        fun getMediaPlayer() =
                mediaPlayer?: synchronized(this){
                    mediaPlayer?:MediaPlayer().also {
                        mediaPlayer = it
                    }
                }

        private val songArrayList:ArrayList<Song> = ArrayList<Song>()
        fun getSongArrayList() = songArrayList

        private val gson:Gson = Gson()
        fun getGson() = gson
    }

    private val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context.applicationContext)
    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }
}