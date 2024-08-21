package com.example.bsaassistant.Academics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Date;

public class academicDbHelper  extends SQLiteOpenHelper {
    private  static  final String DB_NAME ="academicsDB";
    private  static  final Integer DB_VER = 1;
    private static  final String TABLE_NAME = "academicsTable";
    private static final String COL_TOP = "top";
    private static  final String COL_ID = "id";
    private static final  String COL_VENUE ="venue";
    private static final String COL_DATE_FROM = "dateFrom";
    private static final String COL_DATE_TO = "dateTo";
    private static final String COL_THEME = "theme";
    private static  final String COL_DETAILS = "details";



    public academicDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE  " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TOP + " TEXT, " + COL_VENUE + " TEXT, " + COL_DATE_FROM + " TEXT, " + COL_DATE_TO + " TEXT, " + COL_DETAILS + " TEXT, " + COL_THEME + " TEXT " + " ) " );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addAcademic(String top, String venue, String fromDate,String toDate, String details,String theme){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_TOP, top);
        contentValues.put(COL_VENUE, venue);
        contentValues.put(COL_DATE_FROM, fromDate);
        contentValues.put(COL_DATE_TO, toDate);
        contentValues.put(COL_DETAILS, details);
        contentValues.put(COL_THEME, theme);
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();


    }public Cursor fetchAcademic(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public Cursor getAcademic(String wantedAcademic){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME +  " WHERE bank = '" + wantedAcademic + "'" , null);
        return data;

    }   public void deleteOne(int ids)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(" delete from " + TABLE_NAME + " where id" + " = " + ids );


    }
}

