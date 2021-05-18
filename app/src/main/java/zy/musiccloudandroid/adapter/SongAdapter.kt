package zy.musiccloudandroid.adapter

import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import zy.musiccloudandroid.R
import zy.musiccloudandroid.entity.Song

class SongAdapter(val songList:ArrayList<Song>):
    RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val id = view.findViewById<TextView>(R.id.songIdTextView)
        val songName = view.findViewById<TextView>(R.id.songNameTextView)
        val singer = view.findViewById<TextView>(R.id.songSingerTextView)
        val button = view.findViewById<Button>(R.id.playButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.song_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.id.text = songList[position].id.toString()
        holder.singer.text = songList[position].singer
        holder.songName.text = songList[position].name
        holder.button.setOnClickListener {
            val mediaPlayer = MediaPlayer()
            if (mediaPlayer.isPlaying){
                mediaPlayer.stop()
            }
            Log.d("MainActivity","设置DataSource之前")
            //mediaPlayer.setDataSource("http://m7.music.126.net/20210516231841/e21466bce0f98a3dd3a9001b2be62153/ymusic/iLvYuJFiuSGt-2GzLoU8NQ==/509951163150639275")
            mediaPlayer.setDataSource("http://47.108.63.126:8001/song/download?singer=${songList[position].singer}&songname=${songList[position].name}")
            Log.d("MainActivity","设置DataSource之后")
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                Log.d("MainActivity","setOnPrepareListener")
                Log.d("MainActivity","准备完成")
                it.start()
                Log.d("MainActivity","开始完成")
            }
        }
    }
}