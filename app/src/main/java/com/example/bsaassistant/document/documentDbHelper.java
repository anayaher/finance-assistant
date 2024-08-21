package com.example.bsaassistant.document;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

public class documentDbHelper  extends SQLiteOpenHelper {
    private static final String DB_NAME = "docDb";
    private static final Integer DB_VER =1;
    private static final String TABLE_NAME = "docTable";
    private static final String COL_NAME = "name";
    private static final String COL_IMG = "image";

    public documentDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
      sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_NAME + " TEXT, " + COL_IMG + " BLOB " + " ) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addEntry( String name, byte[] image) throws SQLiteException {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(COL_NAME,    name);
        cv.put(COL_IMG,   image);
        database.insert( TABLE_NAME, null, cv );
    }
    public Cursor fetchDoc(){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor data = database.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        return data;

    }
    public Bitmap getImage(String wantedName){
        SQLiteDatabase db = this.getReadableDatabase();

        String qu = "select img  from table where feedid=" + wantedName ;
        Cursor cur = db.rawQuery(qu, null);

        if (cur.moveToFirst()){
            byte[] imgByte = cur.getBlob(1);
            cur.close();
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
        if (cur != null && !cur.isClosed()) {
            cur.close();
        }

        return null;
    }public Cursor findDoc(String wanted){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data =db.rawQuery( "select * from " + TABLE_NAME + " where " +COL_NAME + "='" + wanted + "'" ,null );
        return data;


    }

    public void deleteOne(String name) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, "name=?", new String[]{name} );
    }
}
