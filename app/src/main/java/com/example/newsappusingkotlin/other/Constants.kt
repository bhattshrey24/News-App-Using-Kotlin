package com.example.newsappusingkotlin.other

object Constants {
    const val baseUrl: String = "https://newsapi.org/v2/"
    const val newsApiKey: String = "1e1164c809f34f4b821d4f1ac6443eb3"


    //Shared Pref keys
    const val authSharedPrefKey="Auth_shared_preference"
    const val userDetailInputPrefKey="user_detail_input"
    const val userEmailSharedPrefKey="users_email" // this is present in both shared prefs ie. auth and user detail input
    const val userPasswordSharedPrefKey="users_password"
    const val usersMobileNumberPrefKey="user_mobile_num"
    const val usersCountryPrefKey="user_country"
    const val usersLanguagePrefKey="user_language"
    const val usersNamePrefKey="users_name"
    const val usersSelectedCategories="users_selected_categories"


}