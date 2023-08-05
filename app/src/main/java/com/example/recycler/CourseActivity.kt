package com.example.recycler

import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity

class CourseActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)
        val webView = findViewById<WebView>(R.id.webView)
        val link = intent.getStringExtra(CourseDetailHolder.VIDEO_URL)

        webView.settings.javaScriptEnabled =true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true

        webView.loadUrl(link!!)
    }
}