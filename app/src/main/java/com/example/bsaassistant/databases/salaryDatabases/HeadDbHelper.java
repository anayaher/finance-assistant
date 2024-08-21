package com.example.bsaassistant.databases.salaryDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HeadDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "headDb";
    private static final int DB_VER  = 2;
    private static final String TABLE_NAME = "headTable";
    private static final String COL_HEAD = "head";

    public HeadDbHelper(@Nullable Context context) {
        super(context,DB_NAME , null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME  + " ( "
                + COL_HEAD + " TEXT PRIMARY KEY "  + " ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addHead(String headName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL_HEAD,headName);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }
    public void deleteHead(String unwantedHead){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, COL_HEAD + "=?", new String[]{unwantedHead});
        sqLiteDatabase.close();
    }
    public Cursor getHeads(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        return sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME,null);

    }
}
