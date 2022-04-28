package com.example.newsappusingkotlin.other

import androidx.lifecycle.ViewModelProvider
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCache
import com.example.newsappusingkotlin.ui.viewmodels.ViewModelForCacheFactory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CommonFunctions { // this class contains functions that are common to many classes , this reduces duplicate code problem

    companion object{// it basically make everything inside 'static'
        fun convertArrayListToGson(selectedCategoriesList: MutableList<String>): String {
            val gson = Gson()
            return gson.toJson(selectedCategoriesList)
        }
        fun convertFromJsonToList(json: String?): ArrayList<String> {
            var gson: Gson = Gson()
            val type = object : TypeToken<ArrayList<String?>>() {}.type
            return gson.fromJson(json, type);
        }
    }

}