package zy.musiccloudandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val songList = ArrayList<Song>()

        val gson = Gson()

        val result = JsonObjectRequest(Request.Method.GET,"http://47.108.63.126/api/songs",null,
            Response.Listener {
                Log.d("MainActivity",it.toString())
                val jsonArray = JSONObject(it.toString()).getJSONObject("data").getJSONArray("songs")
                Log.d("MainActivity",jsonArray.toString())
                songList.apply {
                    for (i in 0..jsonArray.length() - 1){
                        add(gson.fromJson(jsonArray[i].toString(),Song::class.java))
                    }
                }
                songRecyclerView.adapter = SongAdapter(songList)
                songRecyclerView.layoutManager = LinearLayoutManager(this)
            },
            Response.ErrorListener {
                Log.d("MainActivity",it.toString())
            })
        MySingleTon.getInstance(this).addToRequestQueue(result)
    }
}