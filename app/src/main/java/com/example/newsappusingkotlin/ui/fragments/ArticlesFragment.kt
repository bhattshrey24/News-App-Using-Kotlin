package com.example.newsappusingkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappusingkotlin.R
import com.example.newsappusingkotlin.databinding.FragmentArticlesBinding


class ArticlesFragment : Fragment() {
    private val binding: FragmentArticlesBinding by lazy { // so that layout binding only happens when need , It improves performance
        FragmentArticlesBinding.inflate(layoutInflater, null, false)
    }
    override fun onCreateView( // Observe It's not OnCreate It's "OnCreateView" , here we bind the layout of the fragment
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        return binding.root
    }
}