package com.wad.tBook.statistical

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase

class PipelineViewModel(application: Application):AndroidViewModel(application) {
    val readAllData : LiveData<List<Accounting>>
    private val repo : PipelineRepository

    init {
        val dao = tBookDatabase.getDBInstace(application).actDao()
        repo = PipelineRepository(dao)
        readAllData = repo.readAllData
    }

}