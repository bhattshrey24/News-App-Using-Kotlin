package com.example.newsappusingkotlin.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsappusingkotlin.R
import com.example.newsappusingkotlin.data.models.News
import java.text.SimpleDateFormat

class NewsListRecyclerAdapter(var parentContext: Context) :
    RecyclerView.Adapter<NewsListRecyclerAdapter.ViewHolder>() {

    private var totalNumberOfArticles: Int = 0;
    private var articles: List<News>? = listOf()

    fun setNews(articles: List<News>) {
        this.articles = articles
        this.totalNumberOfArticles = articles.size
        notifyDataSetChanged()
    }

    private fun timeZoneToTimeConverter(timeStamp: String?): String { // converts timestamp to normal time
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("hh:mm a")
        val parsedDate = inputFormat.parse(timeStamp)
        return outputFormat.format(parsedDate) // returning the formatted date
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsListRecyclerAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_card_list_item, parent, false)
        return ViewHolder(view);
    }


    override fun onBindViewHolder(holder: NewsListRecyclerAdapter.ViewHolder, position: Int) {

        if (articles?.get(position)?.publishedAt == null) {
            holder.timeTv.text = "no time"
        } else {
            val time = timeZoneToTimeConverter(articles?.get(position)?.publishedAt)
            holder.timeTv.text = time
        }
        if (articles?.get(position)?.title == null) {
            holder.titleTV.text = "No Title"
        } else {
            holder.titleTV.text = articles?.get(position)?.title
        }
        if (articles?.get(position)?.urlToArticle == null) { // ie. if no url is given by api then show not found wali image
            Log.d("Error", "Inside NewsListAdapter")
            holder.articleImage.setImageResource(R.drawable.image_not_found)
        } else {
            try {
                Glide.with(parentContext)
                    .load(articles!![position].urlToImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.articleImage)
            } catch (e: Exception) {
                Log.d("Error", "inside newsListAdapter" + e.message.toString())
                holder.articleImage.setImageResource(R.drawable.image_not_found)
            }
        }
    }

    override fun getItemCount(): Int {
        return totalNumberOfArticles
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var articleImage: ImageView = itemView.findViewById(R.id.articles_image_view)
        var timeTv: TextView = itemView.findViewById(R.id.Tv_timeOfArticle)
        var titleTV: TextView = itemView.findViewById(R.id.Tv_title)
    }
}