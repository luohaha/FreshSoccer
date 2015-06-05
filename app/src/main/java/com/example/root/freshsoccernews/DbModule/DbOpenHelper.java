package com.example.root.freshsoccernews.DbModule;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by root on 15-5-23.
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    /**
     * data base's name and version
     * */
    private static String mDataBaseName = "FreshSoccerNews.db";
    private static int mDataBaseVersion = 1;
    private static DbOpenHelper mDbOpenHelper;
    /**
     * using the singleton
     * */
    public static synchronized DbOpenHelper getInstance (Context context) {
        if (mDbOpenHelper == null) {
            mDbOpenHelper = new DbOpenHelper(context.getApplicationContext());
        }
        return mDbOpenHelper;
    }
    public DbOpenHelper(Context context) {
        super(context, mDataBaseName, null, mDataBaseVersion);
    }
    /**
     * create new data base, it will exec the first time, and it won't start next time,
     * we can use it to init
     * */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table sinapage(id integer primary key autoincrement, title varchar(64)," +
                " img varchar(64))";
        db.execSQL(sql);
        sql = "create table hupupage(id integer primary key autoincrement, title varchar(64)," +
                " img varchar(64))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

