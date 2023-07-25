package com.example.appsessions

import android.os.Bundle
import android.webkit.WebView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val title = intent.getStringExtra("title")
        val url = intent.getStringExtra("url")

        // Aquí puedes utilizar el título y el ID del video como desees,
        // como mostrar el título en un TextView y reproducir el video de YouTube.
        // Por ejemplo, para mostrar el título en un TextView:
        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        titleTextView.text = title

        // Luego, para reproducir el video de YouTube, puedes cargar el URL completo en el WebView:
        val webView = findViewById<WebView>(R.id.webView)
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.youtube.com/watch?v=$url")
    }
}
