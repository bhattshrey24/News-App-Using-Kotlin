package com.example.newsappusingkotlin.data.remote.repository

import com.example.newsappusingkotlin.data.models.NewsJsonReceiver
import com.example.newsappusingkotlin.data.remote.api.RetrofitInstance
import com.example.newsappusingkotlin.other.Constants

class MyRepository {
    suspend fun getPost(country: String): NewsJsonReceiver {
        return RetrofitInstance.api.getPost(country, Constants.newsApiKey)
    }

    suspend fun getPostForHomePage(country: String, category: String): NewsJsonReceiver {
        return RetrofitInstance.api.getPostsForHomePage(country,category ,Constants.newsApiKey)
    }
}