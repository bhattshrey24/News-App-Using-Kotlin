package com.example.newsappusingkotlin.ui.fragments

import android.content.Context
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
import com.example.newsappusingkotlin.other.HolderClass
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
        Log.d(Constants.currentDebugTag, "Inside OnResume()")
        when (category) {
            "india" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat1) // I guess ismei latest Data is store hota hai
                }
            }
            "covid" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat2) // I guess ismei latest Data is store hota hai
                }
            }
            "stocks" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat3) // I guess ismei latest Data is store hota hai
                }
            }
            "business" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat4) // I guess ismei latest Data is store hota hai
                }
            }
            "entertainment" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat5) // I guess ismei latest Data is store hota hai
                }
            }
            "general" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat6) // I guess ismei latest Data is store hota hai
                }
            }
            "health" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat7) // I guess ismei latest Data is store hota hai
                }
            }
            "science" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat8) // I guess ismei latest Data is store hota hai
                }
            }
            "sports" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat9) // I guess ismei latest Data is store hota hai
                }
            }
            "technology" -> {
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat10) // I guess ismei latest Data is store hota hai
                }
            }
            else -> {//business is default case
                adapter.let {
                    it?.setNews(viewModel.listOfNewsArticleCat4) // I guess ismei latest Data is store hota hai
                }
            }
        }
        super.onResume()
    }

    override fun onCreateView( // Observe It's not OnCreate It's "OnCreateView" , here we bind the layout of the fragment
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Log.d(
            Constants.currentDebugTag,
            "Inside onCreateView() of Articles Frag , Category= $category"
        )

        binding.circularProgressBarArticlesPage.visibility = View.VISIBLE

        setupRecyclerView()

        setupApiViewModel()
        setupCacheViewModel()

        val cat = arguments?.getString("category", "business")
        val categoryFromArgs = cat ?: "business"
        category = viewModel.getCategoryStringForApi(categoryFromArgs)

        callingForNewsArticles()


        return binding.root
    }


    private fun callingForNewsArticles() {
        binding.circularProgressBarArticlesPage.visibility = View.VISIBLE

        viewModel.getPostForArticlesPage(category)

        viewModel.articlesPageResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d(
                Constants.permanentDebugTag,
                "Response in articlesPageResponse livedata observer in AF , StoredCategory is :$category and Response Category is  : ${response.category} and 1st Article Title: ${response.newsReceiver.articles[0].title}"
            )

            var holderObject = response

            saveData(holderObject)

            adapter?.setNews(holderObject.newsReceiver.articles) // idhr recycler view mei data set krna hi pdega cause api response kaafi time baad bhi de skti hai utni der mei onResume execute ho jaega

            binding.circularProgressBarArticlesPage.visibility = View.GONE
        })
    }

    private fun saveData(holderObject: HolderClass) {
        when (holderObject.category) {
            "india" -> {
                viewModel.listOfNewsArticleCat1 = holderObject.newsReceiver.articles.toMutableList()
            }
            "covid" -> {
                viewModel.listOfNewsArticleCat2 = holderObject.newsReceiver.articles.toMutableList()
            }
            "stocks" -> {
                viewModel.listOfNewsArticleCat3 = holderObject.newsReceiver.articles.toMutableList()
            }
            "business" -> {
                viewModel.listOfNewsArticleCat4 = holderObject.newsReceiver.articles.toMutableList()

            }
            "entertainment" -> {
                viewModel.listOfNewsArticleCat5 = holderObject.newsReceiver.articles.toMutableList()

            }
            "general" -> {
                viewModel.listOfNewsArticleCat6 = holderObject.newsReceiver.articles.toMutableList()

            }
            "health" -> {
                viewModel.listOfNewsArticleCat7 = holderObject.newsReceiver.articles.toMutableList()

            }
            "science" -> {
                viewModel.listOfNewsArticleCat8 = holderObject.newsReceiver.articles.toMutableList()

            }
            "sports" -> {
                viewModel.listOfNewsArticleCat9 = holderObject.newsReceiver.articles.toMutableList()

            }
            "technology" -> {
                viewModel.listOfNewsArticleCat10 =
                    holderObject.newsReceiver.articles.toMutableList()

            }
            else -> {//business is default case
                viewModel.listOfNewsArticleCat4 = holderObject.newsReceiver.articles.toMutableList()

            }
        }
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

    private fun setupApiViewModel() {
        val viewModelFactory = ArticlesPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            )[ArticlesPageViewModel::class.java]
    }

    private fun setupCacheViewModel() {
        val viewModelCacheFactory = ViewModelForCacheFactory(requireActivity().application)
        viewModelForCache = ViewModelProvider(
            requireActivity(),
            viewModelCacheFactory
        )[ViewModelForCache::class.java]
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