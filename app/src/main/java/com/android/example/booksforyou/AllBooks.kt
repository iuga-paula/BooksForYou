package com.android.example.booksforyou

class AllBooks {
    private val finishedBooks: MutableList<Book> = mutableListOf()
    private val inProgressBooks: MutableList<Book> = mutableListOf()

    fun getFinishedBooks(): MutableList<Book> {
        return finishedBooks
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
}