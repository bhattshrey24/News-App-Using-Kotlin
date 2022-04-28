package com.example.newsappusingkotlin.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.data.models.News
import com.example.newsappusingkotlin.data.models.NewsJsonReceiver
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import kotlinx.coroutines.launch

class HomePageViewModel(private val repository: MyRepository) : ViewModel() {

    private val homePageResponse: MutableLiveData<NewsJsonReceiver> =
        MutableLiveData()

    fun getHomePageResponseLiveData(): MutableLiveData<NewsJsonReceiver> {
        return homePageResponse
    }

    var combinedListOfArticles: MutableList<News> = ArrayList()

    fun getPostForHomePage(country: String, category: String) {
        viewModelScope.launch { // this will automatically do async call
            val response: NewsJsonReceiver = repository.getPostForHomePage(country, category)
            homePageResponse.value =
                combiningResults(response) // this will automatically update the UI because myReponse is LiveData
        }
    }

    private fun combiningResults(newsJsonReceiver: NewsJsonReceiver): NewsJsonReceiver { // here I simply add the new response articles to old list so that in home page I can show articles of all 3 categories
        for (news in newsJsonReceiver.articles) {
            combinedListOfArticles.add(news)
        }
        return NewsJsonReceiver("ok", 0, combinedListOfArticles) // shuffle the list later
    }

}