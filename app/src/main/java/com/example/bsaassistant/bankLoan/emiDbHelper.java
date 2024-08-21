package com.example.bsaassistant.bankLoan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class emiDbHelper extends SQLiteOpenHelper  {
    private static final String DB_NAME  = "emiDb";
    private static final Integer DB_VER = 16;

    private static final String COL_ID = "id";
    private  static final String COL_STATUS = "EmiStatus";

    private  static  final  String TABLE_NAME = "emiTable";
    private static  final String COL_NAME = "bankName";
    private static final String COL_DATE = "date";
    private static final String COL_PREMIUM = "premium";
    private static final String COL_LOAN_AMT = "loanAmt";

    private static  final String COL_ROI = "roi";
    private static  final String COL_MANDY = "mandy";


    public emiDbHelper( Context context) {
        super(context, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " +TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_NAME + " TEXT, " + COL_DATE + " STRING, " + COL_MANDY + " TEXT, " + COL_PREMIUM  + " INTEGER, " + COL_ROI + " DOUBLE, " + COL_STATUS + " INTEGER, " + COL_LOAN_AMT + " INTEGER " + " ) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public void addEmi(String bankName,String date,String mandy,Integer premium,Double roi,Integer loanAmt,Integer status){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, bankName);
        contentValues.put(COL_DATE, date);
        contentValues.put(COL_MANDY, mandy);
        contentValues.put(COL_PREMIUM, premium);
        contentValues.put(COL_ROI, roi);
    contentValues.put(COL_LOAN_AMT, loanAmt);
        contentValues.put(COL_STATUS, status);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        sqLiteDatabase.close();
    }
    public Cursor fetchEmi(String wanted){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME +  " WHERE bankName = '" + wanted + "'",null);
        return data;




    }
    public void deleteEmi(String bankName){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(" delete from " + TABLE_NAME + " WHERE bankName = '" + bankName + "'" );
    }
}
