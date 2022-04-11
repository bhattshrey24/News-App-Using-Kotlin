package com.example.newsappusingkotlin.data.remote.repository

import com.example.newsappusingkotlin.data.models.NewsHolderDummy
import com.example.newsappusingkotlin.data.remote.api.RetrofitInstance

class MyRepository {
    suspend fun getPost():NewsHolderDummy{
       return RetrofitInstance.api.getPost()
    }
}