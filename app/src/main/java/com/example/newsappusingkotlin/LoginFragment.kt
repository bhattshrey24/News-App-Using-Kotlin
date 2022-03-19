package com.example.newsappusingkotlin

import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsappusingkotlin.databinding.ActivityMainBinding
import com.example.newsappusingkotlin.databinding.FragmentLoginBinding


class LoginFragment(myFragmentContainer:FrameLayout ) : Fragment(){
    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater, null, false)
    }

    private  var fragmentContainer:FrameLayout =
        myFragmentContainer // passed fragmentContainer as constructor  which is present in "Authentication activity" because we need it here to replace the fragment when use clicks on "Already Have an Account.."

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makePartOfTextClickable()// This method basically makes a part of Text clickable Like in "Dont have an account already?Login" here we can precisely make just "Login" part clickable
        binding.loginPageCircularProgressBar.visibility = View.GONE

    }

    private fun makePartOfTextClickable() {
        val ss = SpannableString("Don't have an account? Sign Up")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                // startActivity(Intent(this@AuthenticationActivity, NextActivity::class.java))
                val signUpFragment = SignUpFragment(fragmentContainer)
                parentFragmentManager.beginTransaction().apply {
                    replace(fragmentContainer.id,signUpFragment)
                    commit()
                }
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