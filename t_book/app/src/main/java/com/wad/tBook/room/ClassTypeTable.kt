package com.wad.tBook.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ClassTypeDao {

    @Delete
    fun delete(class_type:ClassType): Int

    @Insert
    fun addClassTypeData(vararg class_type:ClassType): List<Long>

    @Update
    fun updateClassTypeData(class_type:ClassType): Int

    @Query("DELETE FROM class_type_table where class_type_id = :ClassTypeId")
    fun deleteClassTypeData(ClassTypeId: String?): Int

    @Query("SELECT * FROM class_type_table")
    fun readClassTypeData() : LiveData<List<ClassType>>
}


@Entity(tableName = "class_type_table")
data class ClassType(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "class_type_id")
    var classTypeId: Int = 0,//id，自动生成
    @ColumnInfo(name = "class_type_name")
    var classTypeName: String,//用户名
    @ColumnInfo(name = "class_type_father")
    var classTypePassword: String//密码
)