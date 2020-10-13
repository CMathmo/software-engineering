package com.wad.tBook.room;

import java.lang.System;

@androidx.room.Entity(tableName = "accounting_table")
@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0006\n\u0000\n\u0002\u0010\u000e\n\u0002\b6\n\u0002\u0010\u000b\n\u0002\b\u0004\b\u0087\b\u0018\u00002\u00020\u0001B\u007f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u000b\u001a\u00020\u0007\u0012\u0006\u0010\f\u001a\u00020\u0007\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0007\u0012\u0006\u0010\u000e\u001a\u00020\u0007\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0007\u0012\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010\u0011J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0007H\u00c6\u0003J\u000b\u00102\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u00103\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u00104\u001a\u00020\u0005H\u00c6\u0003J\t\u00105\u001a\u00020\u0007H\u00c6\u0003J\u000b\u00106\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u00107\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u000b\u00108\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\t\u00109\u001a\u00020\u0007H\u00c6\u0003J\t\u0010:\u001a\u00020\u0007H\u00c6\u0003J\u000b\u0010;\u001a\u0004\u0018\u00010\u0007H\u00c6\u0003J\u008d\u0001\u0010<\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\n\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000b\u001a\u00020\u00072\b\b\u0002\u0010\f\u001a\u00020\u00072\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00072\b\b\u0002\u0010\u000e\u001a\u00020\u00072\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\u0010\u001a\u0004\u0018\u00010\u0007H\u00c6\u0001J\u0013\u0010=\u001a\u00020>2\b\u0010?\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010@\u001a\u00020\u0003H\u00d6\u0001J\t\u0010A\u001a\u00020\u0007H\u00d6\u0001R\u001e\u0010\f\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u0013\"\u0004\b\u001b\u0010\u0015R\u001e\u0010\u0002\u001a\u00020\u00038\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u001d\"\u0004\b\u001e\u0010\u001fR \u0010\u0010\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010\u0013\"\u0004\b!\u0010\u0015R \u0010\n\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0013\"\u0004\b#\u0010\u0015R \u0010\r\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010\u0013\"\u0004\b%\u0010\u0015R \u0010\t\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u0013\"\u0004\b\'\u0010\u0015R \u0010\u000f\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010\u0013\"\u0004\b)\u0010\u0015R \u0010\b\u001a\u0004\u0018\u00010\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u0013\"\u0004\b+\u0010\u0015R\u001e\u0010\u000e\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0013\"\u0004\b-\u0010\u0015R\u001e\u0010\u000b\u001a\u00020\u00078\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0013\"\u0004\b/\u0010\u0015\u00a8\u0006B"}, d2 = {"Lcom/wad/tBook/room/Accounting;", "", "accountingId", "", "accountingAmount", "", "accountingFirstClass", "", "accountingSecondClass", "accountingProject", "accountingMember", "accountingType", "accountingAcconut", "accountingMerchant", "accountingTime", "accountingRemark", "accountingImg", "(IDLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getAccountingAcconut", "()Ljava/lang/String;", "setAccountingAcconut", "(Ljava/lang/String;)V", "getAccountingAmount", "()D", "setAccountingAmount", "(D)V", "getAccountingFirstClass", "setAccountingFirstClass", "getAccountingId", "()I", "setAccountingId", "(I)V", "getAccountingImg", "setAccountingImg", "getAccountingMember", "setAccountingMember", "getAccountingMerchant", "setAccountingMerchant", "getAccountingProject", "setAccountingProject", "getAccountingRemark", "setAccountingRemark", "getAccountingSecondClass", "setAccountingSecondClass", "getAccountingTime", "setAccountingTime", "getAccountingType", "setAccountingType", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "app_debug"})
public final class Accounting {
    @androidx.room.ColumnInfo(name = "accounting_id")
    @androidx.room.PrimaryKey(autoGenerate = true)
    private int accountingId;
    @androidx.room.ColumnInfo(name = "accounting_amount")
    private double accountingAmount;
    @org.jetbrains.annotations.NotNull()
    @androidx.room.ColumnInfo(name = "accounting_first_class")
    private java.lang.String accountingFirstClass;
    @org.jetbrains.annotations.Nullable()
    @androidx.room.ColumnInfo(name = "accounting_second_class")
    private java.lang.String accountingSecondClass;
    @org.jetbrains.annotations.Nullable()
    @androidx.room.ColumnInfo(name = "accounting_project")
    private java.lang.String accountingProject;
    @org.jetbrains.annotations.Nullable()
    @androidx.room.ColumnInfo(name = "accounting_member")
    private java.lang.String accountingMember;
    @org.jetbrains.annotations.NotNull()
    @androidx.room.ColumnInfo(name = "accounting_type")
    private java.lang.String accountingType;
    @org.jetbrains.annotations.NotNull()
    @androidx.room.ColumnInfo(name = "accounting_account")
    private java.lang.String accountingAcconut;
    @org.jetbrains.annotations.Nullable()
    @androidx.room.ColumnInfo(name = "accounting_merchant")
    private java.lang.String accountingMerchant;
    @org.jetbrains.annotations.NotNull()
    @androidx.room.ColumnInfo(name = "accounting_Time")
    private java.lang.String accountingTime;
    @org.jetbrains.annotations.Nullable()
    @androidx.room.ColumnInfo(name = "accounting_remark")
    private java.lang.String accountingRemark;
    @org.jetbrains.annotations.Nullable()
    @androidx.room.ColumnInfo(name = "accounting_imagine")
    private java.lang.String accountingImg;
    
