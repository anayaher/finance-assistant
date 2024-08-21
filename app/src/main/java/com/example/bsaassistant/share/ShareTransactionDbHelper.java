package com.example.bsaassistant.share;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ShareTransactionDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "shareTranDb";
    private static final String TABLE_NAME = "shareTranTable";
    private  static  final int DB_VER = 6;
    private  static final String COL_SHARE_NAME ="name";
    private  static  final String COL_DATE = "date";

    private static final String COL_AMT= "amt";

    public ShareTransactionDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_SHARE_NAME + " TEXT, " + COL_DATE + " TEXT ," + COL_AMT + " INTEGER" + " ) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }public ArrayList<shareTransactionModel> getTransactionsFor(String  shareName){
        ArrayList<shareTransactionModel> shareTransactionModels = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor data  = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME  +  " where name=? " ,new String[]{shareName} );
        if (data.getCount() != 0) {
            while (data.moveToNext()) {

                shareTransactionModel shareTransactionModel = new shareTransactionModel();

                shareTransactionModel.setDate(data.getString(1));
                shareTransactionModel.setAmt(data.getInt(2));

                shareTransactionModels.add(shareTransactionModel);



            }
        }
        return shareTransactionModels;

    }
    public int getTotalBal(){
        int total =0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data  = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME,null);
        if (data.getCount() == 0){
            return total;
        }else {
            while (data.moveToNext()){
                total += data.getInt(2);

            }
            return total;
        }
    }
    public void addShareTransaction(String name,int amt,String date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SHARE_NAME,name);
        contentValues.put(COL_DATE,date);
        contentValues.put(COL_AMT,amt);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);

    }
    public  int getAllTotal(){
        int total =0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        if (data.getCount() == 0){
            return 0;

        }else {
            while (data.moveToNext()){
                total += data.getInt(2);


            }
            return total;
        }

    }
    public int getShareTotal(String name){
        int total =0;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME + " where name=? " , new String[]{name});
        if (data.getCount() == 0){
            return 0;

        }else {
            while (data.moveToNext()){
                total += data.getInt(2);


            }
            return total;
        }


    }

    public void deleteShare(String name) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,COL_SHARE_NAME + "=?", new String[]{name});

    }
}
