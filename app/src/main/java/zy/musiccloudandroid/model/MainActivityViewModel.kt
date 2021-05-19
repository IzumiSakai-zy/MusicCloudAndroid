package zy.musiccloudandroid.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject
import zy.musiccloudandroid.const.MySingleTon
import zy.musiccloudandroid.entity.Song

class MainActivityViewModel(val context: Context): ViewModel() {
    private val songs:MutableLiveData<ArrayList<Song>> by lazy {
        MutableLiveData<ArrayList<Song>>().also {
            loadSongs(it)
        }
    }

    fun getSongs(): LiveData<ArrayList<Song>> {
        return songs
    }

    private fun loadSongs(songs:MutableLiveData<ArrayList<Song>>){
        val result:JSONArray? = null
        val request = JsonObjectRequest(Request.Method.GET,"http://47.108.63.126/api/songs",null,
                Response.Listener {
                    Log.d("MainActivityViewModel",it.toString())
                    val jsonArray = JSONObject(it.toString()).getJSONObject("data").getJSONArray("songs")
                    Log.d("MainActivityViewModel",jsonArray.toString())

                    songs.value.also {
                        for (i in 0 until jsonArray.length()){
                            it?.add(MySingleTon.getGson().fromJson(jsonArray[i].toString(),Song::class.java))
                        }
                    }
                },
                Response.ErrorListener {
                    Log.d("MainActivityViewModel",it.toString())
                })
        MySingleTon.getInstance(context).addToRequestQueue(request)
    }
}