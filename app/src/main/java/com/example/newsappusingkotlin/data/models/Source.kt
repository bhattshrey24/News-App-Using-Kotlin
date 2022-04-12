package com.example.newsappusingkotlin.data.models

import com.google.gson.annotations.SerializedName

data class Source(
    @SerializedName("id")
    var id: String,
    var name:String,
    )