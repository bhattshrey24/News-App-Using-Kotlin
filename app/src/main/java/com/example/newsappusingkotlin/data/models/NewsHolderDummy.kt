package com.example.newsappusingkotlin.data.models

import com.google.gson.annotations.SerializedName

data class NewsHolderDummy(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,// the names here should be same as the json response keys

    // u can have different name too but then u have to use the @serializedName annotation as shown below
   //  @SerializedName("body")
   //  val myBody: String
)