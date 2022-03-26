package com.example.newsappusingkotlin

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        supportActionBar?.hide()//removes the app/title bar

        Handler().postDelayed({
            //todo
            // put here the logic where u will check by seeing the authentication token whether user is logged in or not and accordingly send he/she to auth activity or main activity
            // put here the logic for pre loading stuff in cache(from the web/api) so that user don't have to wait again

            val intent = Intent(this, AuthenticationActivity::class.java)
            startActivity(intent)
            finish()// so that user cannot comeback to this screen by pressing back
        }, 5000)
    }
}