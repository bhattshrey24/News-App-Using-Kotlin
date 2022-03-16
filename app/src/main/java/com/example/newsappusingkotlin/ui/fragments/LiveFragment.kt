package com.example.newsappusingkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.newsappusingkotlin.R
import com.example.newsappusingkotlin.databinding.FragmentHomeBinding
import com.example.newsappusingkotlin.databinding.FragmentLiveBinding


class LiveFragment : Fragment() {
    private val binding: FragmentLiveBinding by lazy {
        FragmentLiveBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}