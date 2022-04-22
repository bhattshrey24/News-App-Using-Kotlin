package com.example.newsappusingkotlin.adapters

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.ui.fragments.ArticlesFragment

class ArticlesPageViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return Constants.numberOfCategories
    }

    override fun createFragment(position: Int): Fragment {
       Log.d("ZOOOO ", "inside createFrag with $position")
        return when (position) {
            0 -> {
                setUpFragWithArgument(Constants.category1)
            }
            1 -> {
                setUpFragWithArgument(Constants.category2)
            }
            2 -> {
                setUpFragWithArgument(Constants.category3)
            }
            3 -> {
                setUpFragWithArgument(Constants.category4)
            }
            4 -> {
                setUpFragWithArgument(Constants.category5)
            }
            5 -> {
                setUpFragWithArgument(Constants.category6)
            }
            6 -> {
                setUpFragWithArgument(Constants.category7)
            }
            7 -> {
                setUpFragWithArgument(Constants.category8)
            }
            8 -> {
                setUpFragWithArgument(Constants.category9)
            }
            9 -> {
                setUpFragWithArgument(Constants.category10)
            }
            else -> {
                setUpFragWithArgument(Constants.category1)
            }
        }
    }

    private fun setUpFragWithArgument(category: String): ArticlesFragment {
        val articlesFragment = ArticlesFragment()
        val args = Bundle()
        args.putString("category", category)
        articlesFragment.arguments = args
        return articlesFragment
    }

}