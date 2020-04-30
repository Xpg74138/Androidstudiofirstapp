package com.example.ourfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class localDB extends SQLiteOpenHelper {
    private String DATABASE_NAME;
    private int DATABASE_VERSION;

    //表的定义
    public static class UserInfo implements BaseColumns {
        public static final String TABLE_NAME = "UserInfo";
        public static final String COLUMN_NAME_PLAYER = "player";
        public static final String COLUMN_NAME_SCORE = "score";
    }

    public static class PersonInfo implements BaseColumns {
        public static final String TABLE_NAME = "PersonInfo";
        public static final String COLUMN_ID_NUM = "id_num";
        public static final String COLUMN_NAME = "name";
    }

    public localDB(Context context,String databasename,SQLiteDatabase.CursorFactory factory,int version){
        super(context,databasename,factory,version);
        DATABASE_NAME=databasename;
        DATABASE_VERSION=version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + UserInfo.TABLE_NAME + " ("
                + UserInfo._ID + " INTEGER PRIMARY KEY,"
                + UserInfo.COLUMN_NAME_PLAYER + " TEXT,"
                + UserInfo.COLUMN_NAME_SCORE + " TEXT" + ")";// Text or integer will affect sort
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS " + UserInfo.TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public void insertOnePlayerInfo(String name, String score) {
        SQLiteDatabase dbw = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(localDB.UserInfo.COLUMN_NAME_PLAYER, name);
        value.put(localDB.UserInfo.COLUMN_NAME_SCORE, score);
        dbw.insert(localDB.UserInfo.TABLE_NAME, null, value);
        //可以不适用默认的insert语句，而改用构造sql进行查询
        //String sql = "INSERT " + name + score.....//构造标准SQL插入语句
        //dbw.execSQL(sql);
    }

}

