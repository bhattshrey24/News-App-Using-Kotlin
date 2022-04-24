package com.example.newsappusingkotlin.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappusingkotlin.data.remote.repository.MyRepository

class ArticlesPageViewModelFactory(private val repository: MyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ArticlesPageViewModel(repository) as T
    }
}