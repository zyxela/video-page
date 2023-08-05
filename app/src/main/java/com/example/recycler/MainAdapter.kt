package com.example.recycler

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return MainViewHolder(cellForRow)
    }


    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val video =homeFeed.videos[position]

        holder.itemView.findViewById<TextView>(R.id.tvDescription)?.text = video.name
        holder.itemView.findViewById<TextView>(R.id.tvChannelName)?.text = video.channel.name

        val thumbNailImageView = holder.itemView.findViewById<ImageView>(R.id.ivVideo)
        val channelImageView = holder.itemView.findViewById<ImageView>(R.id.ivChannel)
        Picasso.get().load(video.imageUrl).into(thumbNailImageView)
        Picasso.get().load("https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf").into(channelImageView)

        holder.video = video

    }

    override fun getItemCount(): Int {
        return homeFeed.videos.count()
    }

}

class MainViewHolder(itemView:View, var video: Video?=null): RecyclerView.ViewHolder(itemView)
{
    companion object{
        const val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        const val VIDEO_ID_KEY = "VIDEO_ID"
    }
    init {
        itemView.setOnClickListener{
            val intent = Intent(itemView.context, CourseDetailActivity::class.java)
            intent.putExtra(VIDEO_TITLE_KEY, video?.name)
            intent.putExtra(VIDEO_ID_KEY, video?.id)
            itemView.context.startActivity(intent)
        }

    }
}