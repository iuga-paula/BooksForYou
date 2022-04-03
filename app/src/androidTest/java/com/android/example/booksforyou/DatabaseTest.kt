package com.android.example.booksforyou

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.android.example.booksforyou.database.BooksForYouDao
import com.android.example.booksforyou.database.BooksForYouDb
import com.android.example.booksforyou.database.UserSettings
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseTest {

    private lateinit var booksDao: BooksForYouDao
    private lateinit var db: BooksForYouDb

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, BooksForYouDb::class.java)
            .allowMainThreadQueries()
            .build()
        booksDao = db.databaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetSetting() {
        val s = UserSettings()
        booksDao.insertSetting(s)
        val currentSetting = booksDao.getSetting()
        Assert.assertEquals(currentSetting.buyingReminders, true)
        Assert.assertEquals(currentSetting.readingReminders, true)


    }
}