package com.wad.tBook.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.tBookDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AccountViewModel(application: Application) : AndroidViewModel(application) {
    val readActData : List<Accounting>
    val readAllData :LiveData<List<Accounting>>
    private val repo : AccountRepository

    init {
        val dao = tBookDatabase.getDBInstace(application).actDao()
        repo = AccountRepository(dao)
        readAllData = repo.readAllData
        readActData = repo.readActData
    }

    fun getAccountType() = repo.getAccountType(readActData)

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
