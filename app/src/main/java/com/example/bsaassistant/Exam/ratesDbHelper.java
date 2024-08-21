package com.example.bsaassistant.Exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ratesDbHelper  extends SQLiteOpenHelper {
    private  static  final String DB_NAME = "ratesDB";
    private  static final  String TABLE_NAME = "ratesTable";
    private  static final String COL_PC_RATES ="pcRates";
    private  static  final String COL_S_RATES = "sRates";
    private  static final String COL_PS_RATES = "psRates";
    private  static  final int DB_VER = 1;


    public ratesDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_PC_RATES + " INTEGER, " + COL_S_RATES + " INTEGER, " + COL_PS_RATES + " INTEGER " + " ) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public  void addRates(int pcRate,int sRate,int psRate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL_PC_RATES, pcRate);
        contentValues.put(COL_PS_RATES, psRate);
        contentValues.put(COL_S_RATES, sRate);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();



    }public Cursor fetchRates(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        return  data;


    }


    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,null,null);
        sqLiteDatabase.execSQL(" delete  from "+ TABLE_NAME);
        sqLiteDatabase.close();
    }
}
