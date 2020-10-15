package com.wad.tBook.room

import androidx.lifecycle.LiveData
import androidx.room.*

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