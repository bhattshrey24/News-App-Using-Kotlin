package com.example.newsappusingkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
  //Todo
  // add splash screen
  // add firebase login functionality
  // add select language preference feature
  // add bottom navigation
  // add room
  // add retrofit
  // add theme colors
  // add push notification feature giving notification about of daily news
  // add drawer
  // add search feature
  // add "share with" feature

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myBottomNav=findViewById<BottomNavigationView>(R.id.my_bottom_nav)
        val navController=findNavController(R.id.my_nav_host_fragment)
        myBottomNav.setupWithNavController(navController)
    }
}