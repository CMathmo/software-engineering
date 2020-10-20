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

    @Query("SELECT * FROM accounting_table")
    fun getAllAccountingData() : List<Accounting>


}

//账目表
@Entity(tableName = "accounting_table")
data class Accounting(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "accounting_id")
    var accountingId: Int = 0,//id，自动生成
    @ColumnInfo(name = "accounting_type")
    var accountingType: String,//账目类别
    @ColumnInfo(name = "accounting_amount")
    var accountingAmount: Double,//金额
    @Embedded(prefix = "accounting_class")
    var accountingClass: MultilevelClassification,//分类
    @Embedded(prefix = "accounting_account")
    var accountingAcconut: MultilevelClassification,//账户
    @ColumnInfo(name = "accounting_Time")
    var accountingTime: String,//时间
    @Embedded(prefix = "accounting_member")
    var accountingMember: MultilevelClassification? = null,//成员，选填
    @Embedded(prefix = "accounting_project")
    var accountingProject: MultilevelClassification? = null,//项目，选填
    @Embedded(prefix = "accounting_merchant")
    var accountingMerchant: MultilevelClassification? = null,//商家，选填
    @ColumnInfo(name = "accounting_remark")
    var accountingRemark: String? = null,//备注，选填
    @ColumnInfo(name = "accounting_imagine")
    var accountingImg: String? = null//图片
)

data class MultilevelClassification(
    @ColumnInfo(name = "first_class")
    var firstClass: String,
    @ColumnInfo(name = "second_class")
    var secondClass: String
)
