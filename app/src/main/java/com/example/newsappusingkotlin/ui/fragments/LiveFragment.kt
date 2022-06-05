package com.example.newsappusingkotlin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
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
        binding.livePageWebView.settings.javaScriptEnabled = true
        binding.livePageWebView.webViewClient = WebViewClient()
        binding.livePageWebView.settings.setSupportZoom(true)
        binding.livePageWebView.loadUrl("https://www.youtube.com/results?search_query=live+news")
        return binding.root
    }

}