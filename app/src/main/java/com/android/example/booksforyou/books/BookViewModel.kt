package com.android.example.booksforyou.books

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.android.example.booksforyou.database.BooksForYouDao
import com.android.example.booksforyou.database.UserBooks
import kotlinx.coroutines.launch

class BookViewModel(private val booksDao: BooksForYouDao): ViewModel()  {
    val finishedBooks: LiveData<MutableList<UserBooks>> = booksDao.getAllFinishedBooks()
    val inProgressBooks: LiveData<MutableList<UserBooks>> = booksDao.getAllInProgressBooks()

    fun addNewBook(name: String, authorName : String, noPages : String, type:String, date: String,
                    photoLink: String) {
        val newBook = UserBooks(bookName = name, bookAuthor = authorName, noPages = noPages,
                                type = type, bookDate = date, photoLink = photoLink)
        insertNewBook(newBook)
    }

    fun deleteBook(bookName: String, authorName: String, type: String) {
        deleteBookFromDb(bookName, authorName, type)
    }

    fun finishBook(bookName: String, authorName: String) {
        updateFinisedBook(bookName, authorName)
    }

    fun insertNewBook(b : UserBooks) {
        viewModelScope.launch {
            booksDao.insertBook(b)
        }
    }

    fun deleteBookFromDb(bookName: String, authorName: String, type: String) {
        viewModelScope.launch {
            booksDao.deleteBook2(bookName, authorName, type)
        }
    }

    fun updateFinisedBook(bookName: String, authorName: String) {
        viewModelScope.launch {
            booksDao.updateBookType(bookName, authorName)
        }
    }

    class BookViewModelFactory(private val itemDao: BooksForYouDao) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return BookViewModel(itemDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}