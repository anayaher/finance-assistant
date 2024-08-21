package com.example.bsaassistant.goldLoan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class goldLoanDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "goldLoanDb";
    private static final Integer DB_VER = 1;
    private static final String TABLE_NAME = "loanTable";
    private static final String COL_NAME = "name";
    private static final String COL_AMOUNT= "amount";
    private static final String COL_DATE = "date";
private  static final String COL_TENURE = "tenure";
private static final String COL_ROI = "roi";
private static final String COL_MDATE = "mDate";

private static final String COL_ITEMS = "items";

    public goldLoanDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " +TABLE_NAME + " ( "  + COL_NAME + " TEXT, " + COL_DATE + " TEXT, " + COL_AMOUNT + "  INTEGER, " +COL_TENURE + " INTEGER, " + COL_ROI + " DOUBLE, "+COL_MDATE + " TEXT, " + COL_ITEMS + " TEXT " + " ) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
onCreate(sqLiteDatabase);

    }
public void addGoldLoan(String name,Integer amount,String date,Integer tenure,Double roi,String mDate,String items){
    ContentValues contentValues = new ContentValues();
    SQLiteDatabase sqLiteDatabase = this
            .getWritableDatabase();
    contentValues.put(COL_NAME, name);
    contentValues.put(COL_DATE, date);
    contentValues.put(COL_AMOUNT, amount);
    contentValues.put(COL_TENURE, tenure);
    contentValues.put(COL_ROI, roi);
    contentValues.put(COL_MDATE, mDate);
    contentValues.put(COL_ITEMS, items);
    sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    sqLiteDatabase.close();
}
public Cursor fetchGoldLoan(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor data =  sqLiteDatabase.rawQuery(" SELECT * FROM " +TABLE_NAME, null);
        return data;
    }
    public int getTotalLoan(){
        int t = 0;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor da = sqLiteDatabase.rawQuery("  SELECT * FROM "  + TABLE_NAME,null);
        if (da.getCount() == 0){
            return 0;

        }else {
            while (da.moveToNext()){
                t+= da.getInt(2);

            }
            return t;

        }
    }
    public void deleteGoldLoan(String unWanted){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, "name=?", new String[]{unWanted});
        database.close();
    }


}

