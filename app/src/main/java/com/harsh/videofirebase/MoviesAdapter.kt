package com.harsh.videofirebase

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.rtoshiro.view.video.FullscreenVideoView
import com.harsh.videofirebase.utils.Upload

class MoviesAdapter(
    private val videoList: MutableList<Upload>
) : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.row_video_home, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val uploads = videoList[position]
        holder.name.text = uploads.name
        holder.fullScreenVideoView.setVideoURI(Uri.parse(uploads.url))
        holder.fullScreenVideoView.seekTo(0)
        holder.fullScreenVideoView.requestFocus()
        holder.fullScreenVideoView.setOnPreparedListener {
            it.isLooping = true
            holder.fullScreenVideoView.start()
        }
    }

    override fun getItemCount(): Int {
        return videoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fullScreenVideoView: FullscreenVideoView = itemView.findViewById(R.id.video_view_home)
        var name: TextView = itemView.findViewById(R.id.name)
    }
}