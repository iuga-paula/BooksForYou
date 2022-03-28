package com.android.example.booksforyou.books

import java.util.*

class BookComparator {
    companion object : Comparator<Book> {
        override fun compare(a: Book, b: Book): Int { // day/month/year
            val aItems = a.date.split("/")
            val bItems = b.date.split("/")

            val day1 = aItems[0].toInt()
            val day2 = bItems[0].toInt()
            val month1 = aItems[1].toInt()
            val month2 = bItems[1].toInt()
            val year1 = aItems[2].toInt()
            val year2 = bItems[2].toInt()

            return when {
                year1 != year2 -> year1 - year2
                month1 != month2 -> month1 - month2
                else -> day1 - day2
            }
        }

    }
}