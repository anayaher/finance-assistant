package com.example.bsaassistant.Exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class supervisionDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "supervisionDB";
    private static final String TABLE_NAME = "supervisionTable";
    private static final int DB_VER = 6;

    private static final String COL_ID = "id";
    private static final String COL_SUPERVISION_RATE = "rate";
    private static final String COL_DATE = "date";
    private static final String COL_NOS = "nos";
    private static final String COL_EXAM = "exam";
    public supervisionDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( "+ COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_EXAM + " TEXT, " + COL_DATE + " TEXT, " + COL_SUPERVISION_RATE + " INTEGER, " + COL_NOS + " INTEGER " + " ) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addSuperVision(String examName, String date,Integer rate, Integer nos){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EXAM, examName);
        contentValues.put(COL_DATE, date);
        contentValues.put(COL_NOS, nos);
        contentValues.put(COL_SUPERVISION_RATE, rate);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();

    } public Cursor fetchPaperSetting(String wanted){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME +  " WHERE EXAM = '" + wanted + "'",null);
        return data;
    }
}
