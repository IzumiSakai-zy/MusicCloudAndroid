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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val result = JsonObjectRequest(Request.Method.GET,"http://47.108.63.126/api/songs",null,
//            Response.Listener {
//                Log.d("MainActivity",it.toString())
//                val jsonArray = JSONObject(it.toString()).getJSONObject("data").getJSONArray("songs")
//                Log.d("MainActivity",jsonArray.toString())
//
//                MySingleTon.getSongArrayList().also {
//                    for (i in 0..(jsonArray.length() - 1)){
//                        it.add(MySingleTon.getGson().fromJson(jsonArray[i].toString(),Song::class.java))
//                    }
//                }
//                songRecyclerView.adapter = SongAdapter(MySingleTon.getSongArrayList())
//                songRecyclerView.layoutManager = LinearLayoutManager(this)
//            },
//            Response.ErrorListener {
//                Log.d("MainActivity",it.toString())
//            })
//        MySingleTon.getInstance(this).addToRequestQueue(result)


        //val viewModel:MainActivityViewModel by viewModels()
        val viewModel:MainActivityViewModel by viewModels {
            MainViewModelProvider(this)
        }

        viewModel.getSongs().observe(this, Observer<ArrayList<Song>> {
            Log.d("MainActivity","调用observe方法")
            songRecyclerView.adapter = SongAdapter(it)
            Log.d("MainActivity",it.toString())
        })

        Log.d("MainActivity","准备执行adapter")
        if (viewModel.getSongs().value == null){
            Log.d("MainActivity","为null")
        }else{
            Log.d("MainActivity","非null")
        }

        songRecyclerView.adapter = SongAdapter(viewModel.getSongs().value?:ArrayList())
    }

}