package com.example.newsappusingkotlin

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.newsappusingkotlin.databinding.ActivityDetailsInputBinding

class DetailsInputActivity : AppCompatActivity() {

    private val binding: ActivityDetailsInputBinding by lazy {
        ActivityDetailsInputBinding.inflate(layoutInflater, null, false)
    }

    private lateinit var arrayAdapterForCountries: ArrayAdapter<String>
    private lateinit var arrayAdapterForLanguages: ArrayAdapter<String>

    private var selectedLanguage:String?="English"
    private var selectedCountry:String?="India"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupDropDownMenus()

        binding.buttonDetailsInputPage.setOnClickListener {
            getInput(arrayAdapterForCountries, arrayAdapterForLanguages)
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

    private fun getInput(
        arrayAdapterForCountries: ArrayAdapter<String>,
        arrayAdapterForLanguages: ArrayAdapter<String>
    ) {
        // pass the selectedCountry and selectedLanguage to next page using bundle
        val mobileNumber = binding.ETUsersMobileNumber.text
        Log.d("Drop Down Menu", mobileNumber.toString())
        // navigate to next screen
    }
}