package com.example.newsappusingkotlin

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.newsappusingkotlin.databinding.ActivityAuthenticationBinding


class AuthenticationActivity : AppCompatActivity() {
    private val binding: ActivityAuthenticationBinding by lazy {
        ActivityAuthenticationBinding.inflate(layoutInflater, null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()//removes the app/title bar
        binding.circularProgressBar.visibility = View.GONE
        makePartOfTextClickable()// This method basically makes a part of Text clickable Like in "Dont have an account already?Login" here we can precisely make just "Login" part clickable
    }

    private fun makePartOfTextClickable() {
        val ss = SpannableString("Don't have an account? Login")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                // startActivity(Intent(this@AuthenticationActivity, NextActivity::class.java))
                Toast.makeText(this@AuthenticationActivity, "Yo Clicked", Toast.LENGTH_LONG).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.RED
                ds.isUnderlineText = false // if it's true then it will underline the text
            }
        }
        ss.setSpan(clickableSpan, 23, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.TextViewDontHaveAccount.movementMethod = LinkMovementMethod.getInstance()
        binding.TextViewDontHaveAccount.text = ss
        binding.TextViewDontHaveAccount.highlightColor = Color.TRANSPARENT

    }

}