package com.wad.tBook.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wad.tBook.statistical.AccountRepository

@Dao
interface AccountingDao {

    @Delete()
    fun delete(uaccounting: Accounting): Int

    @Query("delete from accounting_table")
    fun deleteAll():Int

    @Insert
    fun addAccountingData(vararg accounting: Accounting): List<Long>

    @Update
    fun updateAccountingData(accounting: Accounting): Int

    @Query("DELETE FROM accounting_table WHERE accounting_id = :accountingId")
    fun deleteAccountingData(accountingId: Int?): Int

    @Query("SELECT * FROM accounting_table WHERE accounting_id = :accountingId")
    fun getAccountingData(accountingId: Int?) : List<Accounting>

    @Query("SELECT * FROM accounting_table")
    fun readAccountingData() : LiveData<List<Accounting>>

    @Query("SELECT * FROM accounting_table")
    fun readAccountingDataWithoutLiveData() : List<Accounting>

    @Query("SELECT * FROM accounting_table")
    fun getAllAccountingData() : List<Accounting>

    @Query("SELECT accounting_amount FROM accounting_table WHERE accounting_type = '收入'  ")
    fun getAllIncomeAccountingData() : List<Double>

    @Query("SELECT accounting_amount FROM accounting_table WHERE accounting_type = '支出'  ")
    fun getAllExpenditureAccountingData() : List<Double>

    @Query("SELECT accounting_amount FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_account_second_class = :accountingAccount) " +
            "OR (accounting_type = '转账' AND accounting_account_2_second_class = :accountingAccount)  ")
    fun getAllIncomeAccountingDataIn(accountingAccount:String) : List<Double>

    @Query("SELECT accounting_amount FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_account_second_class = :accountingAccount)" +
            "OR (accounting_type = '转账' AND accounting_account_2_second_class = :accountingAccount)  ")
    fun getAllExpenditureAccountingDataIn(accountingAccount:String) : List<Double>


    @Query("SELECT accounting_type,accounting_amount FROM accounting_table")
    fun readAccountTypeData() : List<AccountRepository.TypeAmount>

    @Query("SELECT accounting_type,accounting_amount FROM accounting_table WHERE accounting_account_second_class = :accountingAccount")
    fun readAccountDetailData(accountingAccount:String) : List<AccountRepository.TypeAmount>


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
    @Embedded(prefix = "accounting_class_")
    var accountingClass: MultilevelClassification,//分类
    @Embedded(prefix = "accounting_account_")
    var accountingAcconut: MultilevelClassification,//账户(一般/转出)
    @Embedded(prefix = "accounting_account_2_")
    var accountingAcconut_2: MultilevelClassification? = null,//账户(转入)
    @ColumnInfo(name = "accounting_time")
    var accountingTime: String,//时间
    @Embedded(prefix = "accounting_member_")
    var accountingMember: MultilevelClassification? = null,//成员，选填
    @Embedded(prefix = "accounting_project_")
    var accountingProject: MultilevelClassification? = null,//项目，选填
    @Embedded(prefix = "accounting_merchant_")
    var accountingMerchant: MultilevelClassification? = null,//商家，选填
    @ColumnInfo(name = "accounting_remark")
    var accountingRemark: String,//备注，选填
    @ColumnInfo(name = "accounting_imagine")
    var accountingImg: String? = null//图片
)

data class MultilevelClassification(
    @ColumnInfo(name = "first_class")
    var firstClass: String,
    @ColumnInfo(name = "second_class")
    var secondClass: String
)
