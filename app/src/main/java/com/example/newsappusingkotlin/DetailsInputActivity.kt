package com.example.newsappusingkotlin

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.newsappusingkotlin.databinding.ActivityDetailsInputBinding
import com.example.newsappusingkotlin.other.Constants

class DetailsInputActivity : AppCompatActivity() {

    private val binding: ActivityDetailsInputBinding by lazy {
        ActivityDetailsInputBinding.inflate(layoutInflater, null, false)
    }

    private lateinit var arrayAdapterForCountries: ArrayAdapter<String>
    private lateinit var arrayAdapterForLanguages: ArrayAdapter<String>

    private var selectedLanguage: String? = "English"
    private var selectedCountry: String? = "India"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupDropDownMenus()

        binding.buttonDetailsInputPage.setOnClickListener {
            getInput()
        }

    }

    private fun setupDropDownMenus() {
        val countries = resources.getStringArray(R.array.Countries)
        val languages = resources.getStringArray(R.array.languages)
        arrayAdapterForCountries =
            ArrayAdapter(applicationContext, R.layout.dropdown_menu_item_for_lang, countries)
        arrayAdapterForLanguages =
            ArrayAdapter(applicationContext, R.layout.dropdown_menu_item_for_lang, languages)

        binding.TvAutoCompleteForLanguage.setAdapter(arrayAdapterForLanguages)
        binding.TvAutoCompleteForCountry.setAdapter(arrayAdapterForCountries)

        binding.TvAutoCompleteForLanguage.setOnItemClickListener { adapterView, view, i, l ->
            selectedLanguage = arrayAdapterForLanguages.getItem(i)
            Log.d("Drop Down Menu", selectedLanguage.toString())
        }

        binding.TvAutoCompleteForCountry.setOnItemClickListener { adapterView, view, i, l ->
            selectedCountry = arrayAdapterForCountries.getItem(i)
            Log.d("Drop Down Menu", selectedCountry.toString())
        }
    }

    private fun getInput() {
        val mobileNumber = binding.ETUsersMobileNumber.text.toString()

        //currently Im storing details in sharedPreference Later I'll do it in room
        storeDataInSharedPref(mobileNumber)

        // navigate to next screen
        val intent = Intent(this, CategorySelectionActivity::class.java)
        intent.addFlags(
            Intent.FLAG_ACTIVITY_CLEAR_TOP or
                    Intent.FLAG_ACTIVITY_CLEAR_TASK or
                    Intent.FLAG_ACTIVITY_NEW_TASK
        )
        startActivity(intent)
    }

    private fun storeDataInSharedPref(mobileNumber:String) {
        val sharedPreferences: SharedPreferences? =
            getSharedPreferences(Constants.userDetailInputPrefKey, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor? = sharedPreferences?.edit()

        editor?.apply {
            putString(Constants.usersCountryPrefKey, selectedCountry)
            putString(Constants.usersLanguagePrefKey, selectedLanguage)
            putString(Constants.usersMobileNumberPrefKey, mobileNumber)
        }?.apply()
    }
}