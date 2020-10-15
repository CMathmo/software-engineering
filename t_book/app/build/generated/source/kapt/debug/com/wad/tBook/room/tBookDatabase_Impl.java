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

  private volatile PropertyDao _propertyDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(3) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `accounting_table` (`accounting_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `accounting_type` TEXT NOT NULL, `accounting_amount` REAL NOT NULL, `accounting_Time` TEXT NOT NULL, `accounting_remark` TEXT, `accounting_imagine` TEXT, `accounting_classfirst_class` TEXT NOT NULL, `accounting_classsecond_class` TEXT NOT NULL, `accounting_accountfirst_class` TEXT NOT NULL, `accounting_accountsecond_class` TEXT NOT NULL, `accounting_memberfirst_class` TEXT, `accounting_membersecond_class` TEXT, `accounting_projectfirst_class` TEXT, `accounting_projectsecond_class` TEXT, `accounting_merchantfirst_class` TEXT, `accounting_merchantsecond_class` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `user_table` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_name` TEXT NOT NULL, `user_password` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `property_table` (`property_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `property_type` TEXT NOT NULL, `property_item` TEXT NOT NULL, `property_first_class` TEXT NOT NULL, `property_second_class` TEXT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd4a3e4fd547206f5264ca576a5c1aae7')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `accounting_table`");
        _db.execSQL("DROP TABLE IF EXISTS `user_table`");
        _db.execSQL("DROP TABLE IF EXISTS `property_table`");
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
        final HashMap<String, TableInfo.Column> _columnsAccountingTable = new HashMap<String, TableInfo.Column>(16);
        _columnsAccountingTable.put("accounting_id", new TableInfo.Column("accounting_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_type", new TableInfo.Column("accounting_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_amount", new TableInfo.Column("accounting_amount", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_Time", new TableInfo.Column("accounting_Time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_remark", new TableInfo.Column("accounting_remark", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_imagine", new TableInfo.Column("accounting_imagine", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_classfirst_class", new TableInfo.Column("accounting_classfirst_class", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_classsecond_class", new TableInfo.Column("accounting_classsecond_class", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_accountfirst_class", new TableInfo.Column("accounting_accountfirst_class", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_accountsecond_class", new TableInfo.Column("accounting_accountsecond_class", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_memberfirst_class", new TableInfo.Column("accounting_memberfirst_class", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_membersecond_class", new TableInfo.Column("accounting_membersecond_class", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_projectfirst_class", new TableInfo.Column("accounting_projectfirst_class", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_projectsecond_class", new TableInfo.Column("accounting_projectsecond_class", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_merchantfirst_class", new TableInfo.Column("accounting_merchantfirst_class", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAccountingTable.put("accounting_merchantsecond_class", new TableInfo.Column("accounting_merchantsecond_class", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
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
        final HashMap<String, TableInfo.Column> _columnsPropertyTable = new HashMap<String, TableInfo.Column>(5);
        _columnsPropertyTable.put("property_id", new TableInfo.Column("property_id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPropertyTable.put("property_type", new TableInfo.Column("property_type", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPropertyTable.put("property_item", new TableInfo.Column("property_item", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPropertyTable.put("property_first_class", new TableInfo.Column("property_first_class", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsPropertyTable.put("property_second_class", new TableInfo.Column("property_second_class", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPropertyTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPropertyTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPropertyTable = new TableInfo("property_table", _columnsPropertyTable, _foreignKeysPropertyTable, _indicesPropertyTable);
        final TableInfo _existingPropertyTable = TableInfo.read(_db, "property_table");
        if (! _infoPropertyTable.equals(_existingPropertyTable)) {
          return new RoomOpenHelper.ValidationResult(false, "property_table(com.wad.tBook.room.Property).\n"
                  + " Expected:\n" + _infoPropertyTable + "\n"
                  + " Found:\n" + _existingPropertyTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "d4a3e4fd547206f5264ca576a5c1aae7", "172d111cf847dfff5f6142ecbcb46923");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "accounting_table","user_table","property_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `accounting_table`");
      _db.execSQL("DELETE FROM `user_table`");
      _db.execSQL("DELETE FROM `property_table`");
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

  @Override
  public PropertyDao proDao() {
    if (_propertyDao != null) {
      return _propertyDao;
    } else {
      synchronized(this) {
        if(_propertyDao == null) {
          _propertyDao = new PropertyDao_Impl(this);
        }
        return _propertyDao;
      }
    }
  }
}
