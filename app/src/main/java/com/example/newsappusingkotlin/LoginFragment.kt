package com.example.newsappusingkotlin

import android.content.Intent
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
import com.example.newsappusingkotlin.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment(myFragmentContainer: FrameLayout) : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    private val binding: FragmentLoginBinding by lazy {
        FragmentLoginBinding.inflate(layoutInflater, null, false)
    }

    private var fragmentContainer: FrameLayout =
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
        mAuth = FirebaseAuth.getInstance()
        binding.buttonLogIn.setOnClickListener {
            logIn(
                binding.editTextUsersEmail.text.toString(),
                binding.editTextTextPassword.text.toString()
            )
        }
    }

    private fun logIn(usersEmail: String, usersPassword: String) {
        if (usersEmail == "") {
            binding.editTextUsersEmail.error = "Enter Email"
            binding.editTextUsersEmail.requestFocus()
            return
        }
        if(usersPassword==""){
            binding.editTextTextPassword.error="Enter Password"
            binding.editTextTextPassword.requestFocus()
            return
        }


        binding.loginPageCircularProgressBar.visibility=View.VISIBLE
        val parentActivityReference = host as AuthenticationActivity // host simply returns the reference of the host activity , "as" is used to type cast

        mAuth.signInWithEmailAndPassword(usersEmail, usersPassword)
            .addOnCompleteListener(parentActivityReference) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    binding.loginPageCircularProgressBar.visibility=View.GONE

                    Toast.makeText(context, "Log In successful",
                        Toast.LENGTH_LONG).show()
                    val user = mAuth.currentUser

                    var intent=Intent(parentActivityReference,MainActivity::class.java)
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                    )// this makes sure that user cannot go back to the Log In activity when back button is pressed
                    startActivity(intent)
                   // updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                  //  Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Log In unsuccessful ${task.exception?.message}",
                        Toast.LENGTH_LONG).show()
                  //  updateUI(null)
                }
            }

    }

    private fun makePartOfTextClickable() {
        val ss = SpannableString("Don't have an account? Sign Up")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                // startActivity(Intent(this@AuthenticationActivity, NextActivity::class.java))
                val signUpFragment = SignUpFragment(fragmentContainer)
                parentFragmentManager.beginTransaction().apply {
                    replace(fragmentContainer.id, signUpFragment)
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