package com.example.newsappusingkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.newsappusingkotlin.databinding.ActivityMainBinding
import com.example.newsappusingkotlin.other.Constants
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.view.*
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
    //  Develop Ui to take inputs from user about users interest,mobile number(maybe later add Otp functionality), age, nationality,language-preference and text size text
    //  add user functionality ie. user data stored in firebase firestore having its username , interests , saved/bookmarked news articles
    //  based on user interests show news based on users nationality and interest on home page
    //  complete the articles tab functionality ie. add swipe left to change tab feature like whatsapp(ie. by using tablayout and view pager I guess)
    //  add room ie. cache your news articles , user details etc and complete bookmark feature


    //Todo(future)
    // save users email and password more securely by using some encryption algo or use EncryptedSharedPreferences

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
        navDrawerSetup()

    }

    private fun navDrawerSetup() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navDrawerView: NavigationView = binding.drawerNavigationView.drawer_navigation_view

        binding.mainAppBar.drawerButton.setOnClickListener {// this is how we call methods of 'included' view using binding ie. by simply using dot operator "."
            drawerLayout.openDrawer(Gravity.LEFT)// this opens the drawer when user clicks on drawer icon
        }

        navDrawerView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.about_us -> {
                    Toast.makeText(
                        applicationContext,
                        "Drawer about us Pressed",
                        Toast.LENGTH_SHORT
                    ).show()
                    drawerLayout.closeDrawer(Gravity.LEFT) // this closes the drawer after user clicked on this item
                }
                R.id.share_us -> {
                    Toast.makeText(
                        applicationContext,
                        "Drawer share us Pressed",
                        Toast.LENGTH_SHORT
                    ).show()
                    drawerLayout.closeDrawer(Gravity.LEFT)
                }
                R.id.privacy_policy -> {
                    Toast.makeText(
                        applicationContext,
                        "Drawer privacy policy Pressed",
                        Toast.LENGTH_SHORT
                    ).show()
                    drawerLayout.closeDrawer(Gravity.LEFT)
                }
                R.id.sign_out -> {
                    drawerLayout.closeDrawer(Gravity.LEFT)
                    showSignOutDialogueBox()
                }
            }
            true
        }
    }

    private fun showSignOutDialogueBox() {
        MaterialAlertDialogBuilder(this).
        setTitle("Log Out")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes"){ _, _ -> signOutUser()}
            .setNegativeButton("No"){dialogue,_-> dialogue.dismiss() }
            .show()
    }

    private fun signOutUser() {
        Toast.makeText(
            applicationContext,
            "Successfully Signed Out",
            Toast.LENGTH_SHORT
        ).show()

        FirebaseAuth.getInstance().signOut()

        // deleting the sharedPreference that contains users password and email so that user can log in again with either same or not account
        val sharedPreferences: SharedPreferences? =getSharedPreferences(Constants.authSharedPrefKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.clear()?.commit() // deletes the data present in sharedPreference

        val intent = Intent(this, AuthenticationActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )// this makes sure that user cannot go back to the main activity when back button is pressed
        startActivity(intent)
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