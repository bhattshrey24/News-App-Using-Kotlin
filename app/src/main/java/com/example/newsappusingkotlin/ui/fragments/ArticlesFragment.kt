package com.example.newsappusingkotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.adapters.NewsListRecyclerAdapter
import com.example.newsappusingkotlin.data.cache.SavedNewsEntity
import com.example.newsappusingkotlin.data.models.News
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.FragmentArticlesBinding
import com.example.newsappusingkotlin.ui.viewmodels.MainViewModel
import com.example.newsappusingkotlin.ui.viewmodels.MainViewModelFactory
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCache
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCacheFactory


class ArticlesFragment : Fragment(), NewsListRecyclerAdapter.OnBookmarkButtonListener {

    private val binding: FragmentArticlesBinding by lazy { // so that layout binding only happens when need , It improves performance
        FragmentArticlesBinding.inflate(layoutInflater, null, false)
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: NewsListRecyclerAdapter? = null
    private lateinit var viewModel: MainViewModel
    private lateinit var viewModelForCache: ViewModelForCache
    private lateinit var listOfNewsArticle: List<News>
    private var category: String = "business"

    override fun onCreate(savedInstanceState: Bundle?) {
        val cat = arguments?.getString("category", "business")
        category = cat ?: "business"
        Log.d("Zoo Check", "$category")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView( // Observe It's not OnCreate It's "OnCreateView" , here we bind the layout of the fragment
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.circularProgressBarArticlesPage.visibility = View.VISIBLE

        setupRecyclerView()
        setupViewModelAndCallingForNewsArticles()

        val viewModelCacheFactory = ViewModelForCacheFactory(requireActivity().application)

        viewModelForCache = ViewModelProvider(
            requireActivity(),
            viewModelCacheFactory
        ).get(ViewModelForCache::class.java)


        return binding.root
    }

    private fun setupViewModelAndCallingForNewsArticles() {
        val repository = MyRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)

        viewModel.getPost("in") // in is the code for "India"

        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response ", "Response is : $response")
            adapter?.setNews(response.articles)
            listOfNewsArticle = response.articles
            binding.circularProgressBarArticlesPage.visibility = View.GONE
        })

    }

    private fun setupRecyclerView() {
        layoutManager =
            GridLayoutManager(context, 2) // this 2 is basically number of columns u want
        binding.newsListRecyclerView.layoutManager = layoutManager
        adapter =
            context?.let {
                NewsListRecyclerAdapter(
                    it,
                    this
                )
            } // sending context to adapter so that Glide can use it
        binding.newsListRecyclerView.adapter = adapter
    }

    override fun onBookmarkButtonClick(position: Int) {
        val article = listOfNewsArticle[position]
        var author = if (article.author != null) article.author else ""
        var title = if (article.title != null) article.title else ""
        var description = if (article.description != null) article.description else ""
        var urlToArticle = if (article.urlToArticle != null) article.urlToArticle else ""
        var urlToImage = if (article.urlToImage != null) article.urlToImage else ""
        var publishedAt = if (article.publishedAt != null) article.publishedAt else ""
        var content = if (article.content != null) article.content else ""

        val newsArticle: SavedNewsEntity =
            SavedNewsEntity(
                0, // we have to pas 0 here , dont worry room library will change it since its the primary key
                author,
                title,
                description,
                urlToArticle,
                urlToImage,
                publishedAt,
                content
            )
        viewModelForCache.addNewsArticle(newsArticle)
        Toast.makeText(context, "YO Inside On Bookmark $position  END", Toast.LENGTH_SHORT).show()
    }

}