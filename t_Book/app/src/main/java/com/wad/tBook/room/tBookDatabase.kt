package com.wad.tBook.room

import android.content.Context
import androidx.room.*



@Database(entities = [Accounting::class, User::class,Property::class], version = 5)

abstract class tBookDatabase : RoomDatabase() {
    //获取接口
    abstract fun actDao() : AccountingDao
    abstract fun useDao() : UserDao
    abstract fun proDao() : PropertyDao

    //生成实例
    companion object {
        @Volatile
        private var instance: tBookDatabase? = null
        fun getDBInstace(context: Context): tBookDatabase {
            if (instance == null) {
                synchronized(tBookDatabase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context,
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


