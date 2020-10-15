package com.wad.tBook.room;

import java.lang.System;

@androidx.room.Database(entities = {com.wad.tBook.room.Accounting.class, com.wad.tBook.room.User.class, com.wad.tBook.room.Property.class}, version = 3)
@kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H&J\b\u0010\u0005\u001a\u00020\u0006H&J\b\u0010\u0007\u001a\u00020\bH&\u00a8\u0006\n"}, d2 = {"Lcom/wad/tBook/room/tBookDatabase;", "Landroidx/room/RoomDatabase;", "()V", "actDao", "Lcom/wad/tBook/room/AccountingDao;", "proDao", "Lcom/wad/tBook/room/PropertyDao;", "useDao", "Lcom/wad/tBook/room/UserDao;", "Companion", "app_debug"})
public abstract class tBookDatabase extends androidx.room.RoomDatabase {
    private static volatile com.wad.tBook.room.tBookDatabase instance;
    public static final com.wad.tBook.room.tBookDatabase.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wad.tBook.room.AccountingDao actDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wad.tBook.room.UserDao useDao();
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.wad.tBook.room.PropertyDao proDao();
    
    public tBookDatabase() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 4, 0}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\b"}, d2 = {"Lcom/wad/tBook/room/tBookDatabase$Companion;", "", "()V", "instance", "Lcom/wad/tBook/room/tBookDatabase;", "getDBInstace", "application", "Landroid/app/Application;", "app_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.NotNull()
        public final com.wad.tBook.room.tBookDatabase getDBInstace(@org.jetbrains.annotations.NotNull()
        android.app.Application application) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}