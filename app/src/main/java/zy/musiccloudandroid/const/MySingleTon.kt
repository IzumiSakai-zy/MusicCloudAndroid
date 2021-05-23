package zy.musiccloudandroid.const

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import zy.musiccloudandroid.entity.Song
import kotlin.collections.ArrayList
import kotlin.random.Random

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

        private val media:MediaPlayer by lazy {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setOnPreparedListener {
                Log.d("MainActivity","setOnPrepareListener")
                Log.d("MainActivity","准备完成")
                it.start()
                Log.d("MainActivity","开始完成")
            }
            mediaPlayer.setOnCompletionListener {
                Log.d("MainActivity","播放完毕，开始下一首播放")
                it.reset()
                val index = Random.nextInt(MySingleTon.getSongArrayList().size)
                it.setDataSource("http://47.108.63.126:8001/song/download?singer=${songArrayList[index].singer}&songname=${songArrayList[index].name}")
                it.prepareAsync()
            }
            mediaPlayer
        }
        fun getMediaPlayer() = media


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