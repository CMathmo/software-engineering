package com.wad.tBook.statistical.Pipeline

import androidx.room.ColumnInfo
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.AccountingDao

class PipelineRepository (private val actDao: AccountingDao) {

    //    val readData = actDao.readAccountingData()

    val readAllData = actDao.readAccountingData()
}