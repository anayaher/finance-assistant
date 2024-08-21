package com.example.bsaassistant.gold;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class goldDbHelper extends SQLiteOpenHelper {
    private static  final  String DB_NAME = "goldDb";
    private static final  Integer DB_VER = 2;
    private  static  final  String TABLE_NAME = "goldTable";
    private static  final String COL_NAME = "name";
    private static final String COL_DATE = "date";
    private static final String COL_WEIGHT = "weight";
    private static final String COL_GST = "gst";
    private static final String COL_COST = "cost";
    private static  final String COL_PRICE = "price";
    private static final String COL_MAKING_CHARGES = "charges";
    private static final String COL_RATE = "rate";
    private static final String COL_JEWELER = "jewelerName";
    private static  final  String COL_ID = "id";



    public goldDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +COL_NAME + " TEXT, " + COL_DATE + " TEXT, " + COL_PRICE + " INTEGER, " + COL_MAKING_CHARGES + " FLOAT, " + COL_WEIGHT + " INTEGER, " + COL_RATE + " INTEGER,  " + COL_GST + " INTEGER, " + COL_COST + " INTEGER, " + COL_JEWELER + " TEXT " + " ) "  );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS  " + TABLE_NAME );
        onCreate(sqLiteDatabase);

    }public void addGold(String name,String date,Integer price,Float makingCharges,Float WIG,Integer rate,Integer gst,Integer cost, String jewelerName)
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME , name);
        contentValues.put(COL_DATE , date);
        contentValues.put(COL_PRICE, price);
        contentValues.put(COL_MAKING_CHARGES, makingCharges);
        contentValues.put(COL_WEIGHT, WIG);
        contentValues.put(COL_RATE, rate);
        contentValues.put(COL_JEWELER, jewelerName);
        contentValues.put(COL_GST, gst);
        contentValues.put(COL_COST, cost);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }
    public Cursor fetchGold(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data  = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME, null);
        return data;
    }
    public int getTotalAmt(){
        int t = 0;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor da = sqLiteDatabase.rawQuery("  SELECT * FROM "  + TABLE_NAME,null);
        if (da.getCount() == 0){
            return 0;

        }else {
            while (da.moveToNext()){
                t+= da.getInt(6) * da.getInt(5);

            }
            return t;

        }
    }
    public void deleteGold(int id){
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(" delete from " + TABLE_NAME + " where id" + " = " + id );
    }public void updateGold(Integer id,String name,String date,Integer price,Float makingCharges,Float WIG,Integer rate,Integer gst,Integer cost, String jewelerName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        String upid2 = String.valueOf(id);

        contentValues.put(COL_NAME , name);
        contentValues.put(COL_DATE , date);
        contentValues.put(COL_PRICE, price);
        contentValues.put(COL_MAKING_CHARGES, makingCharges);
        contentValues.put(COL_WEIGHT, WIG);
        contentValues.put(COL_RATE, rate);
        contentValues.put(COL_JEWELER, jewelerName);
        contentValues.put(COL_GST, gst);
        contentValues.put(COL_COST, cost);

        sqLiteDatabase.update(TABLE_NAME, contentValues, "id=?", new String[]{upid2});
        ;





    }
}