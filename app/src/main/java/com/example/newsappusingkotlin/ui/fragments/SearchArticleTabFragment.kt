package com.example.newsappusingkotlin.ui.fragments

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
import com.example.newsappusingkotlin.adapters.SearchFragmentNewsListAdapter
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.FragmentSearchArticleTabBinding
import com.example.newsappusingkotlin.ui.viewmodels.SearchPageViewModel
import com.example.newsappusingkotlin.ui.viewmodels.SearchPageViewModelFactory


class SearchArticleTabFragment : Fragment() {
    private val binding: FragmentSearchArticleTabBinding by lazy {
        FragmentSearchArticleTabBinding.inflate(layoutInflater, null, false)
    }
    private lateinit var viewModel: SearchPageViewModel
    private val repository = MyRepository()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: SearchFragmentNewsListAdapter? = null
    private var topic: String = "business"

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
        binding.ETSearchArticleTab.setOnEditorActionListener{ _, actionId, _ -> // this listeners gets triggered when user presses done button on keyboard
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                    binding.progressBarSearchArticleTab.visibility=View.VISIBLE
                    topic=binding.ETSearchArticleTab.text.toString()
                    callApi()
                    Toast.makeText(context,"Searching",Toast.LENGTH_SHORT).show()
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
                    it
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

    private fun callApi() {
        viewModel.getPostForTopic(topic)
        viewModel.getSearchPageResponseLiveData().observe(viewLifecycleOwner, Observer { response ->
            if (response.articles.isNotEmpty()){
                binding.TVSearchArticleTab.visibility=View.GONE
            }else{
                binding.TVSearchArticleTab.visibility=View.VISIBLE
            }
            adapter?.setNews(response.articles)
            binding.progressBarSearchArticleTab.visibility = View.GONE
        })
    }
}