package com.android.example.booksforyou.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_for_you_books")
class UserBooks(
    @PrimaryKey(autoGenerate = true)
    var bookId: Long = 0L,

    @ColumnInfo(name = "book_name")
    val bookName: String = "",

    @ColumnInfo(name = "book_author")
    val bookAuthor: String =" ",

    @ColumnInfo(name = "book_no_pages")
    val noPages : String = "",

    @ColumnInfo(name = "book_type")
    var type:String = "",

    @ColumnInfo(name = "book_date")
    var bookDate: String = "",

    @ColumnInfo(name = "book_photo_link")
    val photoLink: String = ""
)
{
}