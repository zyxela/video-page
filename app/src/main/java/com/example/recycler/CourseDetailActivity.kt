package com.example.recycler

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CourseDetailActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView_main)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val navBarTitle = intent.getStringExtra(MainViewHolder.VIDEO_TITLE_KEY)
        supportActionBar?.title = navBarTitle

        fetchJSON()
    }

    private fun fetchJSON() {
        val videoId = intent.getIntExtra(MainViewHolder.VIDEO_ID_KEY, -1)
        val courseDetailUrl = "http://api.letsbuildthatapp.com/youtube/course_detail?id=$videoId"


        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object:Callback {
            override fun onFailure(call: Call, e: IOException) {
                print("FAIL")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                val gson = GsonBuilder().create()

                val courseLesson = gson.fromJson(body, Array<CourseLesson>::class.java)

                runOnUiThread{
                    findViewById<RecyclerView>(R.id.recyclerView_main).adapter = CourseDetailAdapter(courseLesson)
                }
            }

        })
    }
}

 class CourseDetailAdapter(val playlist: Array<CourseLesson>) : RecyclerView.Adapter<CourseDetailHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseDetailHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.course_row, parent, false)
        return CourseDetailHolder(view)
    }

    override fun onBindViewHolder(holder: CourseDetailHolder, position: Int) {
        val item = playlist[position]
        holder.itemView.findViewById<TextView>(R.id.tvVideoName)?.text = item.name
        holder.itemView.findViewById<TextView>(R.id.tvDuration)?.text = item.duration

        val thumbNailImageView = holder.itemView.findViewById<ImageView>(R.id.ivVid)

        Picasso.get().load(item.imageUrl).into(thumbNailImageView)

        holder.courseLesson = item

    }

    override fun getItemCount(): Int {
        return playlist.size
    }
}
class CourseDetailHolder(view: View, var courseLesson: CourseLesson?=null) : RecyclerView.ViewHolder(view){
   companion object{
       const val VIDEO_URL = "VIDEO_URL"
   }

    init {
        view.setOnClickListener{
            val intent = Intent(view.context, CourseActivity::class.java)
            intent.putExtra(VIDEO_URL, courseLesson?.link)
            view.context.startActivity(intent)
        }
    }
}

