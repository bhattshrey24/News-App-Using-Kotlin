package com.example.newsappusingkotlin.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.data.models.News
import com.example.newsappusingkotlin.data.models.NewsJsonReceiver
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MyRepository) : ViewModel() {

    val myResponse: MutableLiveData<NewsJsonReceiver> =
        MutableLiveData() // livedata so that our activity gets refreshed when couroutine is done with fetching

    val homePageResponse: MutableLiveData<NewsJsonReceiver> =
        MutableLiveData()

    val articlesPageResponse: MutableLiveData<NewsJsonReceiver> =
        MutableLiveData()

    var combinedListOfArticles: MutableList<News> = ArrayList()

    fun getPost(country: String) {
        viewModelScope.launch { // this will automatically do async call
            val response: NewsJsonReceiver = repository.getPost(country)
            myResponse.value =
                response // this will automatically update the UI because myReponse is LiveData
        }
    }

    fun getPostForHomePage(country: String, category: String) {
        viewModelScope.launch { // this will automatically do async call
            val response: NewsJsonReceiver = repository.getPostForHomePage(country, category)
            homePageResponse.value =
                combiningResults(response) // this will automatically update the UI because myReponse is LiveData
        }
    }

    fun getPostForArticlesPage(category: String) {
        viewModelScope.launch { // this will automatically do async call
            val response: NewsJsonReceiver = repository.getPostForArticlesPage(category)
            articlesPageResponse.value = response
        }
    }

    private fun combiningResults(newsJsonReceiver: NewsJsonReceiver): NewsJsonReceiver { // here I simply add the new response articles to old list so that in home page I can show articles of all 3 categories
        for (news in newsJsonReceiver.articles) {
            combinedListOfArticles.add(news)
        }
        return NewsJsonReceiver("ok", 0, combinedListOfArticles) // shuffle the list later
    }


}