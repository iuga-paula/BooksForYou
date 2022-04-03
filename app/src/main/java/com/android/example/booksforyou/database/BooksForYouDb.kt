package com.android.example.booksforyou.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.example.booksforyou.navigation.Wishlist

@Database(entities = [UserBooks::class, UserSettings::class, UserWishlist::class], version = 1, exportSchema = false)
abstract class BooksForYouDb: RoomDatabase() {
    abstract val databaseDao: BooksForYouDao

    companion object {

        @Volatile
        private var INSTANCE: BooksForYouDb? = null

        fun getInstance(context: Context): BooksForYouDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BooksForYouDb::class.java,
                        "booksForYou_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}