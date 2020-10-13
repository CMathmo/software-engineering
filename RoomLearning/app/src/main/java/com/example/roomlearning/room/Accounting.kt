package com.example.roomlearning.room

import androidx.room.ColumnInfo
import androidx.room.DatabaseView
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounting_table")
data class Accounting(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "accounting_id")
    var accountingId: Int,
    @ColumnInfo(name = "accounting_member")
    var accountingMember: String,
    @ColumnInfo(name = "accounting_type")
    var accountingType: String,
    @ColumnInfo(name = "accounting_merchant")
    var accountingMerchant: String?,
    @ColumnInfo(name = "accounting_Time")
    var accountingTime: String?,
    @ColumnInfo(name = "accounting_number")
    var accountingNumber: String
)

