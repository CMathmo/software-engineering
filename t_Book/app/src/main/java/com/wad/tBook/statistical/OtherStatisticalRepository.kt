package com.wad.tBook.statistical

import androidx.room.ColumnInfo
import com.wad.tBook.room.Accounting
import com.wad.tBook.room.AccountingDao

class OtherStatisticalRepository(private val actDao: AccountingDao) {
    val readAllData = actDao.readAccountingData()
    data class proType(
        @ColumnInfo(name = "property_first_class") var firstClass:String,
        @ColumnInfo(name = "property_second_class") var secondClass:String
    )
    data class TA(
        var inflowAmount: Double,
        var outflowAmount: Double,
        @ColumnInfo(name = "first_class") val firstClass: String,
        @ColumnInfo(name = "second_class") val secondClass: String
    )
    data class TA_fc(
        var inflowAmount: Double,
        var outflowAmount: Double,
        @ColumnInfo(name = "first_class") val firstClass: String
    )
    fun classStatistical(accountingList:List<Accounting>,classList:MutableList<TA>){
        val n = accountingList.size
        for (value in accountingList){
            for (item in classList){
                if (item.firstClass == value.accountingClass.firstClass && item.secondClass == value.accountingClass.secondClass){
                    when(value.accountingType){
                        "收入" -> item.inflowAmount += value.accountingAmount
                        else -> item.outflowAmount -= value.accountingAmount
                    }
                }
            }
        }
        println(classList)
    }
}