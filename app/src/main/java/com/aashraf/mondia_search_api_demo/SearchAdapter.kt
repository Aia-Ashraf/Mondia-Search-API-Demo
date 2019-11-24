package com.example.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.aashraf.mondia_search_api_demo.DetailsActivity
import com.aashraf.mondia_search_api_demo.R
import com.aashraf.mondia_search_api_demo.model.mondiaModels.DataModel
import kotlinx.android.synthetic.main.item_row.view.*

class ChapterAdapter(private val context: MainActivity, private var chaptersList: List<DataModel>) :
    RecyclerView.Adapter<ChapterAdapter.ViewHolder>() {
    lateinit var mainActivity: MainActivity
    lateinit var intent: Intent
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_row, parent, false))
    }

    override fun getItemCount(): Int {
        return chaptersList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.repoName?.text = chaptersList[position].title
        holder.itemView.setOnClickListener {
            intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("song", chaptersList[position].label)
            intent.putExtra("artist", 1)
            startActivity(context,intent, Bundle())
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val repoName = view.tv_item_row

    }

    fun updateItems(updatedItems: List<DataModel>) {
        chaptersList = updatedItems
        notifyDataSetChanged()
    }

}