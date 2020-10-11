package com.wad.tBook

import androidx.room.*

@Database(entities = [Item::class], version = 1)
abstract class SQLDatabase : RoomDatabase() {
    abstract fun item(): ItemDao
}


@Dao
interface ItemDao {

    @Query("select * from Item")
    fun qeuryAll(): List<Item>

    @Insert
    fun insert(vararg item: Item): List<Long>

    @Delete
    fun delete(item: Item): Int

    @Query("Delete from Item")
    fun deleteAll() : Int

    @Update
    fun update(item: Item): Int

}


@Entity
data class Item(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo()
    var index: Long = 0,
    var money: Double,
    var first_class: String,
    var second_class: String? = null,
    var account: String,
    var date: String,
    var member: String? = null,
    var project: String? = null,
    var remark: String? = null,
    var img: String? = null
)