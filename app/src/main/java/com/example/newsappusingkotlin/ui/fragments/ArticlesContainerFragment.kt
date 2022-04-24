package com.example.newsappusingkotlin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsappusingkotlin.adapters.ArticlesPageViewPagerAdapter
import com.example.newsappusingkotlin.databinding.FragmentArticlesContainerBinding
import com.example.newsappusingkotlin.other.Constants
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ArticlesContainerFragment : Fragment() {
    private val binding: FragmentArticlesContainerBinding by lazy { // so that layout binding only happens when need , It improves performance
        FragmentArticlesContainerBinding.inflate(layoutInflater, null, false)
    }

    override fun onResume() {
        Log.d("ZOOO","inside OnResume() Of ArticlesContainerFragment")
        super.onResume()
    }
    override fun onCreateView( // Observe It's not OnCreate It's "OnCreateView" , here we bind the layout of the fragment
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val myTabLayout = binding.articlesContainerTabLayout
        val myViewPager = binding.articlesContainerViewPager
        myTabLayout.tabMode = TabLayout.MODE_SCROLLABLE // this makes the above tab bar scrollable

        Log.d("ZOOO","inside OnCreateView Of ArticlesContainerFragment")
        //val myAdapter = MyViewPagerAdapter(supportFragmentManager, lifecycle)
        val myAdapter =
            ArticlesPageViewPagerAdapter(parentFragmentManager, lifecycle)
        myViewPager.adapter = myAdapter
        TabLayoutMediator(myTabLayout, myViewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = Constants.category1 // these are the names of the tabsT
                }
                1 -> {
                    tab.text = Constants.category2
                }
                2 -> {
                    tab.text = Constants.category3
                }
                3 -> {
                    tab.text = Constants.category4
                }
                4 -> {
                    tab.text = Constants.category5
                }
                5 -> {
                    tab.text = Constants.category6
                }
                6->{
                    tab.text = Constants.category7
                }
                7->{
                    tab.text = Constants.category8
                }
                8->{
                    tab.text = Constants.category9
                }
                9->{
                    tab.text = Constants.category10
                }
            }
        }.attach()

        return binding.root
    }

}