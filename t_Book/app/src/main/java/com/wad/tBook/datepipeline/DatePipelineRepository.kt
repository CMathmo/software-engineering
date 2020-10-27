package com.wad.tBook.datepipeline

import com.wad.tBook.room.AccountingDao

class DatePipelineRepository (private val actDao: AccountingDao) {

    //    val readData = actDao.readAccountingData()
    var startDate:String = ""
    var endDate:String = ""
    var readAllData = actDao.readAllLiveDateFromAndTo(startDate,endDate)

    fun setDate(startDate:String, endDate:String){
        this.startDate = startDate
        this.endDate = endDate
        readAllData = actDao.readAllLiveDateFromAndTo(startDate,endDate)
    }

}