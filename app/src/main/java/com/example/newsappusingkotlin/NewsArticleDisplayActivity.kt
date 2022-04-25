package com.example.newsappusingkotlin

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsappusingkotlin.data.models.News
import com.example.newsappusingkotlin.databinding.ActivityNewsArticleDisplayBinding
import com.example.newsappusingkotlin.other.Constants

class NewsArticleDisplayActivity : AppCompatActivity() {

    private val binding: ActivityNewsArticleDisplayBinding by lazy {
        ActivityNewsArticleDisplayBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val receivedNews: News? =
            intent.getParcelableExtra<News>(Constants.objectPassingThroughIntentKey)

        setUpDataIntoViews(receivedNews)

        title = "Article Page"
        supportActionBar?.setDisplayHomeAsUpEnabled(true); // this is enabling the back button in actionBar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_my_back_button) // this changes the icon of back button
    }

    private fun setUpDataIntoViews(receivedNews: News?) {
        try {
            Glide.with(this)
                .load(receivedNews?.urlToImage)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.IvNewsArticleDisplay)
        } catch (e: Exception) {
            Log.d("Error", "inside newsListAdapter" + e.message.toString())
            binding.IvNewsArticleDisplay.setImageResource(R.drawable.image_not_found)
        }
        binding.TvTitleNewsArticleDisplay.text = receivedNews?.title
        binding.TvTimeNewsArticleDisplay.text = receivedNews?.publishedAt
        binding.TvContentNewsArticleDisplay.text = receivedNews?.content
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // use navigation component instead
        finish()//simply finishing the current activity ie. going back when user presses the back button in actionBar
        return true
        //return super.onOptionsItemSelected(item)
    }

}