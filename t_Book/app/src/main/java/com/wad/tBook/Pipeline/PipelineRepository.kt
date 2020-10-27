package com.wad.tBook.pipeline

import com.wad.tBook.room.AccountingDao

class PipelineRepository (private val actDao: AccountingDao) {

    //    val readData = actDao.readAccountingData()

    val readAllData = actDao.readAccountingData()
}