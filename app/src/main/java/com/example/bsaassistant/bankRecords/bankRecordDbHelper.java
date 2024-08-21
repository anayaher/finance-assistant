package com.example.bsaassistant.bankRecords;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class bankRecordDbHelper extends SQLiteOpenHelper {
    private static final  String DB_NAME = "bankRecordDB";
    private  static  final Integer DB_VER = 3;

    private static final  String TABLE_NAME = "bankTable";
    private static final  String COL_BANK = "bank";
    private static final String COL_ACCNO  = "accNo";
    private static final String COL_CUSNO = "cusNo";
    private static final String COL_IFSC = "ifscNo";
    private static final String COL_CLOSED_ON = "closedOn";
    private static final String COL_WORK_TIME = "workingTime";



    public bankRecordDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_BANK + " TEXT PRIMARY KEY, " + COL_ACCNO + " TEXT, " + COL_IFSC + " TEXT, " + COL_CUSNO + " INTEGER, " + COL_CLOSED_ON + " TEXT, "  + COL_WORK_TIME + " TEXT "  + " ) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void addBankRecord(String bankNamem, String accNo,Integer cusNo,String ifscNo,String closedOn,String workingTime){
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_BANK, bankNamem);
        contentValues.put(COL_CUSNO, cusNo) ;
        contentValues.put(COL_ACCNO, accNo) ;
        contentValues.put(COL_CLOSED_ON, closedOn);
        contentValues.put(COL_WORK_TIME, workingTime);
        contentValues.put(COL_IFSC, ifscNo);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();


    }
    public Cursor fetchBankRecord(String wantedBank){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME   + " WHERE bank = '" + wantedBank + "'", null);
   return data;
    }
    public Cursor fetchAllBanks(){
        SQLiteDatabase sqLiteDatabase =this.getReadableDatabase();
        Cursor date = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        return  date;
    }
    public void DeleteBank(String unwantedBank){
        SQLiteDatabase sqLiteDatabase =this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, "bank=?", new String[]{unwantedBank});
        sqLiteDatabase.close();

    }

}
