package com.example.newsappusingkotlin.other

object Constants {
    const val baseUrl: String = "https://newsapi.org/v2/"
   // Previous Key =  const val newsApiKey: String = "1e1164c809f34f4b821d4f1ac6443eb3"
   const val newsApiKey: String = "13b5b1293fb24d32847415be78d77ae5"

    //Shared Pref keys
    const val authSharedPrefKey = "Auth_shared_preference"
    const val userDetailInputPrefKey = "user_detail_input"
    const val userEmailSharedPrefKey =
        "users_email" // this is present in both shared prefs ie. auth and user detail input
    const val userPasswordSharedPrefKey = "users_password"
    const val usersMobileNumberPrefKey = "user_mobile_num"
    const val usersCountryPrefKey = "user_country"
    const val usersLanguagePrefKey = "user_language"
    const val usersNamePrefKey = "users_name"
    const val usersSelectedCategories = "users_selected_categories"


    //Categories
    const val category1 = "National"
    const val category2 = "Covid"
    const val category3 = "Stocks"
    const val category4 = "Business"
    const val category5 = "Entertainment"
    const val category6 = "General"
    const val category7 = "Health"
    const val category8 = "Science"
    const val category9 = "Sports"
    const val category10 = "Technology"
    const val numberOfCategories = 10

    //Tags For debug
    const val currentDebugTag = "Current Debug"
    const val permanentDebugTag = "Permanent Debug"

}