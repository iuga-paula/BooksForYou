package com.android.example.booksforyou.books


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Book(val name: String, val authorName : String, val noPages : String,
                var type:String, var date: String, val photoLink: String) : Parcelable {

}