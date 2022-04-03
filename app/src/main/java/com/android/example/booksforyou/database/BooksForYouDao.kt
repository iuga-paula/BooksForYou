package com.android.example.booksforyou.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BooksForYouDao {
    //books
    @Insert
    suspend fun insertBook(book: UserBooks)

    @Update
    suspend fun updateBook(book: UserBooks) //from in progress to finished

    @Delete
    suspend fun deleteBook(book: UserBooks)

    @Query("DELETE FROM books_for_you_books WHERE bookId = :id")
    suspend fun deleteBookId(id: Long)

    @Query("SELECT * FROM books_for_you_books WHERE book_type = \"in_progress\" ORDER BY book_date DESC")
     fun getAllInProgressBooks(): LiveData<MutableList<UserBooks>>

    @Query("SELECT * FROM books_for_you_books  WHERE book_type = \"finished\" ORDER BY book_date DESC")
    fun getAllFinishedBooks(): LiveData<MutableList<UserBooks>>


    //wishes
    @Insert
   suspend fun insertWish(wish: UserWishlist)

    @Delete
    suspend fun deleteWish(wish: UserWishlist)

    @Query("DELETE FROM books_for_you_wishlist WHERE wishId = :id")
    suspend fun deleteWishId(id: Long)

    @Query("DELETE FROM books_for_you_wishlist WHERE wishlist_info = :info AND wishlist_obs = :obs")
    suspend fun deleteWish2(info: String, obs: String)

    @Query("SELECT * FROM books_for_you_wishlist ORDER BY wishlist_date DESC")
    fun getAllWishes(): LiveData<MutableList<UserWishlist>>


    //settings
    @Insert
    fun insertSetting(s: UserSettings)

    @Update
    fun updateSetting(s: UserSettings)

    @Delete
    fun deleteSetting(s: UserSettings)

    @Query("SELECT * FROM books_for_you_settings LIMIT 1")
    fun getSetting(): UserSettings

    @Query("UPDATE books_for_you_settings  SET settings_email= :email")
    fun updateEmail(email: String)

    @Query("UPDATE books_for_you_settings  SET settings_reading_reminder= :r")
    fun updateReadingReminder(r: Boolean)

    @Query("UPDATE books_for_you_settings  SET settings_buying_reminder = :r")
    fun updateBuyingReminder(r: Boolean)


}