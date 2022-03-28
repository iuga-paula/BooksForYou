package com.android.example.booksforyou


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Book(val name: String, val authorName : String, val noPages : String,
           val type:String, val date: String, val photoLink: String) : Parcelable {

}