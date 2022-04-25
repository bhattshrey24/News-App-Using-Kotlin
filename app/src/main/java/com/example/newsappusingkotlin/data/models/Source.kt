package com.example.newsappusingkotlin.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    @SerializedName("id")
    var id: String?,
    var name:String?,
    ):Parcelable