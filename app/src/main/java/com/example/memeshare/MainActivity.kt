package com.example.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target


class MainActivity : AppCompatActivity() {
    var currentImageUrl :String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
fun loadMeme(){
    var progressBar = findViewById<ProgressBar>(R.id.progressBar)

    progressBar.visibility = View.VISIBLE
    // Instantiate the RequestQueue.

    val url = "https://meme-api.com/gimme"

// Request a string response from the provided URL.
    val jsonObjectRequest = JsonObjectRequest(
        Request.Method.GET, url, null,
        Response.Listener{ response ->
            currentImageUrl = response.getString("url")
            val memeImageView = findViewById<ImageView>(R.id.memeImageView)
            Glide.with(this).load(currentImageUrl).listener(object: RequestListener<Drawable>{
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                 progressBar.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar.visibility = View.GONE
                    return false
                }
            }).into(memeImageView)

        },
        Response.ErrorListener {

        })

// Add the request to the RequestQueue.
   MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
}


    fun ShareMeme(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey checkout this cool Meme I got from Reddit $currentImageUrl")
        val chooser = Intent.createChooser(intent, "Share this Meme using...")
        startActivity(chooser)

    }
    fun NextMeme(view: View) {
loadMeme()
    }
}