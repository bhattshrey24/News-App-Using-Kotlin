package com.example.newsappusingkotlin.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappusingkotlin.data.remote.repository.MyRepository

class HomePageViewModelFactory(private val repository: MyRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        //We can not create ViewModel on our own. We need ViewModelProviders utility provided by Android to create ViewModels.
        // But ViewModelProviders can only instantiate ViewModels with no arg constructor.
        //So if I have a ViewModel with multiple arguments, then I need to use a Factory that I can pass to ViewModelProviders to use when an instance of MyViewModel is required.
        // the full article telling why we used factory method https://stackoverflow.com/questions/54419236/why-a-viewmodel-factory-is-needed-in-android
        return HomePageViewModel(repository) as T
    }
}