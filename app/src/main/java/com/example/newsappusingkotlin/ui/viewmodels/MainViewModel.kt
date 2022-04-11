package com.example.newsappusingkotlin.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.data.models.NewsHolderDummy
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MyRepository) : ViewModel() {
    val myResponse: MutableLiveData<NewsHolderDummy> = MutableLiveData()
    fun getPost() {
        viewModelScope.launch {
            val response: NewsHolderDummy = repository.getPost()
            myResponse.value = response
        }
    }
}