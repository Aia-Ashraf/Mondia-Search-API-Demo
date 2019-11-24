package com.aashraf.mondia_search_api_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.search.MainActivity

class DetailsActivity : AppCompatActivity() {
lateinit var artist:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        artist=findViewById(R.id.tv_artist)
        val bundle:Bundle = intent.extras!!
        val song = bundle.get("song")
//        val language = bundle.get("language_value")
        artist.text=song.toString()

    }
}
