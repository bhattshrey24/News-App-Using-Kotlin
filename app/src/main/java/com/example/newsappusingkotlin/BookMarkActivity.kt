package com.example.newsappusingkotlin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.adapters.NewsListRecyclerAdapter
import com.example.newsappusingkotlin.data.cache.SavedNewsEntity
import com.example.newsappusingkotlin.data.models.News
import com.example.newsappusingkotlin.data.models.Source
import com.example.newsappusingkotlin.databinding.ActivityBookMarkBinding
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCache
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCacheFactory

class BookMarkActivity : AppCompatActivity(),
    NewsListRecyclerAdapter.OnBookmarkButtonListener,
    NewsListRecyclerAdapter.OnNewsArticleClickListener { // using same adapter that we used in articles Page

    val binding: ActivityBookMarkBinding by lazy {
        ActivityBookMarkBinding.inflate(layoutInflater, null, false)
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: NewsListRecyclerAdapter? = null
    private lateinit var viewModelForCache: ViewModelForCache
    private val listOfNewsArticles: MutableList<News>? = ArrayList()
    private var savedListOfNewsArticles: List<SavedNewsEntity>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Bookmarked Articles"
        setupRecyclerView()
        getNewsArticlesFromCache()
        supportActionBar?.setDisplayHomeAsUpEnabled(true); // this is enabling the back button in actionBar
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_my_back_button) // this changes the icon of back button
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // use navigation component instead
        finish()//simply finishing the current activity ie. going back when user presses the back button in actionBar
        return true
        //return super.onOptionsItemSelected(item)
    }

    private fun getNewsArticlesFromCache() {
        val viewModelCacheFactory = ViewModelForCacheFactory(application)
        viewModelForCache = ViewModelProvider(
            this,
            viewModelCacheFactory
        ).get(ViewModelForCache::class.java)

        viewModelForCache.getAllArticles().observe(this, Observer {

            if (it.isEmpty()) {
                binding.TVEmptyBookMarkNotifier.visibility = View.VISIBLE
            } else {
                binding.TVEmptyBookMarkNotifier.visibility = View.GONE
            }

            savedListOfNewsArticles = it

            for (news in it) {
                val source = Source(
                    "",
                    ""
                ) // just adding dummy source to each Article , cause Im not using it either way
                val savedNewsArticle = News(
                    source,
                    news.author,
                    news.title,
                    news.description,
                    news.urlToArticle,
                    news.urlToImage,
                    news.publishedAt,
                    news.content
                )
                listOfNewsArticles?.add(savedNewsArticle)
            }

            adapter?.setNews(listOfNewsArticles!!)
        })

    }

    private fun setupRecyclerView() {
        layoutManager =
            GridLayoutManager(applicationContext, 2) // this 2 is basically number of columns u want
        binding.recyclerViewBookMark.layoutManager = layoutManager
        adapter =
            applicationContext?.let {
                NewsListRecyclerAdapter(it, this, this)
            } // sending context to adapter so that Glide can use it
        binding.recyclerViewBookMark.adapter = adapter
    }

    override fun onBookmarkButtonClick(position: Int) {
        Toast.makeText(applicationContext, "Deleted Article", Toast.LENGTH_SHORT)
            .show()
        viewModelForCache.deleteNewsArticle(savedListOfNewsArticles!!.get(position))
        listOfNewsArticles?.clear()// This makes sure that when we delete then old data is not written over new data ie. live data will be updated after deleting and then again listOfNewsArticles will again be filled so previous data that was present in list should be removed that is why I used clear()
    }

    private fun convertObjectFromSavedNewsEntityToNews(savedNewsEntity: SavedNewsEntity?): News {
        return News(
            Source("", ""),
            savedNewsEntity?.author,
            savedNewsEntity?.title,
            savedNewsEntity?.description,
            savedNewsEntity?.urlToArticle,
            savedNewsEntity?.urlToImage,
            savedNewsEntity?.publishedAt,
            savedNewsEntity?.content
        )
    }

    override fun onNewsArticleClick(position: Int) {
        var intent = Intent(this, NewsArticleDisplayActivity::class.java)
        val news = convertObjectFromSavedNewsEntityToNews(savedListOfNewsArticles?.get(position))

        intent.putExtra(
            Constants.objectPassingThroughIntentKey,
            news
        )
        startActivity(intent)
    }

}