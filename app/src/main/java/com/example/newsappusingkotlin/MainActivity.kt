package com.example.newsappusingkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
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
    // add theme properly ,like colors, strings , style , font , textSize etc so that I can change whole look of app from one place only
    // add push notification feature giving notification about of daily news
    // add "share with" feature
    // Authentication :-
    //  add create new user functionality
    //  add Custom Login
    //  add google and facebook login
    //  add forgot password functionality
    //  change myCity tab with my account Tab

    // Todo CURRENT:-
    //  complete the articles tab functionality ie. add swipe left to change tab feature like whatsapp(ie. by using tablayout and view pager I guess)
    //  Make Home Page like Whatsapp Tab so that I dont do many Api Calls Simultaneously
    //  Today:-
    //  add news watching page , where user can read the actual news
    //  add room ie. cache your news articles , user details etc and complete bookmark feature(Done)
    //  add users bookmarked items to firestore
    //  make app based on user , ie. when user logs in , get that users data from firestore and show that


    //Todo(future)
    // save users email and password more securely by using some encryption algo or use EncryptedSharedPreferences
    // update Firestore rules to private database from test database within 30 days otherwise it will stop working
    // Ensure that the listener mechanism I implemented is not creating any memory leaks
    // use navGraph to navigate instead of Intent
    // currently Im combining all 3 category articles in one list and showing it in Home page but it looks shabby so instead show different category articles in different tab(like whatsapp tabs)
    // add a "i" button on actionBar which tells user what each tabs shows , like home tab shows top news of your selected category etc

    //Todo(Future fix)
    // code is messy
    // list of news is not being cleared before new data arrives I guess
    // when the number of articles are less then 5 articles then articles page layout gets messed up
    // I guess viewpage and tablayout make objects of fragment of tab which is currently showing and its left and right tab , and destroyes the others so this makes recyclerView loose all content and we again have to resent it which creates delay and its not a good UX
    // To show full content you can do web scraping(as shown in udemy course) from the linkToArticle provided by the Api , but this is not full proof because the sites can change their UI and that will mess up the code of web scraping

    // Todo(Info For Future Errors)
    //  Error HTTP 429 ie too many calls , This is due to NewsApi giving my app penalty for doing lots for calls , so either make new account or find new api or stop for a while till the api removes the ban and fix too many calls error like in home page Im doing 3 calls , and on Resume doing many calls etc

    //Todo(Important Feature)
    // Full content of the articles is not visible cause its available in Paid Version of Api , Im using the free "Developer version"

    private val binding: ActivityMainBinding by lazy {//this is lazy initialization
        ActivityMainBinding.inflate(layoutInflater, null, false)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val myBottomNav = findViewById<BottomNavigationView>(R.id.my_bottom_nav)
        val navController = findNavController(R.id.my_nav_host_fragment)
        myBottomNav.setupWithNavController(navController)

        supportActionBar?.hide()

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
        MaterialAlertDialogBuilder(this).setTitle("Log Out")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ -> signOutUser() }
            .setNegativeButton("No") { dialogue, _ -> dialogue.dismiss() }
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
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences(Constants.authSharedPrefKey, Context.MODE_PRIVATE)
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
            R.id.bookmark -> {
                Toast.makeText(
                    applicationContext,
                    "Bookmark Clicked",
                    Toast.LENGTH_SHORT
                ).show()
                startActivity(Intent(this, BookMarkActivity::class.java))
            }
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