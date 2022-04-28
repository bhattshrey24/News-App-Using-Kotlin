package com.example.newsappusingkotlin.ui.fragments

import android.content.Intent
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
import com.example.newsappusingkotlin.NewsArticleDisplayActivity
import com.example.newsappusingkotlin.adapters.NewsListRecyclerAdapter
import com.example.newsappusingkotlin.data.models.News
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.FragmentArticlesBinding
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.other.HolderClass
import com.example.newsappusingkotlin.ui.viewmodels.ArticlesPageViewModel
import com.example.newsappusingkotlin.ui.viewmodels.ArticlesPageViewModelFactory
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCache
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCacheFactory


class ArticlesFragment : Fragment(), NewsListRecyclerAdapter.OnBookmarkButtonListener,
    NewsListRecyclerAdapter.OnNewsArticleClickListener {

    private val binding: FragmentArticlesBinding by lazy { // so that layout binding only happens when need , It improves performance
        FragmentArticlesBinding.inflate(layoutInflater, null, false)
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: NewsListRecyclerAdapter? = null
    private lateinit var viewModel: ArticlesPageViewModel
    private lateinit var viewModelForCache: ViewModelForCache
    private val repository = MyRepository()
    private var category: String = "business"
    private var listOfNews: MutableList<News> = mutableListOf()



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

    override fun onResume() { // because On resume is called everytime tab changes and not onCreate
        Log.d(Constants.currentDebugTag, "Inside OnResume()")
        viewModel.loadSavedDataInRecyclerView(category, adapter)
        super.onResume()
    }

    private fun callingForNewsArticles() {
        binding.circularProgressBarArticlesPage.visibility = View.VISIBLE

        viewModel.getPostForArticlesPage(category)

        viewModel.getArticlePageResponseLiveData().observe(viewLifecycleOwner, Observer { response ->
            Log.d(
                Constants.permanentDebugTag,
                "Response in articlesPageResponse livedata observer in AF , StoredCategory is :$category and Response Category is  : ${response.category} and 1st Article Title: ${response.newsReceiver.articles[0].title}"
            )

            var holderObject = response

            viewModel.saveData(holderObject)

            adapter?.setNews(holderObject.newsReceiver.articles) // idhr recycler view mei data set krna hi pdega cause api response kaafi time baad bhi de skti hai utni der mei onResume execute ho jaega

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
                    this,
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

        listOfNews = viewModel.setupListOfNewsOfCurrentFragmentInDisplay(category)

        var convertedNewsArticle = viewModel.onBookMarkButtonClickedCode(
            listOfNews,
            position
        )
        viewModelForCache.sendNewsToFireStore(convertedNewsArticle).addOnCompleteListener{
            val docId=it.result.id
            convertedNewsArticle.id=docId// to store data in room we are using id that we got from firestore  when we saved artile there , because this way its easier to delete the data from room and firestore since id at both places are same , also there wont be any discrepancy regarding id since every article will have same id in room as well as firestore
            viewModelForCache.addNewsArticle(
                convertedNewsArticle
            )
        }
        Toast.makeText(context, "Inside On Bookmark $position  END", Toast.LENGTH_SHORT).show()
    }

    override fun onNewsArticleClick(position: Int) {
        Toast.makeText(context, "User Clicked $position View", Toast.LENGTH_SHORT).show()
        var intent = Intent(activity, NewsArticleDisplayActivity::class.java)
        listOfNews = viewModel.setupListOfNewsOfCurrentFragmentInDisplay(category)
        intent.putExtra(Constants.objectPassingThroughIntentKey, listOfNews[position])
        activity?.startActivity(intent)
    }




}