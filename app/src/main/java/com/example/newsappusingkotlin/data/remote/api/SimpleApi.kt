package com.example.newsappusingkotlin.data.remote.api

import com.example.newsappusingkotlin.data.models.NewsJsonReceiver
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {
    @GET("top-headlines")
    suspend fun getPost(@Query("country")country:String, @Query("apiKey")apiKey:String):NewsJsonReceiver // the body will be filled by retrofit
}