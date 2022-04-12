package com.example.newsappusingkotlin.data.models

data class NewsJsonReceiver(
    var status: String,
    var totalResults: Int,
    var articles:List<News> // Gson will automatically convert Json result into your class and subclass just make sure the name of your properties matches the key in Json or use @SerializedName annotation
)