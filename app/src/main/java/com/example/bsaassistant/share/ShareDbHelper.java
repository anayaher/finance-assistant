package com.example.bsaassistant.share;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ShareDbHelper extends SQLiteOpenHelper {
    private  static final String DB_NAME = "shareDb";
    private static  final int DB_VER = 6;
    private static  final String TABLE_NAME = "shareTable";
    private static final String COL_NAME = "name";
    private  static  final String COL_PREV = "prev";
    private  static  final String COL_EMI  = "emi";
    private  static  final  String COL_STATUS = "status";

    public ShareDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_NAME  + " TEXT, "  + COL_PREV +  " INTEGER, "  + COL_EMI + " INTEGER, " + COL_STATUS + " INTEGER " + " ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public  void addShare(String name,Integer prev,Integer emi,Integer status){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues  =new ContentValues();

        contentValues.put(COL_NAME , name);
        contentValues.put(COL_PREV,prev);
        contentValues.put(COL_EMI,emi);
        contentValues.put(COL_STATUS,status);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();

    }

    public void deleteShare(String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,COL_NAME + "=?", new String[]{name});

    }

    public ArrayList<shareDataModel> getShares() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ArrayList<shareDataModel> shareDataModels = new ArrayList<>();

        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM "  + TABLE_NAME,null);
        if (data.getCount() == 0){
            return  shareDataModels;


        }else {
            while (data.moveToNext()){
                shareDataModel shareDataModel = new shareDataModel();
                shareDataModel.setPatPedhiName(data.getString(0));
                shareDataModel.setPrevBal(data.getInt(1));
                shareDataModel.setEmi(data.getInt(2));
                shareDataModel.setStatus(data.getInt(3));
                shareDataModels.add(shareDataModel);


            }
            return  shareDataModels;
        }

    }
}
