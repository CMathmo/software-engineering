package com.wad.tBook.edit

import com.wad.tBook.room.AccountingDao
import com.wad.tBook.room.PropertyDao

class EditRepository (private val proDao: PropertyDao, val type: String, val item: String, val firstClass:String?) {

    //    val readData = actDao.readAccountingData()

    val readClass = if(firstClass == null){
        proDao.getLiveFirstClassFrom(type,item)
    }else{
        proDao.getLiveSecondClassFrom(type,item,firstClass)
    }
}