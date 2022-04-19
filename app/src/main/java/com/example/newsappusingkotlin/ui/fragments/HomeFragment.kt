package com.example.newsappusingkotlin.ui.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.adapters.HomeFragmentNewsListAdapter
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.FragmentHomeBinding
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.ui.viewmodels.MainViewModel
import com.example.newsappusingkotlin.ui.viewmodels.MainViewModelFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater, null, false)
    }
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: HomeFragmentNewsListAdapter? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // activity?.title="My Home" // U can use this to change actionBar Title from Fragment
        binding.circularProgressBarHomePage.visibility = View.VISIBLE

        setupRecyclerView()
        setupViewModelAndCallingForNewsArticles()

        return binding.root
    }

    private fun setupViewModelAndCallingForNewsArticles() {
        val repository = MyRepository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(MainViewModel::class.java)


        val sharedPreferences: SharedPreferences? =
            context?.getSharedPreferences(Constants.userDetailInputPrefKey, Context.MODE_PRIVATE)

        val usersSelectedCategoryJson = sharedPreferences?.getString(
            Constants.usersSelectedCategories,
            ""
        )
        var favCategories = convertFromJsonToList(usersSelectedCategoryJson)
        Log.d("Debug Homee", "${favCategories[0]} nd ${favCategories[1]} nd ${favCategories[2]}")

        viewModel.getPostForHomePage("in", favCategories[0]) // in viewmodel I have added logic that after each call the new list of articles will be added to previous one hence we get all three categories articles
        viewModel.getPostForHomePage("in", favCategories[1])
        viewModel.getPostForHomePage("in", favCategories[2])

        viewModel.homePageResponse.observe(viewLifecycleOwner, Observer { response ->
            Log.d("Response ", "Response is : $response")
            adapter?.setNews(response.articles)
            binding.circularProgressBarHomePage.visibility = View.GONE
        })

    }

    private fun convertFromJsonToList(json: String?): ArrayList<String> {
        var gson: Gson = Gson()
        val type = object : TypeToken<ArrayList<String?>>() {}.type
        return gson.fromJson(json, type);
    }

    private fun setupRecyclerView() {
        layoutManager =
            LinearLayoutManager(context) // this 2 is basically number of columns u want
        binding.recyclerViewHomePage.layoutManager = layoutManager
        adapter =
            context?.let {
                HomeFragmentNewsListAdapter(
                    it
                )
            } // sending context to adapter so that Glide can use it
        binding.recyclerViewHomePage.adapter = adapter
    }

}