package com.example.newsappusingkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappusingkotlin.adapters.CategorySelectionRecyclerAdapter
import com.example.newsappusingkotlin.databinding.ActivityCategorySelectionBinding
import com.example.newsappusingkotlin.other.Constants
import com.google.firebase.firestore.FirebaseFirestore

class CategorySelectionActivity : AppCompatActivity() {

    private val binding: ActivityCategorySelectionBinding by lazy {
        ActivityCategorySelectionBinding.inflate(layoutInflater, null, false)
    }
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.categorySelectionProgressBar.visibility= View.GONE

        layoutManager = LinearLayoutManager(this)
        binding.categorySelectionRecyclerView.layoutManager = layoutManager
        val adapter = CategorySelectionRecyclerAdapter(applicationContext)
        binding.categorySelectionRecyclerView.adapter = adapter

        binding.btnCategorySelection.setOnClickListener {
            val selectedCategoriesList = adapter.getSelectedCategories()
            if (selectedCategoriesList.size == 3) {
                binding.categorySelectionProgressBar.visibility= View.VISIBLE
                sendDetailsToFireStore(selectedCategoriesList)
                // here save the categories etc and do the firestore call and navigate to main activity
            }
        }
    }

    private fun sendDetailsToFireStore(selectedCategoriesList: MutableList<String>) {
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences(Constants.userDetailInputPrefKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()
        editor?.apply {
            // putString(Constants.userEmailSharedPrefKey,emailET)
            // putString(Constants.userPasswordSharedPrefKey,confirmPasswordET)
        }?.apply()

        val usersEmail = sharedPreferences?.getString(
            Constants.userEmailSharedPrefKey, ""
        ).toString()
        val usersMobileNumber = sharedPreferences?.getString(
            Constants.usersMobileNumberPrefKey, ""
        ).toString()
        val usersSelectedLang = sharedPreferences?.getString(
            Constants.usersLanguagePrefKey, ""
        ).toString()
        val usersCountry = sharedPreferences?.getString(
            Constants.usersCountryPrefKey, ""
        ).toString()
        val usersName = sharedPreferences?.getString(Constants.usersNamePrefKey, "").toString()


        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()

        user["mobile_number"] = usersMobileNumber
        user["users_country"] = usersCountry
        user["users_language"] = usersSelectedLang
        user["users_fav_categories"] = selectedCategoriesList
        user["users_email_address"] = usersEmail
        user["users_name"] = usersName

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                binding.categorySelectionProgressBar.visibility= View.GONE
                Toast.makeText(applicationContext, "Added Successfully", Toast.LENGTH_SHORT).show()

                // navigating to main activity
                var intent= Intent(this,MainActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                )// this makes sure that user cannot go back to the Log In activity when back button is pressed
                startActivity(intent)
            }
            .addOnFailureListener {
                binding.categorySelectionProgressBar.visibility= View.GONE
                Toast.makeText(
                    applicationContext,
                    "Error Occured while sending data",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

}