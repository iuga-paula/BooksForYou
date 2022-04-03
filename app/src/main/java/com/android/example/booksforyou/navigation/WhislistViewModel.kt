package com.android.example.booksforyou.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.example.booksforyou.database.BooksForYouDao
import com.android.example.booksforyou.database.UserWishlist
import kotlinx.coroutines.launch

class WishlistViewModel(private val booksDao: BooksForYouDao): ViewModel() {
    val allItems: LiveData<MutableList<UserWishlist>> = booksDao.getAllWishes()

    fun addNewWish(bookName: String, obs: String) {
        val newWish = UserWishlist(bookInfo = bookName, obs = obs)
        insertNewWish(newWish)
    }

    fun deleteWish(bookName: String, obs: String) {
        deleteWishfromDb(bookName, obs)
    }

    private fun insertNewWish(wish: UserWishlist) {
        viewModelScope.launch {
            booksDao.insertWish(wish)
        }

    }

    private fun deleteWishfromDb(bookName: String, obs: String) {
        viewModelScope.launch {
            booksDao.deleteWish2(bookName, obs)
        }
    }

    class WishlistViewModelFactory(private val itemDao: BooksForYouDao) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WishlistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return WishlistViewModel(itemDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}