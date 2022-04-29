package com.example.newsappusingkotlin.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.data.models.NewsJsonReceiver
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.other.Constants
import kotlinx.coroutines.launch

class SearchPageViewModel(private val repository: MyRepository): ViewModel()  {
    private val searchPageResponse: MutableLiveData<NewsJsonReceiver> =
        MutableLiveData()

    fun getSearchPageResponseLiveData(): MutableLiveData<NewsJsonReceiver> {
        return searchPageResponse
    }

    fun getPostForTopic(topic:String){
       val modifiedTopicString=cleanTopicString(topic)
       viewModelScope.launch {
           val response: NewsJsonReceiver = repository.getPostBasedOnQuery(modifiedTopicString)
           Log.d(
               Constants.permanentDebugTag,
               "Response From Api in SearchPageViewModel ,  Articles List Size: ${response.articles.size}"
           )
           searchPageResponse.value=response
       }
    }
    private fun cleanTopicString(topic:String):String{
        // here split the topic based on white space , just take out 1st part and remove others , then convert everything to small case
        return ""
    }


}