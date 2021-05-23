package zy.musiccloudandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import zy.musiccloudandroid.adapter.SongAdapter
import zy.musiccloudandroid.const.MySingleTon
import zy.musiccloudandroid.entity.Song
import zy.musiccloudandroid.model.MainActivityViewModel
import zy.musiccloudandroid.model.MainViewModelProvider
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val result = JsonObjectRequest(Request.Method.GET,"http://47.108.63.126/api/songs",null,
            Response.Listener {
                Log.d("MainActivity",it.toString())
                val jsonArray = JSONObject(it.toString()).getJSONObject("data").getJSONArray("songs")
                Log.d("MainActivity",jsonArray.toString())

                MySingleTon.getSongArrayList().also {
                    it.clear()
                    for (i in 0..(jsonArray.length() - 1)){
                        it.add(MySingleTon.getGson().fromJson(jsonArray[i].toString(),Song::class.java))
                    }
                }
                songRecyclerView.adapter = SongAdapter(MySingleTon.getSongArrayList())
                songRecyclerView.layoutManager = LinearLayoutManager(this)
            },
            Response.ErrorListener {
                Log.d("MainActivity",it.toString())
            })
        MySingleTon.getInstance(this).addToRequestQueue(result)


        pauseButton.setOnClickListener {
            val mediaPlayer = MySingleTon.getMediaPlayer()
            if (mediaPlayer.isPlaying){
                mediaPlayer.pause()
            }else {
                mediaPlayer.start()
            }
        }

        previousSong.setOnClickListener {
            val mediaPlayer = MySingleTon.getMediaPlayer()
            val songList = MySingleTon.getSongArrayList()
            val index = Random.nextInt(MySingleTon.getSongArrayList().size)
            mediaPlayer.reset()
            mediaPlayer.setDataSource("http://47.108.63.126:8001/song/download?singer=${songList[index].singer}&songname=${songList[index].name}")
            mediaPlayer.prepareAsync()
        }

        nextSong.setOnClickListener {
            val mediaPlayer = MySingleTon.getMediaPlayer()
            val songList = MySingleTon.getSongArrayList()
            val index = Random.nextInt(MySingleTon.getSongArrayList().size)
            mediaPlayer.reset()
            mediaPlayer.setDataSource("http://47.108.63.126:8001/song/download?singer=${songList[index].singer}&songname=${songList[index].name}")
            mediaPlayer.prepareAsync()
        }

        seekTo.setOnClickListener {
            val mediaPlayer = MySingleTon.getMediaPlayer()

            mediaPlayer.seekTo(mediaPlayer.currentPosition + 30 * 1000)
        }


    }


}