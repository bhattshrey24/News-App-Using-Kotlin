package com.example.newsappusingkotlin.data.cache

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsappusingkotlin.data.models.Source
import com.google.gson.annotations.SerializedName

@Entity(tableName = "saved_news_table")
data class SavedNewsEntity(
   // @PrimaryKey(autoGenerate = true)

    @PrimaryKey
    var id: String,

//   var source: Source, // no need of this

    var author: String?,// the names here should be same as the json response keys

    var title: String?,

    var description: String?,

    @SerializedName("url")// u can have different name too but then u have to use the @serializedName annotation as shown here
    var urlToArticle: String?,

    var urlToImage: String?,

    var publishedAt: String?,

    var content: String?,
)