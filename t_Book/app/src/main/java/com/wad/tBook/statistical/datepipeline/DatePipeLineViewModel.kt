package com.wad.tBook.statistical.datepipeline

import android.app.Application
import androidx.lifecycle.*
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase

class DatePipeLineViewModel(application: Application) : AndroidViewModel(application) {
    var readAccountingData : LiveData<List<Accounting>>
    private val repo : DatePipelineRepository

    init {
        val dao = tBookDatabase.getDBInstace(application).actDao()
        repo = DatePipelineRepository(dao)
        readAccountingData= repo.readAllData
    }

    fun setDate(startDate:String, endDate:String){
        repo.setDate(startDate,endDate)
        readAccountingData = repo.readAllData
    }
}