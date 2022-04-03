package com.android.example.booksforyou.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books_for_you_settings")
class UserSettings(
    @PrimaryKey(autoGenerate = true)
    var settingId: Long = 0L,

    @ColumnInfo(name = "settings_reading_reminder")
    var readingReminders: Boolean = true,

    @ColumnInfo(name = "settings_buying_reminder")
    var buyingReminders: Boolean = true

)
{

}