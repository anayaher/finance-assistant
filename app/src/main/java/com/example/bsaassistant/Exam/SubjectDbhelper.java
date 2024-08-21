package com.example.bsaassistant.Exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SubjectDbhelper extends SQLiteOpenHelper {
    private  static final  String DB_NAME = "subjectDb";
    private  static  final  int DB_VERSION = 1;
    private static  final  String TABLE = "subjecttableR";
    private  static final  String SUB_COL = "subject";

    public SubjectDbhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE + " ( " + SUB_COL +" TEXT PRIMARY KEY " + " ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);

    }

    public void addSubject(String sub){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SUB_COL, sub);
        sqLiteDatabase.insert(TABLE, null, contentValues);
        sqLiteDatabase.close();


    }public Cursor fetchSubject(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE, null);

        return data;

    }

    public void deleteHead(String unwanted) {

        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE, "subject=?", new String[]{unwanted});
        database.close();
    }
}
