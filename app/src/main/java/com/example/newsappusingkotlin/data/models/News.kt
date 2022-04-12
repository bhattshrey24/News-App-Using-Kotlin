package com.example.newsappusingkotlin.data.models

import com.google.gson.annotations.SerializedName

data class News(
    var source:Source,

    var author: String,// the names here should be same as the json response keys

    var title: String,

    var description: String,

    @SerializedName("url")// u can have different name too but then u have to use the @serializedName annotation as shown here
    var urlToArticle: String,

    var urlToImage: String,

    var publishedAt: String,

    var content: String,
)