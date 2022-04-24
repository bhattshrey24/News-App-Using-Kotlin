package com.example.newsappusingkotlin.data.remote.repository

import androidx.lifecycle.LiveData
import com.example.newsappusingkotlin.data.cache.SavedArticlesDAO
import com.example.newsappusingkotlin.data.cache.SavedNewsEntity

class LocalRepository(private val savedArticlesDao: SavedArticlesDAO) {

    val getAllSavedNewsArticles: LiveData<List<SavedNewsEntity>> =
        savedArticlesDao.getAllSavedNewsArticles() // make it a function instead

    suspend fun addArticle(savedNewsArticle: SavedNewsEntity) {
        savedArticlesDao.insertArticle(savedNewsArticle)
    }

    suspend fun deleteArticle(savedNewsArticle: SavedNewsEntity) {
        savedArticlesDao.deleteArticle(savedNewsArticle)
    }

}