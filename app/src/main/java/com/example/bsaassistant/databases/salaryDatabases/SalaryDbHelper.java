package com.example.bsaassistant.databases.salaryDatabases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bsaassistant.datamodels.salaryModel;

import java.util.ArrayList;

public class SalaryDbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "salDb";
    private static final String TABLE_NAME = "salTable";
    private static final String COL_DATE = "date";
    private static final String COL_MONTH = "month";
    private static final int DB_VER  = 1;


    //sal fields
    private static final String COL_BASIC= "basic";
    private static final String COL_DA= "da";
    private static final String COL_HRA = "hra";
    private static final String COL_TA="ta";
    private static final String COL_OTHER="other";

    //DEDUCTION FIELDS

    private static final String COL_IT = "it";
    private static final String COL_PT = "pt";
    private static final String COL_GPF = "gpf";
    private static  final String COL_JANSE = "janse";
    private static final String COL_VIDYA = "vidya";
    private static final String COL_DED_OTHER = "dedOther";
    private static final String COL_PAY_IN_HAND = "payInHand";
    private static final String COL_TOTAL_SAL = "totalSal";
    private static final String COL_TOTAL_DED = "totalDed";

    public SalaryDbHelper(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_DATE + " DATE, " + COL_MONTH + " TEXT  PRIMARY KEY, " +
                 COL_BASIC + " TEXT, " + COL_DA +  " INTEGER ," + COL_HRA + " INTEGER, " + COL_TA + " INTEGER, " + COL_OTHER + "  INTEGER, " + COL_TOTAL_SAL + " INTEGER, " +
                COL_IT + " INTEGER ," + COL_PT + " INTEGER ," + COL_GPF + " INTEGER ," + COL_JANSE + " INTEGER ," +COL_VIDYA+ " INTEGER ," + COL_DED_OTHER+ " INTEGER, " + COL_TOTAL_DED + " INTEGER, " + COL_PAY_IN_HAND + " INTEGER " + " ) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void insertSalary(String date,String month,int basic,int da,int hra,int ta,int salOther,int totalSal,int it,int pt,int gpf,int janse,int vidya,int dedOther,int totalDed,int payInHand){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_DATE,date);
        contentValues.put(COL_MONTH,month);
        contentValues.put(COL_BASIC,basic);
        contentValues.put(COL_DA,da);
        contentValues.put(COL_HRA,hra);
        contentValues.put(COL_TA,ta);
        contentValues.put(COL_OTHER,salOther);
        contentValues.put(COL_TOTAL_SAL,totalSal);
        contentValues.put(COL_IT,it);
        contentValues.put(COL_PT,pt);
        contentValues.put(COL_GPF,gpf);
        contentValues.put(COL_JANSE,janse);
        contentValues.put(COL_VIDYA,vidya);
        contentValues.put(COL_DED_OTHER,dedOther);
        contentValues.put(COL_TOTAL_DED,totalDed);
        contentValues.put(COL_PAY_IN_HAND,payInHand);

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();

    }
    public Cursor getSalary(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        return sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);

    }
    public ArrayList<salaryModel> fetchSalBetween(String minDate, String maxDate) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<salaryModel> salarymodels = new ArrayList<>();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " where date  BETWEEN ?  AND ? ", new String[]{minDate, maxDate});
        salaryModel salarymodel = new salaryModel();
        while (data.moveToNext()) {
            salarymodel.setMonth(data.getString(1));
            salarymodel.setDate(data.getString(0));
            salarymodel.setBasic(data.getInt(2));
            salarymodel.setDa(data.getInt(3));
            salarymodel.setHra(data.getInt(4));
            salarymodel.setTa(data.getInt(5));
            salarymodel.setOther(data.getInt(6));
            salarymodel.setSalHeadTotal(data.getInt(7));
            salarymodel.setIt(data.getInt(8));
            salarymodel.setPt(data.getInt(9));
            salarymodel.setGpf(data.getInt(10));
            salarymodel.setJanse(data.getInt(11));
            salarymodel.setVidya(data.getInt(12));
            salarymodel.setDedOther(data.getInt(13));
            salarymodel.setDedHeadTotal(data.getInt(14));
            salarymodel.setPayInHand(data.getInt(15));
            salarymodels.add(salarymodel);


        }
        data.close();
        return salarymodels;
    }
}
