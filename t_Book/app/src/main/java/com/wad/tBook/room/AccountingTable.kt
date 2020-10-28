package com.wad.tBook.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.wad.tBook.account.AccountRepository

@Dao
interface AccountingDao {

    //删除指定accountin
    @Delete()
    fun delete(uaccounting: Accounting): Int

    //清空表
    @Query("delete from accounting_table")
    fun deleteAll():Int

    //添加数据（单个或多个）
    @Insert
    fun addAccountingData(vararg accounting: Accounting): List<Long>

    //更新数据
    @Update
    fun updateAccountingData(accounting: Accounting): Int

    //删除指定id对于数据
    @Query("DELETE FROM accounting_table WHERE accounting_id = :accountingId")
    fun deleteAccountingData(accountingId: Int?): Int

    //选择指定id对应数据
    @Query("SELECT * FROM accounting_table WHERE accounting_id = :accountingId")
    fun getAccountingData(accountingId: Int?) : List<Accounting>

    //获取表中所有数据（livedata）
    @Query("SELECT * FROM accounting_table")
    fun readAccountingData() : LiveData<List<Accounting>>

    //获取表中所有数据（list）
    @Query("SELECT * FROM accounting_table")
    fun readAccountingDataWithoutLiveData() : List<Accounting>

    //获取表中所有数据（list）
    @Query("SELECT * FROM accounting_table")
    fun getAllAccountingData() : List<Accounting>

    //获取所有收入金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table WHERE accounting_type = '收入'  ")
    fun getAllIncomeAccountingData() : Double

    //获取所有支出金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table WHERE accounting_type = '支出'  ")
    fun getAllExpenditureAccountingData() : Double

    //获取指定二级账户下流入金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_account_first_class = :accountingFAccount AND accounting_account_second_class = :accountingSAccount) " +
            "OR (accounting_type = '转账' AND accounting_account_2_first_class = :accountingFAccount AND accounting_account_2_second_class = :accountingSAccount)  ")
    fun getAllIncomeAccountingDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    //获取指定二级账户下流出金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_account_first_class = :accountingFAccount AND accounting_account_second_class = :accountingSAccount)" +
            "OR (accounting_type = '转账' AND accounting_account_first_class = :accountingFAccount AND accounting_account_second_class = :accountingSAccount)  ")
    fun getAllExpenditureAccountingDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    //获取指定一级账户下流入金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_class_first_class = :accountingFAccount) ")
    fun getFirstClassIncomeClassDataIn(accountingFAccount:String) : Double

    //获取指定一级账户下流出金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_class_first_class = :accountingFAccount)" )
    fun getFirstClassExpenditureClassDataIn(accountingFAccount:String) : Double

    //获取指定二级成员下收入金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_member_first_class = :accountingFAccount AND accounting_member_second_class = :accountingSAccount) ")
    fun getAllIncomeMemberDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    //获取指定二级成员下支出金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_member_first_class = :accountingFAccount AND accounting_member_second_class = :accountingSAccount)" )
    fun getAllExpenditureMemberDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    //获取指定二级商家下收入金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_merchant_first_class = :accountingFAccount AND accounting_merchant_second_class = :accountingSAccount) ")
    fun getAllIncomeMerchantDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    //获取指定二级商家下支出金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_merchant_first_class = :accountingFAccount AND accounting_merchant_second_class = :accountingSAccount)" )
    fun getAllExpenditureMerchantDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    //获取指定二级项目下收入金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '收入' AND accounting_project_first_class = :accountingFAccount AND accounting_project_second_class = :accountingSAccount) ")
    fun getAllIncomeProjectDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    //获取指定二级项目下支出金额之和
    @Query("SELECT SUM(accounting_amount) FROM accounting_table " +
            "WHERE (accounting_type = '支出' AND accounting_project_first_class = :accountingFAccount AND accounting_project_second_class = :accountingSAccount)" )
    fun getAllExpenditureProjectDataIn(accountingFAccount:String,accountingSAccount:String) : Double

    //获取表中所有数据（以AccountRepository.TypeAmount的形式）(包含记账类型和金额)
    @Query("SELECT accounting_type,accounting_amount FROM accounting_table")
    fun readAccountTypeData() : List<AccountRepository.TypeAmount>

    //获取表中所有指定二级账户的数据（以AccountRepository.TypeAmount的形式）(包含记账类型和金额)
    @Query("SELECT accounting_type,accounting_amount FROM accounting_table WHERE accounting_account_second_class = :accountingAccount")
    fun readAccountDetailData(accountingAccount:String) : List<AccountRepository.TypeAmount>

    //获取所有在startDate~endDate内的数据（list）
    @Query("SELECT * FROM accounting_table WHERE accounting_time >=:startDate AND accounting_time <=:endDate")
    fun readAllDateFromAndTo(startDate:String,endDate:String) : List<Accounting>

    //获取所有在startDate~endDate内的满足Type的数据的总和()
    @Query("SELECT SUM(accounting_amount) FROM accounting_table WHERE accounting_time >=:startDate AND accounting_time <=:endDate AND accounting_type =:type")
    fun readAllDateFromToAndAbout(startDate:String,endDate:String,type:String) : Double

    //获取所有在startDate~endDate内的数据（livedata）
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
