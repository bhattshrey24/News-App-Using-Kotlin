package com.example.newsappusingkotlin.data.remote.api

import com.example.newsappusingkotlin.data.models.NewsHolderDummy
import retrofit2.http.GET

interface SimpleApi {
    @GET("posts/1")
    suspend fun getPost():NewsHolderDummy // the body will be filled by retrofit
}