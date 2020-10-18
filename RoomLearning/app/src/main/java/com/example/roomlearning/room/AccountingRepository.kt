package com.example.roomlearning.room


class AccountingRepository(private val actDao: AccountingDao) {

    val readData = actDao.readAccountingData()

    fun addData(accounting: Accounting){
        actDao.addAccountingData(accounting)
    }

    fun updateData(accounting: Accounting){
        actDao.updateAccountingData(accounting)
    }

    fun deleteData(accountingId: String?){
        actDao.deleteAccountingData(accountingId)
    }
}