    public final int getAccountingId() {
        return 0;
    }
    
    public final void setAccountingId(int p0) {
    }
    
    public final double getAccountingAmount() {
        return 0.0;
    }
    
    public final void setAccountingAmount(double p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAccountingFirstClass() {
        return null;
    }
    
    public final void setAccountingFirstClass(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAccountingSecondClass() {
        return null;
    }
    
    public final void setAccountingSecondClass(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAccountingProject() {
        return null;
    }
    
    public final void setAccountingProject(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAccountingMember() {
        return null;
    }
    
    public final void setAccountingMember(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAccountingType() {
        return null;
    }
    
    public final void setAccountingType(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAccountingAcconut() {
        return null;
    }
    
    public final void setAccountingAcconut(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAccountingMerchant() {
        return null;
    }
    
    public final void setAccountingMerchant(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getAccountingTime() {
        return null;
    }
    
    public final void setAccountingTime(@org.jetbrains.annotations.NotNull()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAccountingRemark() {
        return null;
    }
    
    public final void setAccountingRemark(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getAccountingImg() {
        return null;
    }
    
    public final void setAccountingImg(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public Accounting(int accountingId, double accountingAmount, @org.jetbrains.annotations.NotNull()
    java.lang.String accountingFirstClass, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingSecondClass, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingProject, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingMember, @org.jetbrains.annotations.NotNull()
    java.lang.String accountingType, @org.jetbrains.annotations.NotNull()
    java.lang.String accountingAcconut, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingMerchant, @org.jetbrains.annotations.NotNull()
    java.lang.String accountingTime, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingRemark, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingImg) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final double component2() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component3() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component4() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component7() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component8() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component9() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component10() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.wad.tBook.room.Accounting copy(int accountingId, double accountingAmount, @org.jetbrains.annotations.NotNull()
    java.lang.String accountingFirstClass, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingSecondClass, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingProject, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingMember, @org.jetbrains.annotations.NotNull()
    java.lang.String accountingType, @org.jetbrains.annotations.NotNull()
    java.lang.String accountingAcconut, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingMerchant, @org.jetbrains.annotations.NotNull()
    java.lang.String accountingTime, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingRemark, @org.jetbrains.annotations.Nullable()
    java.lang.String accountingImg) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
        return false;
    }
}