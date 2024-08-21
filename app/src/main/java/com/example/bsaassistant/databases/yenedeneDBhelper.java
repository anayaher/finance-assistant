package com.example.bsaassistant.databases;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.w3c.dom.Text;

public class yenedeneDBhelper extends SQLiteOpenHelper {

    private  static final String Db_Name = "YeneDeneDB";
    private  static  final  int DB_Version = 2;
    private static final String TABLE_YD = "yeneDeneTable";
    private static final String HEAD = "head";
    private static final String YD_ID = "id";
    private static final String YENE = "yene";
    private static final String DENE = "dene";
    private static final String DATE = "date";

    public yenedeneDBhelper(@Nullable Context context) {
        super(context, Db_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( " CREATE TABLE " + TABLE_YD +
                " ( " +YD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + HEAD + " TEXT, " + YENE + " INTEGER, " + DENE + " INTEGER, " + DATE + " TEXT " + ")");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_YD);
        onCreate(sqLiteDatabase);

    }
    public void addYeneDene(String head, Integer yene , Integer dene, String date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(HEAD, head);
        contentValues.put(YENE, yene);
        contentValues.put(DENE, dene);
        contentValues.put(DATE, date);
        sqLiteDatabase.insert(TABLE_YD, null, contentValues);


    }public Cursor fetchYeneDene() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor dataYenedene = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_YD, null);

        return dataYenedene;
    }

    public int getTotalYene(){
        int yene =0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_YD,null);

        if (data.getCount() == 0){
            return yene;
        }
        else {
            while (data.moveToNext()) {
                yene += data.getInt(2);

            }
        }
        data.close();

        return yene;

    }
    public int getTotalDene(){
        int yene =0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_YD,null);

        if (data.getCount() == 0){
            return yene;
        }
        else {
            while (data.moveToNext()) {
                yene += data.getInt(3);

            }
        }
        data.close();

        return yene;

    }
    public void deleteAll()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_YD,null,null);
        db.execSQL(" delete  from "+ TABLE_YD);
        db.close();
    } public void deleteOne(int ids)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(" delete from " + TABLE_YD + " where id" + " = " + ids );




    }
}