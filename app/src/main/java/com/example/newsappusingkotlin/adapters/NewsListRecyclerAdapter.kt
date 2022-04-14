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

    fun setNews(articles: List<News>, totalNumberOfArticles: Int) {
        this.articles = articles
        this.totalNumberOfArticles = totalNumberOfArticles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsListRecyclerAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.news_card_list_item, parent, false)
        return ViewHolder(view);
    }

    private fun timeZoneToTimeConverter(timeStamp: String?): String { // converts timestamp to normal time
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val outputFormat = SimpleDateFormat("hh:mm a")
        val parsedDate = inputFormat.parse(timeStamp)
        val formattedDate = outputFormat.format(parsedDate)
        return formattedDate
    }

    var counter = 0;
    override fun onBindViewHolder(holder: NewsListRecyclerAdapter.ViewHolder, position: Int) {
//        Log.d("Error" , "total number artile in On Bind: $totalNumberOfArticles")
//        Log.d("Error" , "position in On Bind: $position")
//        Log.d("Error" , "arrayListSize in On Bind: ${articles?.size}")
//        Log.d("Error" , "arrayListSize in On Bind: $articles")

        if (articles?.get(position)?.publishedAt == null || articles?.get(position)?.publishedAt.equals(
                ""
            )
        ) {
            holder.timeTv.text = "no time"
        } else {
            val time = timeZoneToTimeConverter(articles?.get(position)?.publishedAt)
            holder.timeTv.text = time
        }

        if (articles?.get(position)?.urlToArticle == null || articles?.get(position)?.urlToArticle.equals(
                ""
            )
        ) { // ie. if no url if given by api then show not found wali image
            Log.d("ErrorImage", "Inside and url is =  $articles?.get(position)?.urlToArticle")
            holder.articleImage.setImageResource(R.drawable.image_not_found)
        } else {
            Log.d("ErrorImage", "Inside else ${counter++}")
            holder.articleImage.setImageResource(R.drawable.image_not_found)

            try {
                Glide.with(parentContext)
                    .load(articles!![position].urlToImage)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.articleImage)
            } catch (e: Exception) {
                Log.d("Error in catch", e.message.toString())
                holder.articleImage.setImageResource(R.drawable.image_not_found)
            }
        }
    }

    override fun getItemCount(): Int {
        if (articles?.size == 0) {
            return 0
        }
        return articles!!.size // change it later to size of headings or images since every article will have a image or heading
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var articleImage: ImageView // add this when u add api properly
        var timeTv: TextView
        init {
            timeTv = itemView.findViewById(R.id.Tv_timeOfArticle)
            articleImage = itemView.findViewById(R.id.articles_image_view)
        }
    }
}