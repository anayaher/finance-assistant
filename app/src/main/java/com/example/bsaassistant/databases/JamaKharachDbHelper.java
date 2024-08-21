package com.example.bsaassistant.databases;

import static androidx.fragment.app.FragmentManager.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.bsaassistant.datamodels.jkModel;
import com.example.bsaassistant.datamodels.salaryModel;

import java.util.ArrayList;

public class JamaKharachDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "jkDb";
    private static final String TABLE_NAME ="jkTable";
    private static final int DB_VER = 3;
    private static final String COL_SR = "srNo";
    private static  final String COL_DATE = "date";
    private static final String COL_TYPE = "type";
    private static final String COL_BAl = "bal";

    private static final String COL_AMOUNT = "amount";
    private static final String COL_PAYEE = "payee";
    private static final String COL_HEAD = "head";
    private static final String COL_DETAILS = "details";


    public JamaKharachDbHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(" CREATE TABLE " + TABLE_NAME + " ( " + COL_SR + " INTEGER PRIMARY KEY, " +  COL_DATE + " TEXT, "
         + COL_TYPE + " INTEGER, " + COL_AMOUNT + " INTEGER, " + COL_BAl + "  INTEGER, " + COL_PAYEE + " TEXT, " + COL_HEAD + " TEXT, " +
                COL_DETAILS + " TEXT " + " ) " );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS  " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }
    public void insertJk(int srNo,String date,int type,int amount,int bal,String payee,String head,String details){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL_SR,srNo);
        contentValues.put(COL_DATE,date);
        contentValues.put(COL_TYPE,type);
        contentValues.put(COL_AMOUNT,amount);
        contentValues.put(COL_BAl,bal);
        contentValues.put(COL_PAYEE,payee);
        contentValues.put(COL_HEAD,head);
        contentValues.put(COL_DETAILS,details);
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        sqLiteDatabase.close();
    }
    public int getLatestSr(){
        int sr = 0;
        SQLiteDatabase sqLiteDatabase  = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME,null);
        if (data.getCount() == 0){
            return sr+1;

        }else {
            if (data.moveToLast()){
                sr = data.getInt(0);
            }
        }
        return sr+1;


    }

    public void deleteSalForMonth(String month){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String whereClause = COL_HEAD + " = ?";
        String[] whereArgs = {month};
        sqLiteDatabase.delete(TABLE_NAME, whereClause, whereArgs);
        sqLiteDatabase.close();


    }
    public int getAllIncome(){
        int allIncoome = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        if (data.getCount() == 0){

        }else
            while (data.moveToNext()){
                if (data.getInt(2) == 1){
                    allIncoome += data.getInt(3);


                }

            }
     return allIncoome;
    }
    public int getAllExpense(){
        int allExpense = 0;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM  " + TABLE_NAME,null);
        if (data.getCount() == 0){
            return allExpense;
        }else {
            while (data.moveToNext()){
                if (data.getInt(2) == 0){
                    allExpense += data.getInt(3);


                }

            }
        }
        return allExpense;
    }
    public int getCurrentBal(){
        int curBal = 0 ;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        if (data.getCount() == 0){
            return 0;

        }else {
            if (data.moveToLast()){
              curBal=   data.getInt(4);

            }
            sqLiteDatabase.close();
            return curBal;
        }
    }
    public Cursor getAllJk(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        return  sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);

    }public int getAmt(int transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int amount = 0;

        String[] columns = {"amount"};
        String selection = "srNo=?";
        String[] selectionArgs = {String.valueOf(transactionId)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int amountIndex = cursor.getColumnIndex("amount");
            if (amountIndex != -1) {
                amount = cursor.getInt(amountIndex);
                ;
            } else {

            }
        }

            if (cursor != null) {
                cursor.close();
            }

            db.close();

            return amount;


        }
    public int getType(int transactionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int type = 0;

        String[] columns = {"type"};
        String selection = "srNo=?";
        String[] selectionArgs = {String.valueOf(transactionId)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int amountIndex = cursor.getColumnIndex("type");
            if (amountIndex != -1) {
                type = cursor.getInt(amountIndex);
                ;
            } else {

            }
        }

        if (cursor != null) {
            cursor.close();
        }

        db.close();

        return type;


    }
    public ArrayList<jkModel> selectBetweenDates(String minDate,String maxDate,String payee){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<jkModel> jkModels = new ArrayList<>();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " where date  BETWEEN ?  AND ? AND payee = ? " , new String[]{minDate, maxDate,payee});

        while (data.moveToNext()) {
            jkModel jkModel = new jkModel();
            jkModel.setSr(data.getInt(0));
            jkModel.setDate(data.getString(1));
            jkModel.setType(data.getInt(2));
            jkModel.setAmt(data.getInt(3));
            jkModel.setBal(data.getInt(4));
            jkModel.setPayee(data.getString(5));
            jkModel.setHead(data.getString(6));
            jkModel.setDetails(data.getString(7));
            jkModels.add(jkModel);

        }
        data.close();
        return jkModels;

    }
    public ArrayList<jkModel>  selectBetweenDates(String minDate,String maxDate){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<jkModel> jkModels = new ArrayList<>();
        Cursor data = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME + " where date  BETWEEN ?  AND ? ", new String[]{minDate, maxDate});

        while (data.moveToNext()) {
            jkModel jkModel = new jkModel();
            jkModel.setSr(data.getInt(0));
            jkModel.setDate(data.getString(1));
            jkModel.setType(data.getInt(2));
            jkModel.setAmt(data.getInt(3));
            jkModel.setBal(data.getInt(4));
            jkModel.setPayee(data.getString(5));
            jkModel.setHead(data.getString(6));
            jkModel.setDetails(data.getString(7));
            jkModels.add(jkModel);

        }
        data.close();
        return jkModels;

    }

    public void deleteTransaction(int transactionId) {
        // Code to delete the transaction with the given transactionId from the SQL database
        // Example:
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = "srNo=?";
        String[] selectionArgs = {String.valueOf(transactionId)};
        db.delete(TABLE_NAME, selection, selectionArgs);
        db.close();
    }

    public void updateSuccessorTransactions(int srNo, int transactionType, int amountDifference) {
        SQLiteDatabase db = this.getWritableDatabase();

        String[] columns = {COL_SR,COL_TYPE,COL_AMOUNT,COL_BAl};
        String selection = "srNo > ?";
        String[] selectionArgs = {String.valueOf(srNo)};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, "srNo ASC");

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int amountIndex = cursor.getColumnIndex(COL_AMOUNT);
                int balanceIndex = cursor.getColumnIndex(COL_BAl);
                int srIndex = cursor.getColumnIndex(COL_SR);
                int typeIndex = cursor.getColumnIndex(COL_TYPE);


                do {


                    // Check if the amount and balance columns exist in the cursor
                    if (amountIndex != -1 && balanceIndex != -1 && typeIndex !=-1 && srIndex !=-1) {
                        int currentAmount = cursor.getInt(amountIndex);
                        int currentBalance = cursor.getInt(balanceIndex);
                        int currentSerialNumber = cursor.getInt(srIndex);
                        int currentTransactionType = cursor.getInt(typeIndex);

                        // Calculate the balance change for the successor transaction
                        int balanceChange = 0;
                        if (transactionType == 0) {
                            // If the deleted transaction type was 0(expense), add the amount difference back
                            balanceChange =  amountDifference;
                        } else if (transactionType == 1) {
                            // If the deleted transaction type was 1 (income), subtract the amount difference
                            balanceChange =  -amountDifference;
                        }

                        // Update the balance of the successor transaction in the model
                        double updatedBalance = currentBalance + balanceChange;
                        ContentValues values = new ContentValues();
                        values.put("bal", updatedBalance);
                        db.update(TABLE_NAME, values, "srNo=?", new String[]{String.valueOf(currentSerialNumber)});
                    } else {
                        Log.d("Tag", "updateSuccessorTransactions:  + error");
                        // Handle the case when the columns do not exist in the cursor
                        // You might want to log an error or show an appropriate message to the user.
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        db.close();
    }



    public ArrayList<jkModel>  getJkModel(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor data = sqLiteDatabase.rawQuery(" SELECT * FROM " + TABLE_NAME,null);
        ArrayList<jkModel> jkModels  = new ArrayList<>();



            while (data.moveToNext()) {
                jkModel jkModel = new jkModel();
                jkModel.setSr(data.getInt(0));
                jkModel.setDate(data.getString(1));
                jkModel.setType(data.getInt(2));
                jkModel.setAmt(data.getInt(3));
                jkModel.setBal(data.getInt(4));
                jkModel.setPayee(data.getString(5));
                jkModel.setHead(data.getString(6));
                jkModel.setDetails(data.getString(7));

                jkModels.add(jkModel);

            }

            data.close();
            return jkModels;




    }

}
