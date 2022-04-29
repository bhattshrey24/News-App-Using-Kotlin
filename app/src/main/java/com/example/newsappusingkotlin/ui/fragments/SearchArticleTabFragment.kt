package com.example.newsappusingkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //setup recycler view
        // setup viewmodel
        // call api
        // change Data in recycler View
        return binding.root
    }

    private fun setupApiViewModel() {
        val viewModelFactory = SearchPageViewModelFactory(repository)
        viewModel =
            ViewModelProvider(
                requireActivity(),
                viewModelFactory
            )[SearchPageViewModel::class.java]
    }

}