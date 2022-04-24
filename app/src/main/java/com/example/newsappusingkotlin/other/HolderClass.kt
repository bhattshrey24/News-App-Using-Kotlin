package com.example.newsappusingkotlin.other

import com.example.newsappusingkotlin.data.models.NewsJsonReceiver

data class HolderClass(
    var newsReceiver: NewsJsonReceiver,
    var category:String,
)