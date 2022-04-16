package com.example.newsappusingkotlin

import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsappusingkotlin.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.main_app_bar.*

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
    //  Fix app bar , add menu and icons in it fix Its UI (Done)
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

        registerForContextMenu(menu_vertical_button) //Right now u have to hold and press the button in order to open the menu ,  this sort of custom menu that opens when u click a view is called ContextMenu , menu_vertical_button is the id of the view on the click of which we want to show the menu

    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.app_bar_vertical_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bookmark -> Toast.makeText(
                applicationContext,
                "Bookmark Clicked",
                Toast.LENGTH_SHORT
            ).show()
            R.id.language_preference -> Toast.makeText(
                applicationContext,
                "Language Preference Clicked",
                Toast.LENGTH_SHORT
            ).show()
            R.id.text_size -> Toast.makeText(
                applicationContext,
                "Text Size Clicked",
                Toast.LENGTH_SHORT
            ).show()
            R.id.settings -> Toast.makeText(
                applicationContext,
                "Settings Clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
        return super.onContextItemSelected(item)
    }


}