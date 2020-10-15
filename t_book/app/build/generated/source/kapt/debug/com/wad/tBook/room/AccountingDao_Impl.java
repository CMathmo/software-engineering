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
        return "INSERT OR ABORT INTO `accounting_table` (`accounting_id`,`accounting_type`,`accounting_amount`,`accounting_Time`,`accounting_remark`,`accounting_imagine`,`accounting_classfirst_class`,`accounting_classsecond_class`,`accounting_accountfirst_class`,`accounting_accountsecond_class`,`accounting_memberfirst_class`,`accounting_membersecond_class`,`accounting_projectfirst_class`,`accounting_projectsecond_class`,`accounting_merchantfirst_class`,`accounting_merchantsecond_class`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Accounting value) {
        stmt.bindLong(1, value.getAccountingId());
        if (value.getAccountingType() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAccountingType());
        }
        stmt.bindDouble(3, value.getAccountingAmount());
        if (value.getAccountingTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAccountingTime());
        }
        if (value.getAccountingRemark() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAccountingRemark());
        }
        if (value.getAccountingImg() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAccountingImg());
        }
        final multilevel_classification _tmpAccountingClass = value.getAccountingClass();
        if(_tmpAccountingClass != null) {
          if (_tmpAccountingClass.getFirst_class() == null) {
            stmt.bindNull(7);
          } else {
            stmt.bindString(7, _tmpAccountingClass.getFirst_class());
          }
          if (_tmpAccountingClass.getSecond_class() == null) {
            stmt.bindNull(8);
          } else {
            stmt.bindString(8, _tmpAccountingClass.getSecond_class());
          }
        } else {
          stmt.bindNull(7);
          stmt.bindNull(8);
        }
        final multilevel_classification _tmpAccountingAcconut = value.getAccountingAcconut();
        if(_tmpAccountingAcconut != null) {
          if (_tmpAccountingAcconut.getFirst_class() == null) {
            stmt.bindNull(9);
          } else {
            stmt.bindString(9, _tmpAccountingAcconut.getFirst_class());
          }
          if (_tmpAccountingAcconut.getSecond_class() == null) {
            stmt.bindNull(10);
          } else {
            stmt.bindString(10, _tmpAccountingAcconut.getSecond_class());
          }
        } else {
          stmt.bindNull(9);
          stmt.bindNull(10);
        }
        final multilevel_classification _tmpAccountingMember = value.getAccountingMember();
        if(_tmpAccountingMember != null) {
          if (_tmpAccountingMember.getFirst_class() == null) {
            stmt.bindNull(11);
          } else {
            stmt.bindString(11, _tmpAccountingMember.getFirst_class());
          }
          if (_tmpAccountingMember.getSecond_class() == null) {
            stmt.bindNull(12);
          } else {
            stmt.bindString(12, _tmpAccountingMember.getSecond_class());
          }
        } else {
          stmt.bindNull(11);
          stmt.bindNull(12);
        }
        final multilevel_classification _tmpAccountingProject = value.getAccountingProject();
        if(_tmpAccountingProject != null) {
          if (_tmpAccountingProject.getFirst_class() == null) {
            stmt.bindNull(13);
          } else {
            stmt.bindString(13, _tmpAccountingProject.getFirst_class());
          }
          if (_tmpAccountingProject.getSecond_class() == null) {
            stmt.bindNull(14);
          } else {
            stmt.bindString(14, _tmpAccountingProject.getSecond_class());
          }
        } else {
          stmt.bindNull(13);
          stmt.bindNull(14);
        }
        final multilevel_classification _tmpAccountingMerchant = value.getAccountingMerchant();
        if(_tmpAccountingMerchant != null) {
          if (_tmpAccountingMerchant.getFirst_class() == null) {
            stmt.bindNull(15);
          } else {
            stmt.bindString(15, _tmpAccountingMerchant.getFirst_class());
          }
          if (_tmpAccountingMerchant.getSecond_class() == null) {
            stmt.bindNull(16);
          } else {
            stmt.bindString(16, _tmpAccountingMerchant.getSecond_class());
          }
        } else {
          stmt.bindNull(15);
          stmt.bindNull(16);
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
        return "UPDATE OR ABORT `accounting_table` SET `accounting_id` = ?,`accounting_type` = ?,`accounting_amount` = ?,`accounting_Time` = ?,`accounting_remark` = ?,`accounting_imagine` = ?,`accounting_classfirst_class` = ?,`accounting_classsecond_class` = ?,`accounting_accountfirst_class` = ?,`accounting_accountsecond_class` = ?,`accounting_memberfirst_class` = ?,`accounting_membersecond_class` = ?,`accounting_projectfirst_class` = ?,`accounting_projectsecond_class` = ?,`accounting_merchantfirst_class` = ?,`accounting_merchantsecond_class` = ? WHERE `accounting_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Accounting value) {
        stmt.bindLong(1, value.getAccountingId());
        if (value.getAccountingType() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getAccountingType());
        }
        stmt.bindDouble(3, value.getAccountingAmount());
        if (value.getAccountingTime() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getAccountingTime());
        }
        if (value.getAccountingRemark() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getAccountingRemark());
        }
        if (value.getAccountingImg() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAccountingImg());
        }
        final multilevel_classification _tmpAccountingClass = value.getAccountingClass();
        if(_tmpAccountingClass != null) {
          if (_tmpAccountingClass.getFirst_class() == null) {
            stmt.bindNull(7);
          } else {
            stmt.bindString(7, _tmpAccountingClass.getFirst_class());
          }
          if (_tmpAccountingClass.getSecond_class() == null) {
            stmt.bindNull(8);
          } else {
            stmt.bindString(8, _tmpAccountingClass.getSecond_class());
          }
        } else {
          stmt.bindNull(7);
          stmt.bindNull(8);
        }
        final multilevel_classification _tmpAccountingAcconut = value.getAccountingAcconut();
        if(_tmpAccountingAcconut != null) {
          if (_tmpAccountingAcconut.getFirst_class() == null) {
            stmt.bindNull(9);
          } else {
            stmt.bindString(9, _tmpAccountingAcconut.getFirst_class());
          }
          if (_tmpAccountingAcconut.getSecond_class() == null) {
            stmt.bindNull(10);
          } else {
            stmt.bindString(10, _tmpAccountingAcconut.getSecond_class());
          }
        } else {
          stmt.bindNull(9);
          stmt.bindNull(10);
        }
        final multilevel_classification _tmpAccountingMember = value.getAccountingMember();
        if(_tmpAccountingMember != null) {
          if (_tmpAccountingMember.getFirst_class() == null) {
            stmt.bindNull(11);
          } else {
            stmt.bindString(11, _tmpAccountingMember.getFirst_class());
          }
          if (_tmpAccountingMember.getSecond_class() == null) {
            stmt.bindNull(12);
          } else {
            stmt.bindString(12, _tmpAccountingMember.getSecond_class());
          }
        } else {
          stmt.bindNull(11);
          stmt.bindNull(12);
        }
        final multilevel_classification _tmpAccountingProject = value.getAccountingProject();
        if(_tmpAccountingProject != null) {
          if (_tmpAccountingProject.getFirst_class() == null) {
            stmt.bindNull(13);
          } else {
            stmt.bindString(13, _tmpAccountingProject.getFirst_class());
          }
          if (_tmpAccountingProject.getSecond_class() == null) {
            stmt.bindNull(14);
          } else {
            stmt.bindString(14, _tmpAccountingProject.getSecond_class());
          }
        } else {
          stmt.bindNull(13);
          stmt.bindNull(14);
        }
        final multilevel_classification _tmpAccountingMerchant = value.getAccountingMerchant();
        if(_tmpAccountingMerchant != null) {
          if (_tmpAccountingMerchant.getFirst_class() == null) {
            stmt.bindNull(15);
          } else {
            stmt.bindString(15, _tmpAccountingMerchant.getFirst_class());
          }
          if (_tmpAccountingMerchant.getSecond_class() == null) {
            stmt.bindNull(16);
          } else {
            stmt.bindString(16, _tmpAccountingMerchant.getSecond_class());
          }
        } else {
          stmt.bindNull(15);
          stmt.bindNull(16);
        }
        stmt.bindLong(17, value.getAccountingId());
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
  public LiveData<List<Accounting>> readAccountingData() {
    final String _sql = "SELECT * FROM accounting_table";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"accounting_table"}, false, new Callable<List<Accounting>>() {
      @Override
      public List<Accounting> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfAccountingId = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_id");
          final int _cursorIndexOfAccountingType = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_type");
          final int _cursorIndexOfAccountingAmount = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_amount");
          final int _cursorIndexOfAccountingTime = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_Time");
          final int _cursorIndexOfAccountingRemark = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_remark");
          final int _cursorIndexOfAccountingImg = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_imagine");
          final int _cursorIndexOfFirstClass = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_classfirst_class");
          final int _cursorIndexOfSecondClass = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_classsecond_class");
          final int _cursorIndexOfFirstClass_1 = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_accountfirst_class");
          final int _cursorIndexOfSecondClass_1 = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_accountsecond_class");
          final int _cursorIndexOfFirstClass_2 = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_memberfirst_class");
          final int _cursorIndexOfSecondClass_2 = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_membersecond_class");
          final int _cursorIndexOfFirstClass_3 = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_projectfirst_class");
          final int _cursorIndexOfSecondClass_3 = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_projectsecond_class");
          final int _cursorIndexOfFirstClass_4 = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_merchantfirst_class");
          final int _cursorIndexOfSecondClass_4 = CursorUtil.getColumnIndexOrThrow(_cursor, "accounting_merchantsecond_class");
          final List<Accounting> _result = new ArrayList<Accounting>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Accounting _item;
            final int _tmpAccountingId;
            _tmpAccountingId = _cursor.getInt(_cursorIndexOfAccountingId);
            final String _tmpAccountingType;
            _tmpAccountingType = _cursor.getString(_cursorIndexOfAccountingType);
            final double _tmpAccountingAmount;
            _tmpAccountingAmount = _cursor.getDouble(_cursorIndexOfAccountingAmount);
            final String _tmpAccountingTime;
            _tmpAccountingTime = _cursor.getString(_cursorIndexOfAccountingTime);
            final String _tmpAccountingRemark;
            _tmpAccountingRemark = _cursor.getString(_cursorIndexOfAccountingRemark);
            final String _tmpAccountingImg;
            _tmpAccountingImg = _cursor.getString(_cursorIndexOfAccountingImg);
            final multilevel_classification _tmpAccountingClass;
            if (! (_cursor.isNull(_cursorIndexOfFirstClass) && _cursor.isNull(_cursorIndexOfSecondClass))) {
              final String _tmpFirst_class;
              _tmpFirst_class = _cursor.getString(_cursorIndexOfFirstClass);
              final String _tmpSecond_class;
              _tmpSecond_class = _cursor.getString(_cursorIndexOfSecondClass);
              _tmpAccountingClass = new multilevel_classification(_tmpFirst_class,_tmpSecond_class);
            }  else  {
              _tmpAccountingClass = null;
            }
            final multilevel_classification _tmpAccountingAcconut;
            if (! (_cursor.isNull(_cursorIndexOfFirstClass_1) && _cursor.isNull(_cursorIndexOfSecondClass_1))) {
              final String _tmpFirst_class_1;
              _tmpFirst_class_1 = _cursor.getString(_cursorIndexOfFirstClass_1);
              final String _tmpSecond_class_1;
              _tmpSecond_class_1 = _cursor.getString(_cursorIndexOfSecondClass_1);
              _tmpAccountingAcconut = new multilevel_classification(_tmpFirst_class_1,_tmpSecond_class_1);
            }  else  {
              _tmpAccountingAcconut = null;
            }
            final multilevel_classification _tmpAccountingMember;
            if (! (_cursor.isNull(_cursorIndexOfFirstClass_2) && _cursor.isNull(_cursorIndexOfSecondClass_2))) {
              final String _tmpFirst_class_2;
              _tmpFirst_class_2 = _cursor.getString(_cursorIndexOfFirstClass_2);
              final String _tmpSecond_class_2;
              _tmpSecond_class_2 = _cursor.getString(_cursorIndexOfSecondClass_2);
              _tmpAccountingMember = new multilevel_classification(_tmpFirst_class_2,_tmpSecond_class_2);
            }  else  {
              _tmpAccountingMember = null;
            }
            final multilevel_classification _tmpAccountingProject;
            if (! (_cursor.isNull(_cursorIndexOfFirstClass_3) && _cursor.isNull(_cursorIndexOfSecondClass_3))) {
              final String _tmpFirst_class_3;
              _tmpFirst_class_3 = _cursor.getString(_cursorIndexOfFirstClass_3);
              final String _tmpSecond_class_3;
              _tmpSecond_class_3 = _cursor.getString(_cursorIndexOfSecondClass_3);
              _tmpAccountingProject = new multilevel_classification(_tmpFirst_class_3,_tmpSecond_class_3);
            }  else  {
              _tmpAccountingProject = null;
            }
            final multilevel_classification _tmpAccountingMerchant;
            if (! (_cursor.isNull(_cursorIndexOfFirstClass_4) && _cursor.isNull(_cursorIndexOfSecondClass_4))) {
              final String _tmpFirst_class_4;
              _tmpFirst_class_4 = _cursor.getString(_cursorIndexOfFirstClass_4);
              final String _tmpSecond_class_4;
              _tmpSecond_class_4 = _cursor.getString(_cursorIndexOfSecondClass_4);
              _tmpAccountingMerchant = new multilevel_classification(_tmpFirst_class_4,_tmpSecond_class_4);
            }  else  {
              _tmpAccountingMerchant = null;
            }
            _item = new Accounting(_tmpAccountingId,_tmpAccountingType,_tmpAccountingAmount,_tmpAccountingClass,_tmpAccountingAcconut,_tmpAccountingTime,_tmpAccountingMember,_tmpAccountingProject,_tmpAccountingMerchant,_tmpAccountingRemark,_tmpAccountingImg);
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
