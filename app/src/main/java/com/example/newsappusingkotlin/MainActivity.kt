package com.example.newsappusingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsappusingkotlin.databinding.ActivityMainBinding
import com.example.newsappusingkotlin.databinding.FragmentMyCityBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
  //Todo
  // add splash screen
  // add firebase login functionality
  // add select language preference feature
  // add room
  // add retrofit
  // add theme colors
  // add push notification feature giving notification about of daily news
  // add drawer
  // add search feature
  // add "share with" feature
  // Authentication :-
  //  add create new user functionality
  //  add Custom Login
  //  add google and facebook login
  //  add forgot password functionality

    private val binding: ActivityMainBinding by lazy {//this is lazy initialization
        ActivityMainBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val myBottomNav=findViewById<BottomNavigationView>(R.id.my_bottom_nav)
        val navController=findNavController(R.id.my_nav_host_fragment)
        myBottomNav.setupWithNavController(navController)

    }
}