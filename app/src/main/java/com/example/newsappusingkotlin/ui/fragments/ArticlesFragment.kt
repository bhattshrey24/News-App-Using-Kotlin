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
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.FragmentArticlesBinding
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.ui.viewmodels.ArticlesPageViewModel
import com.example.newsappusingkotlin.ui.viewmodels.ArticlesPageViewModelFactory
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCache
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCacheFactory


class ArticlesFragment : Fragment(), NewsListRecyclerAdapter.OnBookmarkButtonListener {

    private val binding: FragmentArticlesBinding by lazy { // so that layout binding only happens when need , It improves performance
        FragmentArticlesBinding.inflate(layoutInflater, null, false)
    }
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: NewsListRecyclerAdapter? = null
    private lateinit var viewModel: ArticlesPageViewModel
    private lateinit var viewModelForCache: ViewModelForCache
    private val repository = MyRepository()

    private var category: String = "business"


    override fun onResume() { // because On resume is called everytime tab changes and not onCreate
        Log.d(
            Constants.currentDebugTag,
            "inside onResume() of Article Frag with category $category"
        )
        callingForNewsArticles() //Fix This , Here only Update RecyclerView , Don't Call Api for Response
//        adapter.let {
//            it?.setNews(viewModel.listOfNewsArticle)
//        }
        super.onResume()
    }

    override fun onCreateView( // Observe It's not OnCreate It's "OnCreateView" , here we bind the layout of the fragment
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.circularProgressBarArticlesPage.visibility = View.VISIBLE
        setupRecyclerView()
        setupViewModel()

        val cat = arguments?.getString("category", "business")
        val categoryFromArgs = cat ?: "business"
        category = viewModel.getCategoryStringForApi(categoryFromArgs)

       // callingForNewsArticles()

        val viewModelCacheFactory = ViewModelForCacheFactory(requireActivity().application)
        viewModelForCache = ViewModelProvider(
            requireActivity(),
            viewModelCacheFactory
        )[ViewModelForCache::class.java]

        return binding.root
    }

    private fun setupViewModel() {
        val viewModelFactory = ArticlesPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            )[ArticlesPageViewModel::class.java]
    }

    private fun callingForNewsArticles() {
        binding.circularProgressBarArticlesPage.visibility = View.VISIBLE
        viewModel.getPostForArticlesPage(category)
        viewModel.articlesPageResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d(Constants.permanentDebugTag, "Response in Articles Frag is , articles List Size: ${response.articles.size}")
            adapter?.setNews(response.articles)
            viewModel.listOfNewsArticle = response.articles
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
        viewModelForCache.addNewsArticle(
            viewModel.onBookMarkButtonClickedCode(
                viewModel.listOfNewsArticle,
                position
            )
        )
        Toast.makeText(context, "YO Inside On Bookmark $position  END", Toast.LENGTH_SHORT).show()
    }

}