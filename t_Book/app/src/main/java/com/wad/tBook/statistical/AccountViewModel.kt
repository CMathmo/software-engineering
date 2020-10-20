package com.wad.tBook.statistical

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    //    val readActData : LiveData<List<Accounting>>
    val readAllData :LiveData<List<Accounting>>
    private val repo : AccountRepository

    init {
        val dao = tBookDatabase.getDBInstace(application).actDao()
        repo = AccountRepository(dao)
        readAllData = repo.readAllData
    }

    fun getAccountType(accountinglist: List<Accounting>) = repo.getAccountType(accountinglist)

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

    fun deleteActData(accoutingId: Int?){
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteData(accoutingId)
        }
    }
}
