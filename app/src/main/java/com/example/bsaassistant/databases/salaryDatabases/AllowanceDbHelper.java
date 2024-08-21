package com.example.bsaassistant.databases.salaryDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AllowanceDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "SalSlipDB";
    private static final int DB_VER = 1;
    private static final String TABLE_NAME = "AllowanceTable";
    private static final String COL_BASIC= "basic";
    private static final String COL_DA= "da";
    private static final String COL_HRA = "hra";
    private static final String COL_TA="ta";
    private static final String COL_OTHER="other";



    public AllowanceDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_BASIC + " INTEGER, "
        + COL_DA + " INTEGER, " + COL_HRA + " INTEGER, " + COL_TA + " INTEGER, " + COL_OTHER + " INTEGER " + " ) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE  IF EXISTS " + TABLE_NAME );
        onCreate(sqLiteDatabase);

    }

    public void insertAllowances(int basic,int da,int hra,int ta,int other){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL(" DELETE FROM " + TABLE_NAME);

        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_BASIC,basic);
        contentValues.put(COL_HRA,hra);
        contentValues.put(COL_DA,da);
        contentValues.put(COL_TA,ta);
       contentValues.put(COL_OTHER,other);

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();



    }

    public Cursor getAllowances(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        return sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);


    }

    public  int getTotalAllowance(){
        int basic = 0,hra = 0,da = 0,ta = 0,other = 0;
        int total = 0 ;
        double totalHra =0 ,totalDa = 0;

        int totalAllowance = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor d = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME,null);

        if (d.getCount() == 0){
            return  totalAllowance;

        }else
        {
            while (d.moveToNext()){
                basic = d.getInt(0);
                hra = d.getInt(1);
                da = d.getInt(2);
                ta = d.getInt(3);
                other = d.getInt(4);


                }

            //getting the pda/hra percentage  of basic
            totalDa = ((double) da / 100) * basic;
            totalHra = ((double) hra / 100)* basic;

            //adding all allowances

            total = (int) (basic + totalDa + totalHra + ta + other);
            totalAllowance = total;






        }
        d.close();

        return totalAllowance;

    }
}

