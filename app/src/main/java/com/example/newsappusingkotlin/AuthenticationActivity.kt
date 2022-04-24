package com.example.newsappusingkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsappusingkotlin.databinding.ActivityAuthenticationBinding
import com.example.newsappusingkotlin.ui.fragments.LoginFragment


class AuthenticationActivity : AppCompatActivity() {

    private val binding: ActivityAuthenticationBinding by lazy {
        ActivityAuthenticationBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()//removes the app/title bar

        val loginFragment = LoginFragment(binding.fragmentContainer)// passing fragmentContainer in constructor cause we want to change/replace fragment from another fragment ie. when user clicks on "Already has an account" or "Dont have an account"

        supportFragmentManager.beginTransaction().apply { // In start We display Login Page , cause we are assuming that user already has an account
            replace(binding.fragmentContainer.id, loginFragment)
            commit()
        }


    }

}