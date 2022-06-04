package com.example.newsappusingkotlin.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.adapters.NewsListRecyclerAdapter
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

    private val articlesPageResponse: MutableLiveData<HolderClass> =
        MutableLiveData()

     fun getArticlePageResponseLiveData():MutableLiveData<HolderClass>{
       return articlesPageResponse
    }

    fun getPostForArticlesPage(category: String) {
        viewModelScope.launch { // this will automatically do async call
            val response: NewsJsonReceiver = repository.getPostBasedOnQuery(category)
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

    fun setupListOfNewsOfCurrentFragmentInDisplay(category:String):MutableList<News> {
        when (category) {
            "india" -> {
                return listOfNewsArticleCat1
            }
            "covid" -> {
                return listOfNewsArticleCat2
            }
            "stocks" -> {
                return  listOfNewsArticleCat3
            }
            "business" -> {
                return listOfNewsArticleCat4
            }
            "entertainment" -> {
                return listOfNewsArticleCat5
            }
            "general" -> {
                return listOfNewsArticleCat6
            }
            "health" -> {
                return listOfNewsArticleCat7
            }
            "science" -> {
                return listOfNewsArticleCat8
            }
            "sports" -> {
                return listOfNewsArticleCat9
            }
            "technology" -> {
                return listOfNewsArticleCat10
            }
            else -> {//business is default case
                return listOfNewsArticleCat4
            }
        }
    }

     fun saveData(holderObject: HolderClass) {
        when (holderObject.category) {
            "india" -> {
                listOfNewsArticleCat1 = holderObject.newsReceiver.articles.toMutableList()
            }
            "covid" -> {
                listOfNewsArticleCat2 = holderObject.newsReceiver.articles.toMutableList()
            }
            "stocks" -> {
                listOfNewsArticleCat3 = holderObject.newsReceiver.articles.toMutableList()
            }
            "business" -> {
                listOfNewsArticleCat4 = holderObject.newsReceiver.articles.toMutableList()

            }
            "entertainment" -> {
               listOfNewsArticleCat5 = holderObject.newsReceiver.articles.toMutableList()

            }
            "general" -> {
                listOfNewsArticleCat6 = holderObject.newsReceiver.articles.toMutableList()

            }
            "health" -> {
               listOfNewsArticleCat7 = holderObject.newsReceiver.articles.toMutableList()

            }
            "science" -> {
                listOfNewsArticleCat8 = holderObject.newsReceiver.articles.toMutableList()

            }
            "sports" -> {
                listOfNewsArticleCat9 = holderObject.newsReceiver.articles.toMutableList()

            }
            "technology" -> {
                listOfNewsArticleCat10 =
                    holderObject.newsReceiver.articles.toMutableList()

            }
            else -> {//business is default case
                listOfNewsArticleCat4 = holderObject.newsReceiver.articles.toMutableList()

            }
        }
    }

//    fun onBookMarkButtonClickedCode(
//        listOfNewsArticle: List<News>,
//        position: Int
//    ): SavedNewsEntity {// here I'm converting News into SavedNewsArticle
//
//        val article = listOfNewsArticle[position]
//        var author = if (article.author != null) article.author else ""
//        var title = if (article.title != null) article.title else ""
//        var description = if (article.description != null) article.description else ""
//        var urlToArticle = if (article.urlToArticle != null) article.urlToArticle else ""
//        var urlToImage = if (article.urlToImage != null) article.urlToImage else ""
//        var publishedAt = if (article.publishedAt != null) article.publishedAt else ""
//        var content = if (article.content != null) article.content else ""
//
//        return SavedNewsEntity(
//            "", // we have to passed 0 here , dont worry room library will change it since its the primary key
//            author,
//            title,
//            description,
//            urlToArticle,
//            urlToImage,
//            publishedAt,
//            content
//        )
//    }

    fun loadSavedDataInRecyclerView(category: String,adapter: NewsListRecyclerAdapter?){
        when (category) {
            "india" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat1) // I guess ismei latest Data is store hota hai
                }
            }
            "covid" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat2) // I guess ismei latest Data is store hota hai
                }
            }
            "stocks" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat3) // I guess ismei latest Data is store hota hai
                }
            }
            "business" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat4) // I guess ismei latest Data is store hota hai
                }
            }
            "entertainment" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat5) // I guess ismei latest Data is store hota hai
                }
            }
            "general" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat6) // I guess ismei latest Data is store hota hai
                }
            }
            "health" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat7) // I guess ismei latest Data is store hota hai
                }
            }
            "science" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat8) // I guess ismei latest Data is store hota hai
                }
            }
            "sports" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat9) // I guess ismei latest Data is store hota hai
                }
            }
            "technology" -> {
                adapter.let {
                    it?.setNews(listOfNewsArticleCat10) // I guess ismei latest Data is store hota hai
                }
            }
            else -> {//business is default case
                adapter.let {
                    it?.setNews(listOfNewsArticleCat4) // I guess ismei latest Data is store hota hai
                }
            }
        }
    }
}