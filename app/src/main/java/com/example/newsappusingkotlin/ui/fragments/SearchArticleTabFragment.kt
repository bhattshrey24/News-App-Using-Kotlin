package com.example.newsappusingkotlin.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.NewsArticleDisplayActivity
import com.example.newsappusingkotlin.adapters.SearchFragmentNewsListAdapter
import com.example.newsappusingkotlin.data.models.News
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.FragmentSearchArticleTabBinding
import com.example.newsappusingkotlin.other.CommonFunctions
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.ui.viewmodels.SearchPageViewModel
import com.example.newsappusingkotlin.ui.viewmodels.SearchPageViewModelFactory
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCache
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCacheFactory


class SearchArticleTabFragment : Fragment(), SearchFragmentNewsListAdapter.OnBookmarkButtonListener,
    SearchFragmentNewsListAdapter.OnNewsArticleClickListener {
    private val binding: FragmentSearchArticleTabBinding by lazy {
        FragmentSearchArticleTabBinding.inflate(layoutInflater, null, false)
    }
    private lateinit var viewModel: SearchPageViewModel
    private val repository = MyRepository()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: SearchFragmentNewsListAdapter? = null
    private var topic: String = "business"
    private var listOfNews: List<News> = mutableListOf()
    private lateinit var viewModelForCache: ViewModelForCache

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //setup recycler view
        // setup viewmodel
        // call api
        // change Data in recycler View
        setupRecyclerView()
        setupApiViewModel()
        setupCacheViewModel()
        binding.ETSearchArticleTab.setOnEditorActionListener { _, actionId, _ -> // this listeners gets triggered when user presses done button on keyboard
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.progressBarSearchArticleTab.visibility = View.VISIBLE
                topic = binding.ETSearchArticleTab.text.toString()
                callApi()
                Toast.makeText(context, "Searching", Toast.LENGTH_SHORT).show()
                true
            }
            false
        }
        return binding.root
    }

    private fun setupRecyclerView() {
        layoutManager =
            LinearLayoutManager(context) // this 2 is basically number of columns u want
        binding.recyclerViewSearchArticleTab.layoutManager = layoutManager
        adapter =
            context?.let {
                SearchFragmentNewsListAdapter(
                    it, this, this
                )
            } // sending context to adapter so that Glide can use it
        binding.recyclerViewSearchArticleTab.adapter = adapter
    }

    private fun setupApiViewModel() {
        val viewModelFactory = SearchPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            )[SearchPageViewModel::class.java]
    }

    private fun setupCacheViewModel() {
        val viewModelCacheFactory = ViewModelForCacheFactory(requireActivity().application)
        viewModelForCache = ViewModelProvider(
            requireActivity(),
            viewModelCacheFactory
        )[ViewModelForCache::class.java]
    }

    override fun onBookmarkButtonClick(position: Int) {
        var convertedNewsArticle = CommonFunctions.onBookMarkButtonClickedCode(
            listOfNews,
            position
        )
        viewModelForCache.sendNewsToFireStore(convertedNewsArticle).addOnCompleteListener {
            val docId = it.result.id
            convertedNewsArticle.id =
                docId// to store data in room we are using id that we got from firestore  when we saved artile there , because this way its easier to delete the data from room and firestore since id at both places are same , also there wont be any discrepancy regarding id since every article will have same id in room as well as firestore
            viewModelForCache.addNewsArticle(
                convertedNewsArticle
            )
        }
        Toast.makeText(context, "Inside On Bookmark $position  END", Toast.LENGTH_SHORT).show()
    }

    override fun onNewsArticleClick(position: Int) {
        Toast.makeText(context, "User Clicked $position View", Toast.LENGTH_SHORT).show()
        var intent = Intent(activity, NewsArticleDisplayActivity::class.java)
        //  listOfNews = viewModel.setupListOfNewsOfCurrentFragmentInDisplay(category)
        intent.putExtra(Constants.objectPassingThroughIntentKey, listOfNews[position])
        activity?.startActivity(intent)
    }

    private fun callApi() {
        viewModel.getPostForTopic(topic)
        viewModel.getSearchPageResponseLiveData().observe(viewLifecycleOwner, Observer { response ->
            if (response.articles.isNotEmpty()) {
                binding.TVSearchArticleTab.visibility = View.GONE
            } else {
                binding.TVSearchArticleTab.visibility = View.VISIBLE
            }
            listOfNews = response.articles
            adapter?.setNews(response.articles)
            binding.progressBarSearchArticleTab.visibility = View.GONE
        })
    }
}