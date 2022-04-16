package com.example.newsappusingkotlin.data.models

data class User(
    val id: String,// this is the id we get from firestore which well use to search for the user
    val usersName: String,
    val nationality: String,// will take it while creating new user , will use this to show user only the news of his/her location in home tab
    val languagePreference:String,// will take it while creating new user
    val bookmarkedArticles: List<News>
)

