package com.example.newsappusingkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsappusingkotlin.other.Constants
import com.google.firebase.auth.FirebaseAuth

class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()//removes the app/title bar

        //todo
        // put here the logic for pre loading stuff in cache(from the web/api) so that user don't have to wait again

        val sharedPreferences: SharedPreferences =
            getSharedPreferences(Constants.authSharedPrefKey, Context.MODE_PRIVATE)
        val savedEmailString = sharedPreferences.getString(
            Constants.userEmailSharedPrefKey,
            ""
        )// the 2nd parameter is the default string ie. is sharedPreference does not find any string corresponding to the key you provided then this 2nd parameter value will be returned by the function
        val savedPasswordString =
            sharedPreferences.getString(Constants.userPasswordSharedPrefKey, "")

        if (savedEmailString?.isEmpty() != true) {// if this is empty means user either logged out or this is a new user
            loginUser(
                savedEmailString.toString(),
                savedPasswordString.toString()
            ) // logging In the user
        } else {
            Handler().postDelayed({
                // Just cleaning the sharedPreference
                val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
                editor?.clear()?.commit()

                val intent = Intent(this, AuthenticationActivity::class.java)
                startActivity(intent)
                finish()// so that user cannot comeback to this screen by pressing back
            }, 3000)
        }
    }

    private fun loginUser(savedEmailString: String, savedPasswordString: String) {
        var mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithEmailAndPassword(savedEmailString, savedPasswordString)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    //val user = mAuth.currentUser
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()// so that user cannot comeback to this screen by pressing back
                } else {
                    Toast.makeText(
                        applicationContext, "Log In unsuccessful ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()

                    val intent = Intent(this, AuthenticationActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
    }

}