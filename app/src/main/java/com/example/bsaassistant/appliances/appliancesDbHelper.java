package com.example.bsaassistant.appliances;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class appliancesDbHelper extends SQLiteOpenHelper {
    private  static  final String DB_NAME ="appliances";
    private  static  final Integer DB_VER = 1;
    private static  final String TABLE_NAME = "appliancesTable";
    private static  final String COL_ID = "id";

    private static  final String COL_ITEM_NAME = "item";
    private static  final String COL_PRICE = "price";
    private static  final  String COL_SHOP_NAME = "shop";
    private static  final String COL_DATE_BOUGHT = "dateBought";
    private static  final  String COL_WARRANTY_DURATION = "warranty";
    private static final String COL_EXPIRY_DATE = "expiryDate";

    public appliancesDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ITEM_NAME + " TEXT, " +
                COL_PRICE + " TEXT, " + COL_SHOP_NAME +  " TEXT, " + COL_DATE_BOUGHT + " TEXT, " + COL_WARRANTY_DURATION + " TEXT, " + COL_EXPIRY_DATE + " TEXT " + " ) " );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addAppliance(String itemName , String price,String shopName,String dateBought,String warrantyDuration,String expiryDate){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ITEM_NAME, itemName);
        contentValues.put(COL_PRICE, price);
        contentValues.put(COL_SHOP_NAME, shopName);
        contentValues.put(COL_DATE_BOUGHT, dateBought);
        contentValues.put(COL_WARRANTY_DURATION, warrantyDuration);
        contentValues.put(COL_EXPIRY_DATE, expiryDate);

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();

    }
    public Cursor fetchAppliance(){
    SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
    Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " +TABLE_NAME, null);
    return data;

    }
    public Cursor getAppliance(String wantedAppliance){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME +  " WHERE bank = '" + wantedAppliance + "'" , null);
        return data;

    }   public void deleteOne(int ids)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        database.execSQL(" delete from " + TABLE_NAME + " where id" + " = " + ids );


    }
}
