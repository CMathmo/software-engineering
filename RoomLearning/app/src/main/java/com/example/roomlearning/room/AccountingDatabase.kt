package com.example.roomlearning.room

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Accounting::class], version = 1)
abstract class AccountingDatabase : RoomDatabase() {
    abstract fun actDao() : AccountingDao

    companion object{
        fun getWindow(application: Application) : AccountingDatabase{
            return Room.databaseBuilder(application,AccountingDatabase::class.java,"accountDb")
                .build()
        }
    }
}