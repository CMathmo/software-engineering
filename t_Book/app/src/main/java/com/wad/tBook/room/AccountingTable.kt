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

    @Query("SELECT SUM(accounting_amount) FROM accounting_table WHERE accounting_type = '收入'  ")
    fun getAllIncomeAccountingData() : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table WHERE accounting_type = '支出'  ")
    fun getAllExpenditureAccountingData() : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_account_first_class = :accountingFAccount AND accounting_account_second_class = :accountingSAccount) " +
            "OR (accounting_type = '转账' AND accounting_account_2_first_class = :accountingFAccount AND accounting_account_2_second_class = :accountingSAccount)  ")
    fun getAllIncomeAccountingDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_account_first_class = :accountingFAccount AND accounting_account_second_class = :accountingSAccount)" +
            "OR (accounting_type = '转账' AND accounting_account_first_class = :accountingFAccount AND accounting_account_second_class = :accountingSAccount)  ")
    fun getAllExpenditureAccountingDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_class_first_class = :accountingFAccount) ")
    fun getFirstClassIncomeClassDataIn(accountingFAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_class_first_class = :accountingFAccount)" )
    fun getFirstClassExpenditureClassDataIn(accountingFAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_member_first_class = :accountingFAccount AND accounting_member_second_class = :accountingSAccount) ")
    fun getAllIncomeMemberDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_member_first_class = :accountingFAccount AND accounting_member_second_class = :accountingSAccount)" )
    fun getAllExpenditureMemberDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_merchant_first_class = :accountingFAccount AND accounting_merchant_second_class = :accountingSAccount) ")
    fun getAllIncomeMerchantDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_merchant_first_class = :accountingFAccount AND accounting_merchant_second_class = :accountingSAccount)" )
    fun getAllExpenditureMerchantDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_project_first_class = :accountingFAccount AND accounting_project_second_class = :accountingSAccount) ")
    fun getAllIncomeProjectDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_project_first_class = :accountingFAccount AND accounting_project_second_class = :accountingSAccount)" )
    fun getAllExpenditureProjectDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    @Query("SELECT accounting_type,accounting_amount FROM accounting_table")
    fun readAccountTypeData() : List<AccountRepository.TypeAmount>

    @Query("SELECT accounting_type,accounting_amount FROM accounting_table WHERE accounting_account_second_class = :accountingAccount")
    fun readAccountDetailData(accountingAccount:String) : List<AccountRepository.TypeAmount>

    @Query("SELECT * FROM accounting_table WHERE accounting_time >=:startDate AND accounting_time <=:endDate")
    fun readAllDateFromAndTo(startDate:String,endDate:String) : List<Accounting>

    @Query("SELECT * FROM accounting_table WHERE accounting_time >=:startDate AND accounting_time <=:endDate")
    fun readAllLiveDateFromAndTo(startDate:String,endDate:String) : LiveData<List<Accounting>>

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
