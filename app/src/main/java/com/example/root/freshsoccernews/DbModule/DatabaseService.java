package com.example.root.freshsoccernews.DbModule;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by root on 15-5-23.
 */
public interface DatabaseService {
    public long insertData(String tableName, ContentValues contentValues);

    public int deleteData(String tableName, String whereClause, String[] whereArgs);

    public int updateData(String tableName, ContentValues contentValues, String whereClause,
                          String[] whereArgs);

    public Cursor queryData(String tableName, String selectionClause, String[] selectionArgs);
}
