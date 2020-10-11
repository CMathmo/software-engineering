package com.example.roomlearning.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountingViewModel(application: Application) : AndroidViewModel(application) {
    val readActData : LiveData<List<Accounting>>
    private val repo : AccountingRepository

    init {
        val dao = AccountingDatabase.getWindow(application).actDao()
        repo = AccountingRepository(dao)
        readActData = repo.readData
    }

    fun addActData(accounting: Accounting){
        viewModelScope.launch(Dispatchers.IO) {
            repo.addData(accounting)
        }
    }

    fun updateActData(accounting: Accounting){
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateData(accounting)
        }
    }

    fun deleteActData(accoutingId: String?){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteData(accoutingId)
        }
    }


}