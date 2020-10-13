package com.wad.tBook.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.*

@Database(entities = [Accounting::class,User::class], version = 1)
abstract class tBookDatabase : RoomDatabase() {
    //获取接口
    abstract fun actDao() : AccountingDao
    abstract fun useDao() : UserDao

    //生成实例
    companion object {
        @Volatile
        private var instance: tBookDatabase? = null
        fun getDBInstace(application: Application): tBookDatabase {
            if (instance == null) {
                synchronized(tBookDatabase::class) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            application,
                            tBookDatabase::class.java,
                            "tBook.db"
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return instance!!
        }
    }
}

@Dao
interface AccountingDao {

    @Delete
    fun delete(uaccounting:Accounting): Int

    @Insert
    fun addAccountingData(vararg accounting:Accounting): List<Long>

    @Update
    fun updateAccountingData(accounting:Accounting): Int

    @Query("DELETE FROM accounting_table where accounting_id = :accountingId")
    fun deleteAccountingData(accountingId: String?): Int

    @Query("SELECT * FROM accounting_table")
    fun readAccountingData() : LiveData<List<Accounting>>
}

@Dao
interface UserDao {

    @Delete
    fun delete(user:User): Int

    @Insert
    fun addUserData(vararg user:User): List<Long>

    @Update
    fun updateUseerData(user:User): Int

    @Query("DELETE FROM user_table where user_id = :userId")
    fun deleteUserData(userId: String?): Int

    @Query("SELECT * FROM user_table")
    fun readUserData() : LiveData<List<User>>
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

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    var userId: Int = 0,//id，自动生成
    @ColumnInfo(name = "user_name")
    var userName: String,//用户名
    @ColumnInfo(name = "user_password")
    var userPassword: String//密码
)