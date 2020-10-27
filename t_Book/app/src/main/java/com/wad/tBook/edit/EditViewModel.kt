
package com.wad.tBook.edit

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wad.tBook.room.tBookDatabase


class EditViewModel(application: Application, type: String,item: String,firstClass: String?):AndroidViewModel(application) {

    private val TAG = EditActivity::class.qualifiedName
    val readAllData : LiveData<List<String>>
    private val repo : EditRepository

    init {
        val dao = tBookDatabase.getDBInstace(application).proDao()
        repo = EditRepository(dao,type,item,firstClass)
        Log.d(TAG,"momo:EVM"+firstClass)
        readAllData = repo.readClass
    }
}

class EditViewModelFactory(private val application: Application, private val type: String, private val item: String, private val firstClass: String?=null) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EditViewModel(application, type, item, firstClass) as T
    }

}