package com.example.newsappusingkotlin.data.remote.api

import com.example.newsappusingkotlin.other.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance { // right now we are creating a single ton but u can later on add hilt
    private val retrofit by lazy { // this makes the retrofit object , its private so that no one can use it outside this class
        Retrofit.Builder()
            .baseUrl(Constants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: SimpleApi by lazy { // here we are simply using the retrofit object we created above and calling the 'create' function on it which will simply fill the api functions present in our SimpleApi with 'body' and give us the object so that we can use the api
        retrofit.create(SimpleApi::class.java)
    }
}