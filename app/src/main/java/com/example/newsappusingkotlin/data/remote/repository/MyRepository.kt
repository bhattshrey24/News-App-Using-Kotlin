package com.example.newsappusingkotlin.data.remote.repository

import android.util.Log
import com.example.newsappusingkotlin.data.models.NewsJsonReceiver
import com.example.newsappusingkotlin.data.remote.api.RetrofitInstance
import com.example.newsappusingkotlin.other.Constants
import retrofit2.HttpException

class MyRepository {

//    suspend fun getPost(country: String): NewsJsonReceiver {
//        return RetrofitInstance.api.getPost(country, Constants.newsApiKey)
//    }

    suspend fun getPostForHomePage(country: String, category: String): NewsJsonReceiver {
        var apiResponse = NewsJsonReceiver(
            "",
            0,
            listOf()
        ) //making Dummy Object so that I could return it if Api Gives Error
        try { // Using Try Catch to Handle API Errors
            apiResponse =
                RetrofitInstance.api.getPostsForHomePage(country, category, Constants.newsApiKey)
        } catch (e: HttpException) {
            //this.javaClass.name
            Log.d(Constants.permanentDebugTag, "Http error in ${this.javaClass.name} ${e.message}")
        } catch (e: Exception) {
            //this.javaClass.name
            Log.d(Constants.permanentDebugTag, "Exception in ${this.javaClass.name} ${e.message}")
        }
        return apiResponse
    }

    suspend fun getPostBasedOnQuery(category: String): NewsJsonReceiver {
        var apiResponse = NewsJsonReceiver(
            "",
            0,
            listOf()
        ) //making Dummy Object so that I could return it if Api Gives Error
        try {
            apiResponse =
                RetrofitInstance.api.getPostsForBasedOnQuery(category, Constants.newsApiKey)
        } catch (e: HttpException) {
            Log.d(Constants.permanentDebugTag, "Http error in ${this.javaClass.name} ${e.message}")
        } catch (e: Exception) {
            Log.d(Constants.permanentDebugTag, "Exception in ${this.javaClass.name} ${e.message}")
        }
        return apiResponse
    }



}