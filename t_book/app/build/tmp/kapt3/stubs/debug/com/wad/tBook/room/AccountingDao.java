package com.wad.tBook.room;

import java.lang.System;

@androidx.room.Dao()
@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\t\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u00020\u0001J\'\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u0012\u0010\u0005\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00070\u0006\"\u00020\u0007H\'\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0007H\'J\u0012\u0010\f\u001a\u00020\n2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\'J\u0014\u0010\u000f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00030\u0010H\'J\u0014\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\u00030\u0010H\'J\u0010\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0007H\'\u00a8\u0006\u0013"}, d2 = {"Lcom/wad/tBook/room/AccountingDao;", "", "addAccountingData", "", "", "accounting", "", "Lcom/wad/tBook/room/Accounting;", "([Lcom/wad/tBook/room/Accounting;)Ljava/util/List;", "delete", "", "uaccounting", "deleteAccountingData", "accountingId", "", "getAlphabetizedAccountingList", "Landroidx/lifecycle/LiveData;", "readAccountingData", "updateAccountingData", "app_debug"})
public abstract interface AccountingDao {
    
    @androidx.room.Delete()
    public abstract int delete(@org.jetbrains.annotations.NotNull()
    com.wad.tBook.room.Accounting uaccounting);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Insert()
    public abstract java.util.List<java.lang.Long> addAccountingData(@org.jetbrains.annotations.NotNull()
    com.wad.tBook.room.Accounting... accounting);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * from accounting_table ORDER BY accounting_account ASC")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.wad.tBook.room.Accounting>> getAlphabetizedAccountingList();
    
    @androidx.room.Update()
    public abstract int updateAccountingData(@org.jetbrains.annotations.NotNull()
    com.wad.tBook.room.Accounting accounting);
    
    @androidx.room.Query(value = "DELETE FROM accounting_table where accounting_id = :accountingId")
    public abstract int deleteAccountingData(@org.jetbrains.annotations.Nullable()
    java.lang.String accountingId);
    
    @org.jetbrains.annotations.NotNull()
    @androidx.room.Query(value = "SELECT * FROM accounting_table")
    public abstract androidx.lifecycle.LiveData<java.util.List<com.wad.tBook.room.Accounting>> readAccountingData();
}