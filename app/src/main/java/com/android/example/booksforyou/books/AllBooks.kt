package com.android.example.booksforyou.books

import android.app.Application
import com.android.example.booksforyou.database.UserBooks
import com.android.example.booksforyou.navigation.WishlistItemViewHolder

class AllBooks {
    private var finishedBooks: MutableList<Book> = mutableListOf()
    private var inProgressBooks: MutableList<Book> = mutableListOf()

    fun getFinishedBooks(): MutableList<Book> {
        return finishedBooks
    }

    fun setFinishedBooks(newList: MutableList<UserBooks>) {
        finishedBooks = mutableListOf()
        for(b in newList) {
            finishedBooks.add(Book(b.bookName, b.bookAuthor, b.noPages, b.type, b.bookDate, b.photoLink))
        }
    }

    fun setInProgressBooks(newList: MutableList<UserBooks>) {
        inProgressBooks = mutableListOf()
        for(b in newList) {
        inProgressBooks.add(Book(b.bookName, b.bookAuthor, b.noPages, b.type, b.bookDate, b.photoLink))
        }
    }

    fun getInProgressBooks(): MutableList<Book> {
        return inProgressBooks
    }

    fun addFinishedBook(b: Book) {
        finishedBooks.add(b)
    }

    fun addInProgressBook(b: Book) {
        inProgressBooks.add(b)
    }

    fun removeFinishedBook(position: Int) {
        if(finishedBooks.size > position) {
            finishedBooks.removeAt(position)
        }
    }

    fun removeInProgressBook(position: Int) {
        if(inProgressBooks.size > position) {
            inProgressBooks.removeAt(position)
        }
    }
}