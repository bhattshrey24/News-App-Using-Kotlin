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
import com.google.gson.Gson

class CategorySelectionActivity : AppCompatActivity() {

    private val binding: ActivityCategorySelectionBinding by lazy {
        ActivityCategorySelectionBinding.inflate(layoutInflater, null, false)
    }

    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.categorySelectionProgressBar.visibility = View.GONE

        //setting up the recyclerView
        layoutManager = LinearLayoutManager(this)
        binding.categorySelectionRecyclerView.layoutManager = layoutManager
        val adapter = CategorySelectionRecyclerAdapter(applicationContext)
        binding.categorySelectionRecyclerView.adapter = adapter

        binding.btnCategorySelection.setOnClickListener {
            val selectedCategoriesList = adapter.getSelectedCategories()
            if (selectedCategoriesList.size == 3) {
                binding.categorySelectionProgressBar.visibility = View.VISIBLE
                sendDetailsToFireStore(selectedCategoriesList)
            }
        }
    }


    private fun sendDetailsToFireStore(selectedCategoriesList: MutableList<String>) {
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences(Constants.userDetailInputPrefKey, Context.MODE_PRIVATE)

        var arrayListConvertedToJson: String =
            convertArrayListToGson(selectedCategoriesList)//since we cannot save arrayList directly in sharedPref therefore we have to convert it in a form that can be saved in shared pref ,  this function converts the arrayList in string using "Gson" converter , basically Gson converts arrayList into Json which is nothing but a string

        val editorForUser: SharedPreferences.Editor? = sharedPreferences?.edit()

        editorForUser?.apply {
            putString(Constants.usersNamePrefKey, "nameET")
            putString(Constants.usersSelectedCategories, arrayListConvertedToJson)
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
        val user: MutableMap<String, Any> = HashMap() // imply creating a hashmap

        user["mobile_number"] = usersMobileNumber
        user["users_country"] = usersCountry
        user["users_language"] = usersSelectedLang
        user["users_fav_categories"] = selectedCategoriesList
        user["users_email_address"] = usersEmail
        user["users_name"] = usersName

        db.collection("users")
            .add(user) // passing the hashmap to friestore , it will extract key-value pair from it
            .addOnSuccessListener {
                binding.categorySelectionProgressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Added Successfully", Toast.LENGTH_SHORT).show()

                // navigating to main activity
                var intent = Intent(this, MainActivity::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                )// this makes sure that user cannot go back to the Log In activity when back button is pressed
                startActivity(intent)
            }
            .addOnFailureListener {
                binding.categorySelectionProgressBar.visibility = View.GONE
                Toast.makeText(
                    applicationContext,
                    "Error Occured while sending data",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

    private fun convertArrayListToGson(selectedCategoriesList: MutableList<String>): String {
        val gson = Gson()
        val json: String = gson.toJson(selectedCategoriesList)
        return json
    }

}