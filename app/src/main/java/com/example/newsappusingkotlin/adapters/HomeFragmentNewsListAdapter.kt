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

class HomeFragmentNewsListAdapter(var parentContext: Context) :
    RecyclerView.Adapter<HomeFragmentNewsListAdapter.ViewHolder>() {

    private var totalNumberOfArticles: Int = 0;
    private var articles: List<News>? = listOf()

    fun setNews(articles: List<News>) {
        this.articles = articles
        this.totalNumberOfArticles = articles.size
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HomeFragmentNewsListAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.homepage_news_card_list_item, parent, false)
        return ViewHolder(view);
    }
    private fun timeZoneToTimeConverter(timeStamp: String?): String { // converts timestamp to normal time
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("hh:mm a")
        val parsedDate = inputFormat.parse(timeStamp)
        return outputFormat.format(parsedDate) // returning the formatted date
    }

    override fun onBindViewHolder(holder: HomeFragmentNewsListAdapter.ViewHolder, position: Int) {
        if (articles?.get(position)?.publishedAt == null) {
            holder.articleTime.text = "no time"
        } else {
            val time = timeZoneToTimeConverter(articles?.get(position)?.publishedAt)
            holder.articleTime.text = time
        }
        if (articles?.get(position)?.title == null) {
            holder.articleTitle.text = "No Title"
        } else {
            holder.articleTitle.text = articles?.get(position)?.title
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
        var articleImage: ImageView = itemView.findViewById(R.id.IVHome_article_image)
        var articleTitle: TextView = itemView.findViewById(R.id.TVHome_title)
        var articleTime: TextView = itemView.findViewById(R.id.TVHome_time)
    }
}