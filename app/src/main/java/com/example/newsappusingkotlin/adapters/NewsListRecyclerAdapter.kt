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
import com.example.newsappusingkotlin.other.Constants
import java.text.SimpleDateFormat

class NewsListRecyclerAdapter(
    var parentContext: Context,
    var onBookMarkBtnListener: NewsListRecyclerAdapter.OnBookmarkButtonListener,
    var onNewsArticleClickListener: OnNewsArticleClickListener
) :
    RecyclerView.Adapter<NewsListRecyclerAdapter.ViewHolder>() {

    private var totalNumberOfArticles: Int = 0;
    private var articles: List<News>? = listOf()

    fun setNews(articles: List<News>) {
        this.articles = listOf() // clearing the data
        this.articles = articles
        this.totalNumberOfArticles = articles.size
        notifyDataSetChanged()
    }

    private fun timeZoneToTimeConverter(ts: String?): String? {
//        Log.d(Constants.currentDebugTag, " Timestamp is $ts")
//        val time = ts?.substring(11, 19) // taking out just the 'time' from the timestamp
//        Log.d(Constants.currentDebugTag, " Time is : $time")
//        var inputFormat = SimpleDateFormat("HH:mm:ss")
//        val outputFormat = SimpleDateFormat("hh:mm a")
        val time = ts?.substring( 0,10) // taking out just the 'time' from the timestamp
      //  Log.d(Constants.currentDebugTag, " Time is : $time")
        var inputFormat = SimpleDateFormat("yyyy-mm-dd")
        val outputFormat = SimpleDateFormat("dd-mm-yyyy")
        val parsedDate = inputFormat.parse(time)
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
        try {
            val time = timeZoneToTimeConverter(articles?.get(position)?.publishedAt)
            holder.timeTv.text = time
        } catch (e: Exception) {
            Log.d(
                Constants.currentDebugTag,
                "Caught Exception while converting timestamp ${e.message}"
            )
        }
        if (articles?.get(position)?.title == null) {
            holder.titleTV.text = "No Title"
        } else {
            holder.titleTV.text = articles?.get(position)?.title
        }

        if (articles?.get(position)?.urlToArticle == null||articles?.get(position)?.urlToArticle.toString().isEmpty()) { // ie. if no url is given by api then show not found wali image
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
        var btnBookmark: ImageView = itemView.findViewById(R.id.btn_bookmark)


        init {
            btnBookmark.setOnClickListener {
                val pos = adapterPosition
                onBookMarkBtnListener.onBookmarkButtonClick(pos) // this is my listener method , ie. im using listener mechanism to communicate with fragment
            }
            itemView.setOnClickListener {
                val pos = adapterPosition
                onNewsArticleClickListener.onNewsArticleClick(pos)
            }
        }

    }

    interface OnBookmarkButtonListener {
        fun onBookmarkButtonClick(position: Int)
    }

    interface OnNewsArticleClickListener {
        fun onNewsArticleClick(position: Int)
    }

}