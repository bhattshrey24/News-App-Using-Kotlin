package com.example.newsappusingkotlin.data.remote.repository

import androidx.lifecycle.LiveData
import com.example.newsappusingkotlin.data.cache.SavedArticlesDAO
import com.example.newsappusingkotlin.data.cache.SavedNewsEntity

class LocalRepository(private val savedArticlesDao: SavedArticlesDAO) {
// Change the name from Local to RemoteAndDatabaseRepo  or just combine both the repositories ie. Local and MyRepo

    val getAllSavedNewsArticles: LiveData<List<SavedNewsEntity>> =
        savedArticlesDao.getAllSavedNewsArticles() // make it a function instead

    suspend fun addArticle(savedNewsArticle: SavedNewsEntity):Long {
        return savedArticlesDao.insertArticle(savedNewsArticle)
    }

    suspend fun deleteArticle(savedNewsArticle: SavedNewsEntity) {
        return savedArticlesDao.deleteArticle(savedNewsArticle)
    }

}