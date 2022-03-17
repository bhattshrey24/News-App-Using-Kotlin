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
import com.example.newsappusingkotlin.databinding.FragmentSignUpBinding


class SignUpFragment(myFragmentContainer:FrameLayout?) : Fragment() {

    private val binding: FragmentSignUpBinding by lazy {
        FragmentSignUpBinding.inflate(layoutInflater, null, false)
    }

    private var fragmentContainer: FrameLayout?=null // passed fragmentContainer as constructor  which is present in "Authentication activity" because we need it here to replace the fragment when use clicks on "Already Have an Account.."

    init {
        fragmentContainer=myFragmentContainer // this is how we use values provided as constructor ie we save it in a local variable inside Init
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        makePartOfTextClickable()
        binding.signUpPageCircularProgressBar.visibility = View.GONE

    }

    private fun makePartOfTextClickable() {
        val ss = SpannableString("Already Have An Account? Login")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                // startActivity(Intent(this@AuthenticationActivity, NextActivity::class.java))
                val loginFragment = LoginFragment(fragmentContainer)
                parentFragmentManager.beginTransaction().apply {
                    replace(fragmentContainer!!.id,loginFragment)
                    commit()
                }
            // Toast.makeText(context, "Yo Clicked Login", Toast.LENGTH_LONG).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.RED
                ds.isUnderlineText = false // if it's true then it will underline the text
            }
        }
        ss.setSpan(clickableSpan, 24, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.TextViewAlreadyHaveAnAccount.movementMethod = LinkMovementMethod.getInstance()
        binding.TextViewAlreadyHaveAnAccount.text = ss
        binding.TextViewAlreadyHaveAnAccount.highlightColor = Color.TRANSPARENT
    }

}