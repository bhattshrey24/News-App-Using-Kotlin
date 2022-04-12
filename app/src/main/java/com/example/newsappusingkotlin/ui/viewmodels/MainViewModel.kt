package com.example.newsappusingkotlin.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsappusingkotlin.data.models.NewsJsonReceiver
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MyRepository) : ViewModel() {

    val myResponse: MutableLiveData<NewsJsonReceiver> = MutableLiveData() // livedata so that our activity gets refreshed when couroutine is done with fetching

    fun getPost(country:String) {
        viewModelScope.launch {
            val response: NewsJsonReceiver = repository.getPost(country)
            myResponse.value = response // this will automatically update the UI because myReponse is LiveData
        }
    }
}