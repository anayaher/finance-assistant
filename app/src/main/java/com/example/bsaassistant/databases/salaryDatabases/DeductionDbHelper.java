package com.example.bsaassistant.databases.salaryDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DeductionDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "deductionDb";
    private static final int DB_VER = 1;
    private static final String TABLE_NAME = "DeductionTable";

    private static final String COL_IT = "it";
    private static final String COL_PT = "pt";
    private static final String COL_GPF = "gpf";
    private static  final String COL_JANSE = "janse";
    private static final String COL_VIDYA = "vidya";
    private static final String COL_OTHER = "other";



    public DeductionDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " ( " + COL_IT + " INTEGER, " + COL_PT + " INTEGER, " + COL_GPF + " INTEGER, " + COL_JANSE + " INTEGER, " + COL_VIDYA + " INTEGER, " + COL_OTHER + "  INTEGER " + " ) " );


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void insertDeduction(int it,int pt,int gpf,int janse,int vidya,int other){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
        contentValues.put(COL_IT,it);
        contentValues.put(COL_PT,pt);
        contentValues.put(COL_GPF,gpf);
        contentValues.put(COL_JANSE,janse);
        contentValues.put(COL_VIDYA,vidya);
        contentValues.put(COL_OTHER,other);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }
    public Cursor getDeductions(){

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return sqLiteDatabase.rawQuery("  SELECT * FROM " + TABLE_NAME,null);
    }



    public  int getTotalDeduction(){
        int deductionAmt = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor d = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME,null);

        if (d.getCount() == 0){
            return  deductionAmt;

        }else
        {
            while (d.moveToNext()){
                for (int i = 0;i<6;i++){
                    deductionAmt += d.getInt(i);

                }


            }
        }

        return deductionAmt;

    }
}
