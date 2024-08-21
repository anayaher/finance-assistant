package com.example.bsaassistant.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PayeeDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "payeeDb";
    private static final int DB_VER  = 2;
    private static final String TABLE_NAME = "payeeTable";
    private static final String COL_PAYEE = "name";

    public PayeeDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME  + " ( "
        + COL_PAYEE + " TEXT PRIMARY KEY"  + " ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addPayee(String payeeName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues  = new ContentValues();
        contentValues.put(COL_PAYEE,payeeName);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }
    public void deletePayee(String unwantedPayee){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, COL_PAYEE + "=?", new String[]{unwantedPayee});
        sqLiteDatabase.close();
    }
    public Cursor getPayee(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        return sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME,null);

    }
}
