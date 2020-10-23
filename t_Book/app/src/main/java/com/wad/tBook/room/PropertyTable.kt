package com.wad.tBook.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PropertyDao {

    @Delete
    fun delete(property:Property): Int

    @Insert
    fun addPropertyData(vararg property:Property): List<Long>

    @Update
    fun updatePropertyData(property:Property): Int

    @Query("DELETE FROM property_table WHERE property_id = :PropertyId")
    fun deletePropertyData(PropertyId: String?): Int

    @Query("DELETE FROM property_table")
    fun deleteAllPropertyData(): Int

    @Query("SELECT * FROM property_table")
    fun readPropertyData() : LiveData<List<Property>>

    @Query("SELECT * FROM property_table")
    fun getAllPropertyData() : List<Property>

    @Query("SELECT DISTINCT property_first_class FROM property_table WHERE property_type = :PropertyType AND property_item =:PropertyItem")
    fun getFirstClassFrom(PropertyType:String, PropertyItem:String): List<String>

    @Query("SELECT DISTINCT property_second_class FROM property_table WHERE property_type = :PropertyType AND property_item =:PropertyItem AND property_first_class =:PropertyFirstClass")
    fun getSecondClassFrom(PropertyType:String, PropertyItem:String, PropertyFirstClass:String): List<String>
}


@Entity(tableName = "property_table")
data class Property(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "property_id")
    var propertyId: Int = 0,//id，自动生成
    @ColumnInfo(name = "property_type")
    var propertyType: String,//类别（收支转账）
    @ColumnInfo(name = "property_item")
    var propertyItem: String,//项目
    @ColumnInfo(name = "property_first_class")
    var propertyFirstClass: String,//一级分类
    @ColumnInfo(name = "property_second_class")
    var propertySecondClass: String//二级分类
)