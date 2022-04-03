package com.android.example.booksforyou.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_for_you_wishlist")
class UserWishlist(
    @PrimaryKey(autoGenerate = true)
    var wishId: Long = 0L,

    @ColumnInfo(name = "wishlist_info")
    val bookInfo: String = "",

    @ColumnInfo(name = "wishlist_obs")
    val obs: String = "",

    @ColumnInfo(name = "wishlist_date")
    val wishlistDate: Long = System.currentTimeMillis()
) {
}