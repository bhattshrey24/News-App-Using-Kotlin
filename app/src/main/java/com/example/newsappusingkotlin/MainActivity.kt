package com.example.newsappusingkotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsappusingkotlin.data.remote.repository.MyRepository
import com.example.newsappusingkotlin.databinding.ActivityMainBinding
import com.example.newsappusingkotlin.ui.viewmodels.MainViewModel
import com.example.newsappusingkotlin.ui.viewmodels.MainViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //Todo
    // add select language preference feature
    // add room
    // add retrofit
    // add theme colors
    // add push notification feature giving notification about of daily news
    // add "share with" feature
    // Current:-
    // add recycler view
    // make fetching of random news from newsApi using retrofit
    // add drawer
    // add search feature
    // Authentication :-
    //  add create new user functionality
    //  add Custom Login
    //  add google and facebook login
    //  add forgot password functionality

    private val binding: ActivityMainBinding by lazy {//this is lazy initialization
        ActivityMainBinding.inflate(layoutInflater, null, false)
    }
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val repository = MyRepository()
//        val viewModelFactory = MainViewModelFactory(repository)
//
//        viewModel= ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
//        viewModel.getPost("in") // in is the code for "India"
//
//        viewModel.myResponse.observe(this, Observer { response->
//            Log.d("Response " , "Response is : $response")
//        })
        val myBottomNav = findViewById<BottomNavigationView>(R.id.my_bottom_nav)
        val navController = findNavController(R.id.my_nav_host_fragment)
        myBottomNav.setupWithNavController(navController)

    }
}