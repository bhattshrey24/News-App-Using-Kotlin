package com.example.newsappusingkotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.adapters.NewsListRecyclerAdapter
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.FragmentArticlesBinding
import com.example.newsappusingkotlin.ui.viewmodels.MainViewModel
import com.example.newsappusingkotlin.ui.viewmodels.MainViewModelFactory


class ArticlesFragment : Fragment() {

    private val binding: FragmentArticlesBinding by lazy { // so that layout binding only happens when need , It improves performance
        FragmentArticlesBinding.inflate(layoutInflater, null, false)
    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter:NewsListRecyclerAdapter?=null
    private lateinit var viewModel: MainViewModel

    override fun onCreateView( // Observe It's not OnCreate It's "OnCreateView" , here we bind the layout of the fragment
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val repository = MyRepository()
        val viewModelFactory = MainViewModelFactory(repository)

        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)
        viewModel.getPost("in") // in is the code for "India"

        layoutManager =
       GridLayoutManager(context, 2) // this 2 is basically number of columns u want

        binding.newsListRecyclerView.layoutManager = layoutManager

        adapter = context?.let { NewsListRecyclerAdapter(it) }

        binding.newsListRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.myResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response ", "Response is : $response")
           adapter?.setNews(response.articles,response.totalResults)
        })

    }
}