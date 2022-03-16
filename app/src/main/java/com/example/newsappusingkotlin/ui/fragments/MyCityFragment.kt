package com.example.newsappusingkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsappusingkotlin.R
import com.example.newsappusingkotlin.databinding.FragmentHomeBinding
import com.example.newsappusingkotlin.databinding.FragmentMyCityBinding


class MyCityFragment : Fragment() {
    private val binding: FragmentMyCityBinding by lazy {
        FragmentMyCityBinding.inflate(layoutInflater, null, false)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}