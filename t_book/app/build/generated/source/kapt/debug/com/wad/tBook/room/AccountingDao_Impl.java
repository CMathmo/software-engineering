package com.wad.tBook.room;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AccountingDao_Impl implements AccountingDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Accounting> __insertionAdapterOfAccounting;

  private final EntityDeletionOrUpdateAdapter<Accounting> __deletionAdapterOfAccounting;

  private final EntityDeletionOrUpdateAdapter<Accounting> __updateAdapterOfAccounting;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAccountingData;

  public AccountingDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAccounting = new EntityInsertionAdapter<Accounting>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `accounting_table` (`accounting_id`,`accounting_amount`,`accounting_first_class`,`accounting_second_class`,`accounting_project`,`accounting_member`,`accounting_type`,`accounting_account`,`accounting_merchant`,`accounting_Time`,`accounting_remark`,`accounting_imagine`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Accounting value) {
        stmt.bindLong(1, value.getAccountingId());
        stmt.bindDouble(2, value.getAccountingAmount());
        if (value.getAccountingFirstClass() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAccountingFirstClass());
        }
        if (value.getAccountingSecondClass() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAccountingSecondClass());
        }
        if (value.getAccountingProject() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAccountingProject());
        }
        if (value.getAccountingMember() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAccountingMember());
        }
        if (value.getAccountingType() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAccountingType());
        }
        if (value.getAccountingAcconut() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAccountingAcconut());
        }
        if (value.getAccountingMerchant() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getAccountingMerchant());
        }
        if (value.getAccountingTime() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getAccountingTime());
        }
        if (value.getAccountingRemark() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getAccountingRemark());
        }
        if (value.getAccountingImg() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getAccountingImg());
        }
      }
    };
    this.__deletionAdapterOfAccounting = new EntityDeletionOrUpdateAdapter<Accounting>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `accounting_table` WHERE `accounting_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Accounting value) {
        stmt.bindLong(1, value.getAccountingId());
      }
    };
    this.__updateAdapterOfAccounting = new EntityDeletionOrUpdateAdapter<Accounting>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `accounting_table` SET `accounting_id` = ?,`accounting_amount` = ?,`accounting_first_class` = ?,`accounting_second_class` = ?,`accounting_project` = ?,`accounting_member` = ?,`accounting_type` = ?,`accounting_account` = ?,`accounting_merchant` = ?,`accounting_Time` = ?,`accounting_remark` = ?,`accounting_imagine` = ? WHERE `accounting_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Accounting value) {
        stmt.bindLong(1, value.getAccountingId());
        stmt.bindDouble(2, value.getAccountingAmount());
        if (value.getAccountingFirstClass() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getAccountingFirstClass());
        }
        if (value.getAccountingSecondClass() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAccountingSecondClass());
        }
        if (value.getAccountingProject() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAccountingProject());
        }
        if (value.getAccountingMember() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAccountingMember());
        }
        if (value.getAccountingType() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getAccountingType());
        }
        if (value.getAccountingAcconut() == null) {
          stmt.bindNull(8);
        } else {
          stmt.bindString(8, value.getAccountingAcconut());
        }
        if (value.getAccountingMerchant() == null) {
          stmt.bindNull(9);
        } else {
          stmt.bindString(9, value.getAccountingMerchant());
        }
        if (value.getAccountingTime() == null) {
          stmt.bindNull(10);
        } else {
          stmt.bindString(10, value.getAccountingTime());
        }
        if (value.getAccountingRemark() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getAccountingRemark());
        }
        if (value.getAccountingImg() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getAccountingImg());
        }
        stmt.bindLong(13, value.getAccountingId());
      }
    };
    this.__preparedStmtOfDeleteAccountingData = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM accounting_table where accounting_id = ?";
        return _query;
      }
    };
  }

  @Override
  public List<Long> addAccountingData(final Accounting... accounting) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      List<Long> _result = __insertionAdapterOfAccounting.insertAndReturnIdsList(accounting);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int delete(final Accounting uaccounting) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__deletionAdapterOfAccounting.handle(uaccounting);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updateAccountingData(final Accounting accounting) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfAccounting.handle(accounting);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deleteAccountingData(final String accountingId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAccountingData.acquire();
    int _argIndex = 1;
    if (accountingId == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, accountingId);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteAccountingData.release(_stmt);
    }
  }

  @Override
  public LiveData<List<Accounting>> getAlphabetizedAccountingList() {
    final String _sql = "SELECT * from accounting_table ORDER BY accounting_account ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"accounting_table"}, false, new Callable<List<Accounting>>() {
      @Override
      public List<Accounting> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAccountingId = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_id");
          final int _cursorIndexOfAccountingAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_amount");
          final int _cursorIndexOfAccountingFirstClass = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_first_class");
          final int _cursorIndexOfAccountingSecondClass = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_second_class");
          final int _cursorIndexOfAccountingProject = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_project");
          final int _cursorIndexOfAccountingMember = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_member");
          final int _cursorIndexOfAccountingType = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_type");
          final int _cursorIndexOfAccountingAcconut = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_account");
          final int _cursorIndexOfAccountingMerchant = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_merchant");
          final int _cursorIndexOfAccountingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_Time");
          final int _cursorIndexOfAccountingRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_remark");
          final int _cursorIndexOfAccountingImg = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_imagine");
          final List<Accounting> _result = new ArrayList<Accounting>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Accounting _item;
            final int _tmpAccountingId;
            _tmpAccountingId = _cursor.getInt(_cursorIndexOfAccountingId);
            final double _tmpAccountingAmount;
            _tmpAccountingAmount = _cursor.getDouble(_cursorIndexOfAccountingAmount);
            final String _tmpAccountingFirstClass;
            _tmpAccountingFirstClass = _cursor.getString(_cursorIndexOfAccountingFirstClass);
            final String _tmpAccountingSecondClass;
            _tmpAccountingSecondClass = _cursor.getString(_cursorIndexOfAccountingSecondClass);
            final String _tmpAccountingProject;
            _tmpAccountingProject = _cursor.getString(_cursorIndexOfAccountingProject);
            final String _tmpAccountingMember;
            _tmpAccountingMember = _cursor.getString(_cursorIndexOfAccountingMember);
            final String _tmpAccountingType;
            _tmpAccountingType = _cursor.getString(_cursorIndexOfAccountingType);
            final String _tmpAccountingAcconut;
            _tmpAccountingAcconut = _cursor.getString(_cursorIndexOfAccountingAcconut);
            final String _tmpAccountingMerchant;
            _tmpAccountingMerchant = _cursor.getString(_cursorIndexOfAccountingMerchant);
            final String _tmpAccountingTime;
            _tmpAccountingTime = _cursor.getString(_cursorIndexOfAccountingTime);
            final String _tmpAccountingRemark;
            _tmpAccountingRemark = _cursor.getString(_cursorIndexOfAccountingRemark);
            final String _tmpAccountingImg;
            _tmpAccountingImg = _cursor.getString(_cursorIndexOfAccountingImg);
            _item = new Accounting(_tmpAccountingId,_tmpAccountingAmount,_tmpAccountingFirstClass,_tmpAccountingSecondClass,_tmpAccountingProject,_tmpAccountingMember,_tmpAccountingType,_tmpAccountingAcconut,_tmpAccountingMerchant,_tmpAccountingTime,_tmpAccountingRemark,_tmpAccountingImg);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<Accounting>> readAccountingData() {
    final String _sql = "SELECT * FROM accounting_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"accounting_table"}, false, new Callable<List<Accounting>>() {
      @Override
      public List<Accounting> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAccountingId = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_id");
          final int _cursorIndexOfAccountingAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_amount");
          final int _cursorIndexOfAccountingFirstClass = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_first_class");
          final int _cursorIndexOfAccountingSecondClass = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_second_class");
          final int _cursorIndexOfAccountingProject = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_project");
          final int _cursorIndexOfAccountingMember = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_member");
          final int _cursorIndexOfAccountingType = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_type");
          final int _cursorIndexOfAccountingAcconut = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_account");
          final int _cursorIndexOfAccountingMerchant = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_merchant");
          final int _cursorIndexOfAccountingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_Time");
          final int _cursorIndexOfAccountingRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_remark");
          final int _cursorIndexOfAccountingImg = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_imagine");
          final List<Accounting> _result = new ArrayList<Accounting>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Accounting _item;
            final int _tmpAccountingId;
            _tmpAccountingId = _cursor.getInt(_cursorIndexOfAccountingId);
            final double _tmpAccountingAmount;
            _tmpAccountingAmount = _cursor.getDouble(_cursorIndexOfAccountingAmount);
            final String _tmpAccountingFirstClass;
            _tmpAccountingFirstClass = _cursor.getString(_cursorIndexOfAccountingFirstClass);
            final String _tmpAccountingSecondClass;
            _tmpAccountingSecondClass = _cursor.getString(_cursorIndexOfAccountingSecondClass);
            final String _tmpAccountingProject;
            _tmpAccountingProject = _cursor.getString(_cursorIndexOfAccountingProject);
            final String _tmpAccountingMember;
            _tmpAccountingMember = _cursor.getString(_cursorIndexOfAccountingMember);
            final String _tmpAccountingType;
            _tmpAccountingType = _cursor.getString(_cursorIndexOfAccountingType);
            final String _tmpAccountingAcconut;
            _tmpAccountingAcconut = _cursor.getString(_cursorIndexOfAccountingAcconut);
            final String _tmpAccountingMerchant;
            _tmpAccountingMerchant = _cursor.getString(_cursorIndexOfAccountingMerchant);
            final String _tmpAccountingTime;
            _tmpAccountingTime = _cursor.getString(_cursorIndexOfAccountingTime);
            final String _tmpAccountingRemark;
            _tmpAccountingRemark = _cursor.getString(_cursorIndexOfAccountingRemark);
            final String _tmpAccountingImg;
            _tmpAccountingImg = _cursor.getString(_cursorIndexOfAccountingImg);
            _item = new Accounting(_tmpAccountingId,_tmpAccountingAmount,_tmpAccountingFirstClass,_tmpAccountingSecondClass,_tmpAccountingProject,_tmpAccountingMember,_tmpAccountingType,_tmpAccountingAcconut,_tmpAccountingMerchant,_tmpAccountingTime,_tmpAccountingRemark,_tmpAccountingImg);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
