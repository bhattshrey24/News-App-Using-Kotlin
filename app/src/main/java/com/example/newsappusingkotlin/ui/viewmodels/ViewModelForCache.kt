package com.example.newsappusingkotlin.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.data.cache.MyDatabase
import com.example.newsappusingkotlin.data.cache.SavedNewsEntity
import com.example.newsappusingkotlin.data.remote.repository.LocalRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelForCache(application: Application) :
    AndroidViewModel(application) { // AndroidViewModel is different from normal View model because it contains application reference

    private val getAllNewsArticles: LiveData<List<SavedNewsEntity>>
    private val repository: LocalRepository

    init {
        val savedArticlesDAO = MyDatabase.getDatabase(application)
            .savedNewsArticlesDao() // we are getting our DAO from our Database class
        repository = LocalRepository(savedArticlesDAO)
        getAllNewsArticles = repository.getAllSavedNewsArticles
    }

    fun getAllArticles(): LiveData<List<SavedNewsEntity>> {
        return getAllNewsArticles
    }

    fun addNewsArticle(newsArticle: SavedNewsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addArticle(newsArticle)
        }
    }

    fun deleteNewsArticle(newsArticle: SavedNewsEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteArticle(newsArticle)
        }
    }

}