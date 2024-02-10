package com.example.memeadda
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import android.widget.ProgressBar
import android.widget.Toast
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target



class MainActivity : AppCompatActivity() {
        var currentImageURL: String?=null
    private lateinit var imageView: ImageView // Define imageView as a property of the class
    private lateinit var pBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView) // Initialize imageView in onCreate()
        pBar = findViewById(R.id.pBar) // Initialize pBar in onCreate()

        loadmeme() // Call loadmeme function to load meme on activity creation
    }

    private fun loadmeme() {
        pBar.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.com/gimme"

        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
              currentImageURL = response.getString("url")
                Glide.with(this). load(currentImageURL).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                       pBar.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                       pBar.visibility=View.GONE
                       return false
                    }
                })
                .into(imageView)
            },
            Response.ErrorListener {
                Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show()
            })

        queue.add(JsonObjectRequest)
    }



    fun Sharememe(view: View) {
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hello Buddy! CheckOut this cool meme on MemeAdda $currentImageURL Dowanload this cool app MemeAdda. All rights reserverd to Kashyap")
        val chooser=Intent.createChooser(intent,"Share this meme using !")
        startActivity(chooser)
    }

    fun nextmeme(view: View) {
        loadmeme()

    }
}
