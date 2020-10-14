package com.wad.tBook.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AccountingDao {

    @Delete
    fun delete(uaccounting: Accounting): Int

    @Insert
    fun addAccountingData(vararg accounting: Accounting): List<Long>

    @Update
    fun updateAccountingData(accounting: Accounting): Int

    @Query("DELETE FROM accounting_table where accounting_id = :accountingId")
    fun deleteAccountingData(accountingId: String?): Int

    @Query("SELECT * FROM accounting_table")
    fun readAccountingData() : LiveData<List<Accounting>>
}

//账目表
@Entity(tableName = "accounting_table")
data class Accounting(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "accounting_id")
    var accountingId: Int = 0,//id，自动生成
    @ColumnInfo(name = "accounting_amount")
    var accountingAmount: Double,//金额
    @ColumnInfo(name = "accounting_first_class")
    var accountingFirstClass: String,//一类
    @ColumnInfo(name = "accounting_second_class")
    var accountingSecondClass: String? = null,//二类
    @ColumnInfo(name = "accounting_project")
    var accountingProject: String? = null,//项目，选填
    @ColumnInfo(name = "accounting_member")
    var accountingMember: String? = null,//成员，选填
    @ColumnInfo(name = "accounting_type")
    var accountingType: String,//类别
    @ColumnInfo(name = "accounting_account")
    var accountingAcconut: String,//账户
    @ColumnInfo(name = "accounting_merchant")
    var accountingMerchant: String? = null,//商家，选填
    @ColumnInfo(name = "accounting_Time")
    var accountingTime: String,//时间
    @ColumnInfo(name = "accounting_remark")
    var accountingRemark: String? = null,//备注，选填
    @ColumnInfo(name = "accounting_imagine")
    var accountingImg: String? = null//图片
)
