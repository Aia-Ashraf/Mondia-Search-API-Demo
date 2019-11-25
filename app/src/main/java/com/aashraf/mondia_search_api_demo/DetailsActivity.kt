package com.aashraf.mondia_search_api_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.search.MainActivity

class DetailsActivity : AppCompatActivity() {
lateinit var artist:TextView
lateinit var song:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        artist=findViewById(R.id.tv_artist)
        song=findViewById(R.id.song_name)
        val bundle:Bundle = intent.extras!!
        val songg = bundle.get("song")
        val artistt = bundle.get("artist")
        artist.text="Artist Name :- "+artistt.toString()
        song.text="Song Name :- "+songg.toString()
    }
}
