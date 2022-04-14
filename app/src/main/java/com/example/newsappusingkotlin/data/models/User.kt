package com.example.newsappusingkotlin.data.models

data class User(
    val usersName: String,
    val usersEmailAddress: String,
    val usersPassword: String // don't store password
)

//Todo
// saved articles links or something
// users interested topics so that we only show those movies
// user details like city , language etc