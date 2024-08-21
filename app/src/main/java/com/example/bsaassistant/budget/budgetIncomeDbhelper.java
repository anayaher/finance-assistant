package com.example.bsaassistant.budget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class budgetIncomeDbhelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "budgetIncomeDb";
    public static final String TABLE_NAME = "budgetTable";
    public static final  String COL_NAME = "name";
    public static final String  COL_AMT = "amount";
    public static final String COL_STATE = "state";
    private static final int DB_VER = 4;

    public budgetIncomeDbhelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE "  + TABLE_NAME + " ( " + COL_NAME +  " TEXT, " + COL_AMT + " INTEGER, " + COL_STATE + " INTEGER " + " ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void addIncomeSource(String  name,int amt,int state){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME,name);
        contentValues.put(COL_AMT,amt);
        contentValues.put(COL_STATE,state);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();

    }
    public ArrayList<budgetIncomeModel> getIncomeSources()
    {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
       ArrayList<budgetIncomeModel>dataModels = new ArrayList<>();
       Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
           if (data.getCount() == 0){
               return dataModels;
           }
           else {
               while (data.moveToNext()){
                   budgetIncomeModel dataModel = new budgetIncomeModel();
                    dataModel.setName(data.getString(0));
                    dataModel.setAmount(data.getInt(1));
                    dataModel.setChecked(true);
                   dataModels.add(dataModel);

               }
               return dataModels;
           }

    }
    public int getTotalIncome(){
        int totalIncome = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        if (data.getCount() == 0){
            return totalIncome;
        }else {
            while (data.moveToNext()){
                totalIncome += data.getInt(1);

            }
            return totalIncome;

        }
    }

    public void updateStatus(int newState,String name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_STATE,newState);
        sqLiteDatabase.update(TABLE_NAME,contentValues,"name=?",new String[]{String.valueOf(name)});
    }

    public void deleteIncomeSource(String name){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        sqLiteDatabase.delete(TABLE_NAME,"name=?",new String[]{String.valueOf(name)});


    }
}
