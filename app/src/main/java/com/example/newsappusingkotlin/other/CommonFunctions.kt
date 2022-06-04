package com.example.newsappusingkotlin.other

import com.example.newsappusingkotlin.data.cache.SavedNewsEntity
import com.example.newsappusingkotlin.data.models.News
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CommonFunctions { // this class contains functions that are common to many classes , this reduces duplicate code problem

    companion object {
        // it basically make everything inside 'static'
        fun convertArrayListToGson(selectedCategoriesList: MutableList<String>): String {
            val gson = Gson()
            return gson.toJson(selectedCategoriesList)
        }

        fun convertFromJsonToList(json: String?): ArrayList<String> {
            var gson: Gson = Gson()
            val type = object : TypeToken<ArrayList<String?>>() {}.type
            return gson.fromJson(json, type);
        }
        fun onBookMarkButtonClickedCode(
            listOfNewsArticle: List<News>,
            position: Int
        ): SavedNewsEntity {// here I'm converting News into SavedNewsArticle

            val article = listOfNewsArticle[position]
            var author = if (article.author != null) article.author else ""
            var title = if (article.title != null) article.title else ""
            var description = if (article.description != null) article.description else ""
            var urlToArticle = if (article.urlToArticle != null) article.urlToArticle else ""
            var urlToImage = if (article.urlToImage != null) article.urlToImage else ""
            var publishedAt = if (article.publishedAt != null) article.publishedAt else ""
            var content = if (article.content != null) article.content else ""

            return SavedNewsEntity(
                "", // we have to passed 0 here , dont worry room library will change it since its the primary key
                author,
                title,
                description,
                urlToArticle,
                urlToImage,
                publishedAt,
                content
            )
        }
    }

}