package com.wad.tBook.room;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class tBookDatabase_Impl extends tBookDatabase {
  private volatile AccountingDao _accountingDao;

  private volatile UserDao _userDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `accounting_table` (`accounting_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `accounting_amount` REAL NOT NULL, `accounting_first_class` TEXT NOT NULL, `accounting_second_class` TEXT, `accounting_project` TEXT, `accounting_member` TEXT, `accounting_type` TEXT NOT NULL, `accounting_account` TEXT NOT NULL, `accounting_merchant` TEXT, `accounting_Time` TEXT NOT NULL, `accounting_remark` TEXT, `accounting_imagine` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `user_table` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_name` TEXT NOT NULL, `user_password` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '1eb7940db2bd8737047f09dd635e4e56')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `accounting_table`");
        _db.execSQL("DROP TABLE IF EXISTS `user_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsAccountingTable = new HashMap<String, TableInfo.Column>(12);
        _columnsAccountingTable.put("accounting_id", new TableInfo.Column("accounting_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_amount", new TableInfo.Column("accounting_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_first_class", new TableInfo.Column("accounting_first_class", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_second_class", new TableInfo.Column("accounting_second_class", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_project", new TableInfo.Column("accounting_project", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_member", new TableInfo.Column("accounting_member", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_type", new TableInfo.Column("accounting_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_account", new TableInfo.Column("accounting_account", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_merchant", new TableInfo.Column("accounting_merchant", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_Time", new TableInfo.Column("accounting_Time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_remark", new TableInfo.Column("accounting_remark", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_imagine", new TableInfo.Column("accounting_imagine", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAccountingTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAccountingTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAccountingTable = new TableInfo("accounting_table", _columnsAccountingTable, _foreignKeysAccountingTable, _indicesAccountingTable);
        final TableInfo _existingAccountingTable = TableInfo.read(_db, "accounting_table");
        if (! _infoAccountingTable.equals(_existingAccountingTable)) {
          return new RoomOpenHelper.ValidationResult(false, "accounting_table(com.wad.tBook.room.Accounting).\n"
                  + " Expected:\n" + _infoAccountingTable + "\n"
                  + " Found:\n" + _existingAccountingTable);
        }
        final HashMap<String, TableInfo.Column> _columnsUserTable = new HashMap<String, TableInfo.Column>(3);
        _columnsUserTable.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserTable.put("user_name", new TableInfo.Column("user_name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserTable.put("user_password", new TableInfo.Column("user_password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserTable = new TableInfo("user_table", _columnsUserTable, _foreignKeysUserTable, _indicesUserTable);
        final TableInfo _existingUserTable = TableInfo.read(_db, "user_table");
        if (! _infoUserTable.equals(_existingUserTable)) {
          return new RoomOpenHelper.ValidationResult(false, "user_table(com.wad.tBook.room.User).\n"
                  + " Expected:\n" + _infoUserTable + "\n"
                  + " Found:\n" + _existingUserTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "1eb7940db2bd8737047f09dd635e4e56", "47678eb83e8e34111f8ffaed2e748a01");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "accounting_table","user_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `accounting_table`");
      _db.execSQL("DELETE FROM `user_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public AccountingDao actDao() {
    if (_accountingDao != null) {
      return _accountingDao;
    } else {
      synchronized(this) {
        if(_accountingDao == null) {
          _accountingDao = new AccountingDao_Impl(this);
        }
        return _accountingDao;
      }
    }
  }

  @Override
  public UserDao useDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }
}
