package com.wad.tBook.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.*
import com.wad.tBook.room.*

@Database(entities = [Accounting::class, User::class,Property::class], version = 3)
abstract class tBookDatabase : RoomDatabase() {
    //获取接口
    abstract fun actDao() : AccountingDao
    abstract fun useDao() : UserDao
    abstract fun proDao() : PropertyDao

    //生成实例
    companion object {
        @Volatile
        private var instance: tBookDatabase? = null
        fun getDBInstace(application: Application): tBookDatabase {
            if (instance == null) {
                synchronized(tBookDatabase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            application,
                            tBookDatabase::class.java,
                            "tBook.db"
                        )
                            .allowMainThreadQueries()
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}

