package com.example.roomlearning.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountingDao {

    @Insert
    fun addAccountingData(accounting:Accounting)

    @Update
    fun updateAccountingData(accounting:Accounting)

    @Query("DELETE FROM accounting_table where accounting_id = :accountingId")
    fun deleteAccountingData(accountingId: String?)

    @Query("SELECT * FROM accounting_table")
    fun readAccountingData() : LiveData<List<Accounting>>
}