package com.example.newsappusingkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.R

class NewsListRecyclerAdapter : RecyclerView.Adapter<NewsListRecyclerAdapter.ViewHolder>() {
    private var place = arrayOf("delhi", "Kolkata", "Chennai", "Tamil nadu", "Uttrakhand")
    private var time = arrayOf("10:15", "2:12", "3:20", "4:20", "5:48")

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsListRecyclerAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_card_list_item, parent, false)
        return ViewHolder(view);
    }

    override fun onBindViewHolder(holder: NewsListRecyclerAdapter.ViewHolder, position: Int) {
    holder.placeTv.text=place[position]
      holder.timeTv.text=time[position]
    }

    override fun getItemCount(): Int {
        return place.size // change it later to size of headings or images since every article will have a image or heading
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //  var articleImage:ImageView // add this when u add api properly
        var placeTv: TextView
        var timeTv: TextView

        init {
            placeTv = itemView.findViewById(R.id.Tv_placeOfArticle)
            timeTv = itemView.findViewById(R.id.Tv_timeOfArticle)
        }
    }
}