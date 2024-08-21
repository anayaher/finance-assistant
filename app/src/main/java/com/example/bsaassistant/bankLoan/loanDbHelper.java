package com.example.bsaassistant.bankLoan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class loanDbHelper extends SQLiteOpenHelper {
    private static  final  String DB_NAME = "loanDb";
    private static final  Integer DB_VER = 8;
    private  static  final  String TABLE_NAME = "loanTable";
    private static  final String COL_NAME = "bankName";
    private static final String COL_DATE = "date";
    private static final String COL_AMOUNT = "amount";
    private static  final String COL_TENURE = "tenure";
    private static final String COL_ROI = "roi";
    private static final String COL_EMI = "emi";
    private static final String COL_ACC = "accNo";
    private static  final  String COL_ID = "id";


    public loanDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +COL_NAME + " TEXT, " + COL_AMOUNT + " INTEGER, " + COL_TENURE + " INTEGER, " + COL_ROI + " FLOAT, " + COL_EMI + " INTEGER," + COL_DATE + " STRING, " + COL_ACC + " STRING "  + " ) "  );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS  " + TABLE_NAME );
        onCreate(sqLiteDatabase);

    }
    public  int getTotalLoans(){
        int t =0;
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        Cursor da = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        if (da.getCount() == 0) {
            return t;

        }
        else
        {
            while (da.moveToNext()){
                t+= da.getInt(2);
            }
        return t;
        }

    }
    public void addLoan
            (String bankName,Integer amount,Integer tenure,Integer emi,Float roi,String date,String accNo){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME , bankName);
        contentValues.put(COL_DATE , date);

        contentValues.put(COL_AMOUNT, amount);
contentValues.put(COL_TENURE, tenure);
contentValues.put(COL_EMI, emi);
        contentValues.put(COL_ROI, roi);
        contentValues.put(COL_ACC, accNo);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }  public Cursor fetchLoan(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data  = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        return data;
    }public void deleteLoan(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(" delete from " + TABLE_NAME + " where id" + " = " + id );
    }
}
