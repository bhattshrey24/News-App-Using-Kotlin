package com.example.newsappusingkotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsappusingkotlin.databinding.ActivityNewsArticleDisplayBinding

class NewsArticleDisplayActivity : AppCompatActivity() {

    private val binding: ActivityNewsArticleDisplayBinding by lazy {
        ActivityNewsArticleDisplayBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}