package com.wad.tBook.statistical

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase

class OtherStatisticalViewModel(application: Application): AndroidViewModel(application) {
    val readAllData : LiveData<List<Accounting>>
    private val repo : OtherStatisticalRepository

    init {
        val dao = tBookDatabase.getDBInstace(application).actDao()
        repo = OtherStatisticalRepository(dao)
        readAllData = repo.readAllData
    }

}