package com.example.bsaassistant.Exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class examMainDbhelper extends SQLiteOpenHelper {
    private  static final String Db_Name = "ExamMainDb";
    private  static  final  int DB_Version = 5;
    private static final String TABLE_EXAM_HEAD = "EXAMHEAD";
    private static final String Exam_COL = "exam";
    private static final String DEDHEAD_TITLE = "title";
    private static final String DEDHEAD_AMOUNT = "amount";
    private static final String bill_paid = "paid";
    public examMainDbhelper(@Nullable Context context) {
        super(context, Db_Name, null, DB_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_EXAM_HEAD +
               " ( " + Exam_COL + " TEXT PRIMARY KEY,  " + bill_paid + " INTEGER " +
               " ) ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM_HEAD);
        onCreate(sqLiteDatabase);

    }
    public void createExam(String examName,int status){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Exam_COL, examName);
        cv.put(bill_paid, status);
        sqLiteDatabase.insert(TABLE_EXAM_HEAD, null, cv);
        sqLiteDatabase.close();

    }
    public Cursor fetchExams() {

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_EXAM_HEAD, null);

        return data;
    }
    public void setPaid(String examName){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(bill_paid, 1);
        sqLiteDatabase.update(TABLE_EXAM_HEAD, cv, "exam=?", new String[]{examName});

    }

    public Cursor fetchState() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_EXAM_HEAD, null);
        return data;
    }
    public void deleteExam(String unwantedExam){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_EXAM_HEAD, "exam=?", new String[]{unwantedExam});
        sqLiteDatabase.close();
        }
}
