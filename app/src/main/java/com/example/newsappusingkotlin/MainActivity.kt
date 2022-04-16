package com.example.newsappusingkotlin

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsappusingkotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //Todo
    // add select language preference feature
    // add search feature
    // add retrofit
    // add theme colors
    // add push notification feature giving notification about of daily news
    // add "share with" feature
    // Authentication :-
    //  add create new user functionality
    //  add Custom Login
    //  add google and facebook login
    //  add forgot password functionality
    // CURRENT:-
    //  Fix app bar , add menu and icons in it fix Its UI
    //  add drawer
    //  add room ie. cache your news articles , user details etc
    //  add user functionality ie. user data stored in firebase having its username , interests , saved/bookmarked news articles


    private val binding: ActivityMainBinding by lazy {//this is lazy initialization
        ActivityMainBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val myBottomNav = findViewById<BottomNavigationView>(R.id.my_bottom_nav)
        val navController = findNavController(R.id.my_nav_host_fragment)
        myBottomNav.setupWithNavController(navController)
    }


}