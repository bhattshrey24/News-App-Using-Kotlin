package com.example.newsappusingkotlin.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.data.cache.SavedNewsEntity
import com.example.newsappusingkotlin.data.models.News
import com.example.newsappusingkotlin.data.models.NewsJsonReceiver
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.other.HolderClass
import kotlinx.coroutines.launch

class ArticlesPageViewModel(private val repository: MyRepository) : ViewModel() {

    var listOfNewsArticleCat1: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat2: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat3: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat4: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat5: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat6: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat7: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat8: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat9: MutableList<News> = mutableListOf()
    var listOfNewsArticleCat10: MutableList<News> = mutableListOf()

    val articlesPageResponse: MutableLiveData<HolderClass> =
        MutableLiveData()

    fun getPostForArticlesPage(category: String) {
        viewModelScope.launch { // this will automatically do async call
            val response: NewsJsonReceiver = repository.getPostForArticlesPage(category)
            Log.d(
                Constants.permanentDebugTag,
                "Response From Api in ArticlesViewModel Articled List Size: ${response.articles.size}"
            )
            articlesPageResponse.value = HolderClass(response, category)
        }
    }

    fun getCategoryStringForApi(categoryFromArgs: String): String {
        val listOfCategory = listOf(Constants.category1, Constants.category2, Constants.category3)
        var ansStr = "business"
        for (cat in listOfCategory) {
            if (Constants.category1 == categoryFromArgs) {
                ansStr = "india" //pass the users nationality here
                break
            } else if (Constants.category2 == categoryFromArgs) {
                ansStr = "covid"
                break
            } else if (Constants.category3 == categoryFromArgs) {
                ansStr = "stocks"
                break
            } else if (Constants.category4 == categoryFromArgs) {
                ansStr = "business"
                break
            } else if (Constants.category5 == categoryFromArgs) {
                ansStr = "entertainment"
                break
            } else if (Constants.category6 == categoryFromArgs) {
                ansStr = "general"
                break
            } else if (Constants.category7 == categoryFromArgs) {
                ansStr = "health"
                break
            } else if (Constants.category8 == categoryFromArgs) {
                ansStr = "science"
                break
            } else if (Constants.category9 == categoryFromArgs) {
                ansStr = "sports"
                break
            } else if (Constants.category10 == categoryFromArgs) {
                ansStr = "technology"
                break
            } else {
                ansStr = "sports"
                break
            }
        }
        return ansStr
    }

    fun onBookMarkButtonClickedCode(listOfNewsArticle: List<News>, position: Int): SavedNewsEntity {
        val article = listOfNewsArticle[position]
        var author = if (article.author != null) article.author else ""
        var title = if (article.title != null) article.title else ""
        var description = if (article.description != null) article.description else ""
        var urlToArticle = if (article.urlToArticle != null) article.urlToArticle else ""
        var urlToImage = if (article.urlToImage != null) article.urlToImage else ""
        var publishedAt = if (article.publishedAt != null) article.publishedAt else ""
        var content = if (article.content != null) article.content else ""

        val newsArticle: SavedNewsEntity =
            SavedNewsEntity(
                0, // we have to pas 0 here , dont worry room library will change it since its the primary key
                author,
                title,
                description,
                urlToArticle,
                urlToImage,
                publishedAt,
                content
            )
        return newsArticle
    }


}