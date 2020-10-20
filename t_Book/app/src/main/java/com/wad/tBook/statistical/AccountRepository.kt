package com.wad.tBook.statistical

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.AccountingDao

class AccountRepository(private val actDao: AccountingDao) {

    //    val readData = actDao.readAccountingData()

    val readAllData = actDao.readAccountingData()

    fun addData(accounting: Accounting){
        actDao.addAccountingData(accounting)
    }

    fun getAccountType(accounting: List<Accounting>): List<AccountClass> {
        val creditCard = "信用卡"
        val eWallet = "电子钱包"
        val cash = "现金"
        val rechargeableCard = "充值卡"
        val bond = "债券"
        val secondClassList = mutableListOf<String>("平安银行","浦发银行","微信","支付宝","校园卡","沃尔玛购物卡","债券")
        val n = secondClassList.size
        val secondList = mutableListOf(AccountClass(secondClassList[0],0.0))
        for (index in 1 until n) {
            secondList.add(AccountClass(secondClassList[index],0.0))
        }
        for (index in 0 until n) {
            for (item in accounting) {
                val second = item.accountingClass.secondClass
                if (second == secondClassList[index])
                    when(item.accountingType) {
                        "收入" -> secondList[index].Amount += item.accountingAmount
                        else -> secondList[index].Amount -= item.accountingAmount
                    }
            }
        }
        val firstList = listOf(
            AccountClass(creditCard, 0.0),
            AccountClass(eWallet, 0.0),
            AccountClass(cash, 0.0),
            AccountClass(rechargeableCard, 0.0),
            AccountClass(bond, 0.0)
        )
        for (item in secondList){
            when(item.Class) {
                secondClassList[0] -> firstList[0].Amount += item.Amount
                secondClassList[1] -> firstList[0].Amount += item.Amount
                secondClassList[2] -> firstList[1].Amount += item.Amount
                secondClassList[3] -> firstList[1].Amount += item.Amount
                secondClassList[4] -> firstList[2].Amount += item.Amount
                secondClassList[5] -> firstList[2].Amount += item.Amount
                secondClassList[6] -> firstList[3].Amount += item.Amount
            }
        }
        return secondList
    }

    fun updateData(accounting: Accounting){
        actDao.updateAccountingData(accounting)
    }

    fun deleteData(accountingId: Int?){
        actDao.deleteAccountingData(accountingId)
    }

    data class TypeSummary(val type_total:Double,val accountinglist: List<Accounting>)
    data class AccountClass(
        @ColumnInfo(name = "accounting_class") var Class:String,
        @ColumnInfo(name = "accounting_amount") var Amount:Double
    )
    data class TypeAmount(
        @ColumnInfo(name = "accounting_type") var Type:String,
        @ColumnInfo(name = "accounting_amount") var Amount:Double
    )
}