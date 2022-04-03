package com.android.example.booksforyou.database

import androidx.multidex.MultiDexApplication

class BooksApplication: MultiDexApplication() {
    val booksDatabase: BooksForYouDb by lazy { BooksForYouDb.getInstance(this) }
}