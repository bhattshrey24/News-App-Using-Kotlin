package com.example.newsappusingkotlin.ui.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsappusingkotlin.AuthenticationActivity
import com.example.newsappusingkotlin.MainActivity
import com.example.newsappusingkotlin.SignUpFragment
import com.example.newsappusingkotlin.data.cache.SavedNewsEntity
import com.example.newsappusingkotlin.databinding.FragmentLoginBinding
import com.example.newsappusingkotlin.other.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


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
            validateUserInputs(
                binding.editTextUsersEmail.text.toString().trim(),
                binding.editTextTextPassword.text.toString().trim()
            )
        }
    }

    private fun validateUserInputs(usersEmail: String, usersPassword: String) {
        if (usersEmail == "") {
            binding.editTextUsersEmail.error = "Enter Email"
            binding.editTextUsersEmail.requestFocus()
            return
        }
        if (usersPassword == "") {
            binding.editTextTextPassword.error = "Enter Password"
            binding.editTextTextPassword.requestFocus()
            return
        }

        logIn(usersEmail, usersPassword)
    }

    private fun logIn(usersEmail: String, usersPassword: String) {

        binding.facebookLoginBtn.setOnClickListener {
            facebookLogin()
        }
        binding.gmailLoginBtn.setOnClickListener {
            gmailLogin()
        }

        binding.loginPageCircularProgressBar.visibility = View.VISIBLE

        val parentActivityReference =
            host as AuthenticationActivity // host simply returns the reference of the host activity , "as" is used to type cast

        mAuth.signInWithEmailAndPassword(usersEmail, usersPassword)
            .addOnCompleteListener(parentActivityReference) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    binding.loginPageCircularProgressBar.visibility = View.GONE

                    //saving users details so that user dont have to login again and again
                    saveDataInSharedPref(usersEmail, usersPassword)

                    //setUsersData(mAuth.currentUser?.uid)

                    Toast.makeText(
                        context, "Log In successful",
                        Toast.LENGTH_LONG
                    ).show()
                    //val user = mAuth.currentUser

                    var intent = Intent(parentActivityReference, MainActivity::class.java)
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                    )// this makes sure that user cannot go back to the Log In activity when back button is pressed
                    startActivity(intent)
                } else {
                    binding.loginPageCircularProgressBar.visibility = View.GONE
                    Toast.makeText(
                        context, "Log In unsuccessful ${task.exception?.message}",
                        Toast.LENGTH_LONG
                    ).show()
                    //  updateUI(null)
                }
            }

    }

    private fun setUsersData(userId: String?) {
        val db = FirebaseFirestore.getInstance()
        db.collection(Constants.userCollectionFSKey).document(userId!!).get()
            .addOnCompleteListener {
                val mobileNumber = it.result.get(Constants.usersMobileNumberFSKey)
                val language = it.result.get(Constants.usersSelectedLangFSKey)
                val usersCountry = it.result.get(Constants.usersCountryFSKey)
                val usersSelectedCategories =
                    it.result.get(Constants.usersSelectedCategoriesListFSKey)
                val userEmail = it.result.get(Constants.usersEmailFSKey)
                val userName = it.result.get(Constants.usersNameFSKey)

     // save this data in sharedPred

            }.addOnFailureListener {
                Log.d(
                    Constants.currentDebugTag,
                    "ERROR while RETRIEVING data from FIRESTORE for User Details"
                )
            }


        db.collection(Constants.userCollectionFSKey).document(userId)
            .collection(Constants.userArticleDocumentFSKey).get().addOnCompleteListener {
                //Todo(current todo and problem)
                // in 'logout' add the code for deleting the Room database table and sharedPref data cause both of these will be repopulated during login for existing user and during Signup for new User
                // save data in SavedNewsEntity object and then pass it to Room

                for (document in it.result) {
                    val id =document.get(Constants.newsIDFSKey).toString()
                    val title = document.get(Constants.newsTitleFSKey).toString()
                    val author = document.get(Constants.newsAuthorFSKey).toString()
                    val content = document.get(Constants.newsContentFSKey).toString()
                    val description = document.get(Constants.newsDescriptionFSKey).toString()
                    val publishedAt = document.get(Constants.newsPublishedAtFSKey).toString()
                    val urlToArticle = document.get(Constants.newsUrlToArticleFSKey).toString()
                    val urlToImage = document.get(Constants.newsUrlToImageFSKey).toString()

                    val news = SavedNewsEntity(
                        id,
                        author,
                        title,
                        description,
                        urlToArticle,
                        urlToImage,
                        publishedAt,
                        content
                    )

                    // Save it in room but theres a problem , that room might save this with new
                    // Id cause I did 'autogenerate' in PrimaryKey , so Solution That
                    // I think are :- 1)Maybe If we give id other than 0 then it will save it with the
                    // id we give , 2)Or maybe add new feild where u add fireStore id which will be given
                    // default value of -1 which means its not set yet in which case ill delete from database
                    // using the id provided by room only and if its something then ill use that id to
                    // delete from firestore(this something else is basically id given by room previously) ,
                    // 3) or simply delete based On title cause this way id kuch bhi ho it wont matter

                    //Log.d(Constants.currentDebugTag,"Data from FIRESTORE is $title")
                }

            }.addOnFailureListener {
                Log.d(
                    Constants.currentDebugTag,
                    "ERROR while RETRIEVING data from FIRESTORE for users saved Articles"
                )
            }
    }


    private fun saveDataInSharedPref(usersEmail: String, usersPassword: String) {
        val sharedPreferences: SharedPreferences? =
            activity?.getSharedPreferences(Constants.authSharedPrefKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.apply {
            putString(Constants.userEmailSharedPrefKey, usersEmail)
            putString(Constants.userPasswordSharedPrefKey, usersPassword)
        }?.apply()

    }

    private fun gmailLogin() {
        TODO("Not yet implemented")
        Toast.makeText(context, "Under Development", Toast.LENGTH_LONG).show()
    }

    private fun facebookLogin() {
        TODO("Not yet implemented")
        Toast.makeText(context, "Under Development", Toast.LENGTH_LONG).show()
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
        ss.setSpan(clickableSpan, 23, ss.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.TextViewDontHaveAccount.movementMethod = LinkMovementMethod.getInstance()
        binding.TextViewDontHaveAccount.text = ss
        binding.TextViewDontHaveAccount.highlightColor = Color.TRANSPARENT
    }

}