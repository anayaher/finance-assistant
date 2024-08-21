package com.example.bsaassistant.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PasswordDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "password_database";
    private static final String TABLE_NAME = "password_table";
    private static final String COLUMN_PASSWORD = "password";

    public PasswordDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
                + COLUMN_PASSWORD + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertPassword(String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME); // Delete existing password
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_PASSWORD + ") VALUES ('" + password + "')");
        db.close();
    }

    public String getPassword() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_PASSWORD + " FROM " + TABLE_NAME;
        String password = null;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            password = cursor.getString(0);
        } else {
            // No password found in the database, set default password as "0000"
            password = "";
            insertPassword(password);
        }
        cursor.close();
        db.close();
        return password;
    }

}
