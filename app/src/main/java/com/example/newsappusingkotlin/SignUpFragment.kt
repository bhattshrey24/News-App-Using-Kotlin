package com.example.newsappusingkotlin

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
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.newsappusingkotlin.databinding.FragmentSignUpBinding
import com.example.newsappusingkotlin.other.Constants
import com.example.newsappusingkotlin.ui.fragments.LoginFragment
import com.google.firebase.auth.FirebaseAuth
import java.util.regex.Pattern


class SignUpFragment(myFragmentContainer: FrameLayout) : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    private val binding: FragmentSignUpBinding by lazy {
        FragmentSignUpBinding.inflate(layoutInflater, null, false)
    }

    private var fragmentContainer: FrameLayout =
        myFragmentContainer// passed fragmentContainer as constructor  which is present in "Authentication activity" because we need it here to replace the fragment when use clicks on "Already Have an Account.."

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.signUpPageCircularProgressBar.visibility = View.GONE

        makePartOfTextClickable()

        mAuth = FirebaseAuth.getInstance()

        binding.buttonSignUp.setOnClickListener {
            validateUserInputs()
        }
    }

    private fun validateUserInputs() {
        // checking If user entered correct data or not
        var nameET: String = binding.editTextTextPersonName.text.toString().trim()
        var emailET: String = binding.editTextTextEmailAddress.text.toString().trim()
        var createPasswordEt: String = binding.editTextTextCreatePassword.text.toString().trim()
        var confirmPasswordET: String = binding.editTextTextConfirmPassword.text.toString().trim()

        if (nameET == "") {
            // Toast.makeText(context, "Please Enter Name", Toast.LENGTH_LONG).show()
            binding.editTextTextPersonName.setError("Please Enter Name") // this notifies the user what the error was
            binding.editTextTextPersonName.requestFocus()// this highlights the ET , so we are highlighting the ET that created problem here which is "name edit text"
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailET).matches()) {
            // Toast.makeText(context, "Enter Valid Email Address", Toast.LENGTH_LONG).show()
            binding.editTextTextEmailAddress.setError("Enter Valid Email Address")
            binding.editTextTextEmailAddress.requestFocus()
            return
        }
        if (createPasswordEt == "") {
            // Toast.makeText(context, "Password is required", Toast.LENGTH_LONG).show()
            binding.editTextTextCreatePassword.setError("Password is required")
            binding.editTextTextCreatePassword.requestFocus()
            return
        }

        if (createPasswordEt != (confirmPasswordET)) {
            //  Toast.makeText(context, "Confirm Password do not Match", Toast.LENGTH_LONG).show()
            binding.editTextTextConfirmPassword.setError("Confirm Password do not Match")
            binding.editTextTextConfirmPassword.requestFocus()
            return
        }

        val isValid =
            checkPassword(createPasswordEt)// checks whether password follows all the norms or not like has one uppercase and one lower case etc
        if (!isValid) {
            return // ie. if password do not follow all norms then don't proceed further
        }

        // If we reached here means user inserted correct email and password following all the norms
        createUser(nameET, emailET, createPasswordEt, confirmPasswordET)// create user
    }

    private fun createUser(
        nameET: String,
        emailET: String,
        createPasswordEt: String,
        confirmPasswordET: String
    ) {

        // Creating new user

        val parentActivityReference =
            host as AuthenticationActivity // host simply returns the reference of the host activity , "as" is used to type cast

        binding.signUpPageCircularProgressBar.visibility = View.VISIBLE

        mAuth.createUserWithEmailAndPassword(emailET, createPasswordEt)
            .addOnCompleteListener(parentActivityReference) { task ->
                if (task.isSuccessful) {

                    //saving users details so that user dont have to login again and again and we can send it to firestore
                    saveUserDetailsInSharedPref(emailET, confirmPasswordET, nameET)
                    //navigating to next screen
                    var intent = Intent(parentActivityReference, DetailsInputActivity::class.java)
                    intent.addFlags(
                        Intent.FLAG_ACTIVITY_CLEAR_TOP or
                                Intent.FLAG_ACTIVITY_CLEAR_TASK or
                                Intent.FLAG_ACTIVITY_NEW_TASK
                    )// this makes sure that user cannot go back to the Log In activity when back button is pressed
                    startActivity(intent) // navigating to main activity

                } else {
                    binding.signUpPageCircularProgressBar.visibility = View.GONE
                    Toast.makeText(
                        context, "Sign Up failed ${task.exception?.message} ",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

    }

    private fun saveUserDetailsInSharedPref(
        emailET: String,
        confirmPasswordET: String,
        nameET: String
    ) {

        val sharedPreferencesAuth: SharedPreferences? =
            activity?.getSharedPreferences(
                Constants.authSharedPrefKey,
                Context.MODE_PRIVATE
            )

        val editorAuth: SharedPreferences.Editor? = sharedPreferencesAuth?.edit()

        editorAuth?.apply {
            putString(Constants.userEmailSharedPrefKey, emailET)
            putString(Constants.userPasswordSharedPrefKey, confirmPasswordET)
        }?.apply()

        val sharedPreferencesUser: SharedPreferences? =
            activity?.getSharedPreferences(
                Constants.userDetailInputPrefKey,
                Context.MODE_PRIVATE
            )
        val editorForUser: SharedPreferences.Editor? = sharedPreferencesUser?.edit()
        editorForUser?.apply {
            putString(Constants.usersNamePrefKey, nameET)
            putString(Constants.userEmailSharedPrefKey, emailET)
        }?.apply()


        binding.signUpPageCircularProgressBar.visibility = View.GONE

        Toast.makeText(
            context, "Sign up successful",
            Toast.LENGTH_LONG
        ).show()
    }

    private fun checkPassword(password: String): Boolean { // just using regex to check if password follows all the norms or not

        val pAtLeastOneDigit = Pattern.compile("(?=.*[0-9])")//at least 1 digit
        val pAtLeastOneUpperCase = Pattern.compile("(?=.*[A-Z])") //at least 1 upper case letter
        val pAtLeastOneLowerCase = Pattern.compile("(?=.*[a-z])")//at least 1 lower case letter
        val pAnyLetter = Pattern.compile("(?=.*[a-zA-Z])")//contains letters
        val pAtLeast1SpecialCharacter =
            Pattern.compile("(?=.*[@#$%^&+=])")//at least 1 special character
        val pNoWhiteSpace = Pattern.compile("(?=\\S+$)")//no white spaces
        val pAtleast6Characters = Pattern.compile(".{6,}")//at least 6 characters

        val pattern = Pattern.compile(
            "^" + // checks if contains ^
                    pAtLeastOneDigit +
                    pAtLeastOneLowerCase +
                    pAtLeastOneUpperCase +
                    pAnyLetter +
                    pAtLeast1SpecialCharacter +
                    pNoWhiteSpace +
                    pAtleast6Characters +
                    "$" // checks if contains $
        )

        if (!pattern.matcher(password).matches()) {

            binding.editTextTextCreatePassword.setError("Password is not following all the norms")
            return false;
        }

        return true;// ie. if we reached till here means password is fine
    }

    private fun makePartOfTextClickable() {
        val ss = SpannableString("Already Have An Account? Login")
        val clickableSpan: ClickableSpan = object : ClickableSpan() {
            override fun onClick(textView: View) {
                // startActivity(Intent(this@AuthenticationActivity, NextActivity::class.java))
                val loginFragment = LoginFragment(fragmentContainer)
                parentFragmentManager.beginTransaction().apply {
                    replace(fragmentContainer!!.id, loginFragment)
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